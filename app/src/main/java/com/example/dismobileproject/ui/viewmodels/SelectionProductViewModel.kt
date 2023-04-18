package com.example.dismobileproject.ui.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dismobileproject.DisApplication
import com.example.dismobileproject.data.model.ContraindicationModel
import com.example.dismobileproject.data.model.IndicationModel
import com.example.dismobileproject.data.model.ProductModel
import com.example.dismobileproject.data.repositories.NetworkProductParameterRepository
import com.example.dismobileproject.data.repositories.ProductParameterRepository
import com.example.dismobileproject.data.repositories.ProductRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.dismobileproject.data.model.SelectionParameterModel
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface AscDescUiState{
    object Ascending: AscDescUiState
    object Decreasing: AscDescUiState
    object Ignore: AscDescUiState
}

sealed interface CountUiState{
    object Five: CountUiState
    object Ten: CountUiState
    object All: CountUiState
}

sealed interface AvailabilityUiState{
    object Show: AvailabilityUiState
    object NotShow: AvailabilityUiState
}

sealed interface SelectionUiState{
    object Success: SelectionUiState
    object Loading: SelectionUiState
    object Error: SelectionUiState
}

sealed interface SelectableParameterState{
    object Indication: SelectableParameterState
    object Contraindication: SelectableParameterState
    object Non: SelectableParameterState
}

sealed interface SelectableScreensState{
    object SelectionParameter: SelectableScreensState
    object EvaluationParameter: SelectableScreensState
}

class ParameterListModel(
    val Id: Int,
    val Name: String,
    initialChecked: Boolean = false
) {
    var isSelected by mutableStateOf(initialChecked)
}

class EvaluationListModel(
    val Number: Int,
    initialChecked: Boolean = false
) {
    var isSelected by mutableStateOf(initialChecked)
}

