package com.example.dismobileproject.ui.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dismobileproject.DisApplication
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
    object SideEffect: SelectableParameterState
    object Non: SelectableParameterState
}

class ParameterListModel(
    val Id: Int,
    val Name: String,
    initialChecked: Boolean = false
) {
    var isSelected by mutableStateOf(initialChecked)
}

class SelectionParameterViewModel(
    private  val productParameterRepository: ProductParameterRepository,
    private  val productRepository: ProductRepository
): ViewModel(){

    var selectionUiState: SelectionUiState by mutableStateOf(SelectionUiState.Loading)
        private set

    var selectableParameterState: SelectableParameterState by mutableStateOf(SelectableParameterState.Non)
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

    var sideEffectStates by mutableStateOf(mutableListOf<ParameterListModel>())
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

    fun onSideEffectCheck(id: Int){
        if (
            ((sideEffectStates.find {it.Id == id})?.isSelected == false) and
            (sideEffectStates.count { it.isSelected } >= 3)
        ){
            return
        }
        sideEffectStates.find { it.Id == id }?.let { item ->
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

    fun onDeleteSideEffect(id: Int){
        sideEffectStates.find { it.Id == id }?.let { item ->
            item.isSelected = false
        }
    }

    fun updateSelectableParameter(selectableParameter: SelectableParameterState){
        selectableParameterState = selectableParameter
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
                productParameterRepository.getSideEffects().forEach() {
                        item ->
                    sideEffectStates.add(ParameterListModel(Id = item.id ?: 0, Name = item.name ?: ""))
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
            SideEffectIds = sideEffectStates.filter { item -> item.isSelected }.map { item -> item.Id },
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
            evaluationContraindication = null,
            evaluationSideEffect = null,
            evaluationPrise = null,
            evaluationAssessment = null,
            evaluationReviews = null
        )
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
                SelectionParameterViewModel(productParameterRepository = productParameterRepository, productRepository)
            }
        }
    }
}