class SelectionProductViewModel(
    private  val productParameterRepository: ProductParameterRepository,
    private  val productRepository: ProductRepository
): ViewModel(){

    var selectionUiState: SelectionUiState by mutableStateOf(SelectionUiState.Loading)
        private set

    var selectableParameterState: SelectableParameterState by mutableStateOf(SelectableParameterState.Non)
        private set

    var selectableScreensState: SelectableScreensState by mutableStateOf(SelectableScreensState.SelectionParameter)
        private set

    //region main parameter states
    var priceUiState: AscDescUiState by mutableStateOf(AscDescUiState.Ignore)
        private set

    var assessmentUiState: AscDescUiState by mutableStateOf(AscDescUiState.Ignore)
        private set

    var reviewsUiState: AscDescUiState by mutableStateOf(AscDescUiState.Ignore)
        private set

    var countUiState: CountUiState by mutableStateOf(CountUiState.Five)
        private set

    var availabilityUiState: AvailabilityUiState by mutableStateOf(AvailabilityUiState.NotShow)
        private set
    //endregion

    //region bottom sheet states
    val indicationStates by mutableStateOf(mutableListOf<ParameterListModel>())

    var contraindicationStates by mutableStateOf(mutableListOf<ParameterListModel>())
    //endregion

    //region evaluation states
    val evaluationContraindicationStates by mutableStateOf((1..10).map { EvaluationListModel(Number = it) }.toMutableList())

    val evaluationPriceStates by mutableStateOf((1..10).map { EvaluationListModel(Number = it) }.toMutableList())

    val evaluationAssessmentStates by mutableStateOf((1..10).map { EvaluationListModel(Number = it) }.toMutableList())

    val evaluationReviewsStates by mutableStateOf((1..10).map { EvaluationListModel(Number = it) }.toMutableList())
    //endregion

    fun updatePriceState(ascDescState: AscDescUiState){
        priceUiState = ascDescState
    }

    fun updateAssessmentState(ascDescState: AscDescUiState){
        assessmentUiState = ascDescState
    }

    fun updateReviewsState(ascDescState: AscDescUiState){
        reviewsUiState = ascDescState
    }

    fun updateCountState(countState: CountUiState){
        countUiState = countState
    }

    fun updateAvailabilityState(availabilityState: AvailabilityUiState){
        availabilityUiState = availabilityState
    }

    fun updateSelectableScreensState(selectableScreen: SelectableScreensState){
        selectableScreensState = selectableScreen
    }

    fun onIndicationCheck(id: Int){
        indicationStates.find { it.isSelected }?.let { item ->
            item.isSelected = false
        }
        indicationStates.find { it.Id == id }?.let { item ->
            item.isSelected = true
        }
    }

    fun onContraindicationCheck(id: Int){
        if (
            ((contraindicationStates.find {it.Id == id})?.isSelected == false) and
            (contraindicationStates.count { it.isSelected } >= 3)
        ){
            return
        }
        contraindicationStates.find { it.Id == id }?.let { item ->
            item.isSelected = !item.isSelected
        }
    }

    fun onDeleteIndication(id: Int){
        indicationStates.find { it.Id == id }?.let { item ->
            item.isSelected = false
        }
    }

    fun onDeleteContraindication(id: Int){
        contraindicationStates.find { it.Id == id }?.let { item ->
            item.isSelected = false
        }
    }

    fun updateSelectableParameter(selectableParameter: SelectableParameterState){
        selectableParameterState = selectableParameter
    }

    fun onEvaluationContraindicationCheck(number: Int){
        evaluationContraindicationStates.find { it.isSelected }?.let { item ->
            item.isSelected = false
        }
        evaluationContraindicationStates.find { it.Number == number }?.let { item ->
            item.isSelected = true
        }
    }

    fun onEvaluationPriceCheck(number: Int){
        evaluationPriceStates.find { it.isSelected }?.let { item ->
            item.isSelected = false
        }
        evaluationPriceStates.find { it.Number == number }?.let { item ->
            item.isSelected = true
        }
    }

    fun onEvaluationAssessmentCheck(number: Int){
        evaluationAssessmentStates.find { it.isSelected }?.let { item ->
            item.isSelected = false
        }
        evaluationAssessmentStates.find { it.Number == number }?.let { item ->
            item.isSelected = true
        }
    }

    fun onEvaluationReviewsCheck(number: Int){
        evaluationReviewsStates.find { it.isSelected }?.let { item ->
            item.isSelected = false
        }
        evaluationReviewsStates.find { it.Number == number }?.let { item ->
            item.isSelected = true
        }
    }

    fun getParamaters(){
        viewModelScope.launch {
            selectionUiState = SelectionUiState.Loading
            try {
                productParameterRepository.getIndications().forEach() {
                    item ->
                    indicationStates.add(ParameterListModel(Id = item.id ?: 0, Name = item.name ?: ""))
                }
                productParameterRepository.getContraindications().forEach() {
                        item ->
                    contraindicationStates.add(ParameterListModel(Id = item.id ?: 0, Name = item.name ?: ""))
                }

                selectionUiState = SelectionUiState.Success
            }
            catch (e: IOException){
                selectionUiState = SelectionUiState.Error
            }
            catch (e: HttpException){
                selectionUiState = SelectionUiState.Error
            }
        }
    }

    fun getSelectionModel(): SelectionParameterModel{
        var model = SelectionParameterModel(
            IndicationId = indicationStates.find { item -> item.isSelected }?.Id,
            ContraindicationIds = contraindicationStates.filter { item -> item.isSelected }.map { item -> item.Id },
            PriseSort = when(priceUiState){
                is AscDescUiState.Ascending -> true
                is AscDescUiState.Decreasing -> false
                is AscDescUiState.Ignore -> null
            },
            AssessmentSort = when(assessmentUiState){
                is AscDescUiState.Ascending -> true
                is AscDescUiState.Decreasing -> false
                is AscDescUiState.Ignore -> null
            },
            ReviewsSort = when(reviewsUiState){
                is AscDescUiState.Ascending -> true
                is AscDescUiState.Decreasing -> false
                is AscDescUiState.Ignore -> null
            },
            CountResults = when(countUiState){
                is CountUiState.Five -> 5
                is CountUiState.Ten -> 10
                is CountUiState.All -> null
            },
            Availability = when(availabilityUiState){
                is AvailabilityUiState.Show -> true
                is AvailabilityUiState.NotShow -> false
            },
            evaluationContraindication = evaluationContraindicationStates.find { item -> item.isSelected }?.Number,
            evaluationPrise = evaluationPriceStates.find { item -> item.isSelected }?.Number,
            evaluationAssessment = evaluationAssessmentStates.find { item -> item.isSelected }?.Number,
            evaluationReviews = evaluationReviewsStates.find { item -> item.isSelected }?.Number
        )
        viewModelScope.launch {
            try {
                val selectionParameterJson = Gson().toJson(model)
                var models = productRepository.selectProducts(selectionParameterJson)
            }
            catch (e: IOException){
                selectionUiState = SelectionUiState.Error
            }
            catch (e: HttpException){
                selectionUiState = SelectionUiState.Error
            }
        }
        return model
    }

    init {
        getParamaters()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DisApplication)
                var productParameterRepository = application.container.productParameterRepository
                var productRepository = application.container.productRepository
                SelectionProductViewModel(productParameterRepository = productParameterRepository, productRepository)
            }
        }
    }
}