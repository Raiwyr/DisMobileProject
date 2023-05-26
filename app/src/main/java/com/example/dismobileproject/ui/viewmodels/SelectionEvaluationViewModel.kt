package com.example.dismobileproject.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dismobileproject.DisApplication
import com.example.dismobileproject.data.model.SelectionParameterModel
import com.example.dismobileproject.data.repositories.ProductRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class EvaluationListModel(
    val Number: Int,
    initialChecked: Boolean = false
) {
    var isSelected by mutableStateOf(initialChecked)
}

sealed interface InitSelectionEvaluation{
    object Init: InitSelectionEvaluation
    object NoInit: InitSelectionEvaluation
}

class SelectionEvaluationViewModel(
    private  val productRepository: ProductRepository
): ViewModel() {

    var initViewModel: InitSelectionEvaluation by mutableStateOf(InitSelectionEvaluation.NoInit)
        private set

    //region
    var contraindicationEnabledState by mutableStateOf(false)
        private set

    var sideEffectEnabledState by mutableStateOf(false)
        private set

    var priceEnabledState by mutableStateOf(false)
        private set

    var assessmentEnabledState by mutableStateOf(false)
        private set

    var reviewsEnabledState by mutableStateOf(false)
        private set
    //endregion

    //region evaluation states
    val evaluationContraindicationStates by mutableStateOf((1..10).map { EvaluationListModel(Number = it) }.toMutableList())

    val evaluationSideEffectStates by mutableStateOf((1..10).map { EvaluationListModel(Number = it) }.toMutableList())

    val evaluationPriceStates by mutableStateOf((1..10).map { EvaluationListModel(Number = it) }.toMutableList())

    val evaluationAssessmentStates by mutableStateOf((1..10).map { EvaluationListModel(Number = it) }.toMutableList())

    val evaluationReviewsStates by mutableStateOf((1..10).map { EvaluationListModel(Number = it) }.toMutableList())
    //endregion

    fun initializeViewModel(model: SelectionParameterModel){
        if((model.ContraindicationIds?.count() ?: 0) > 0)
            contraindicationEnabledState = true
        if((model.SideEffectIds?.count() ?: 0) > 0)
            sideEffectEnabledState = true
        if(model.PriseSort != null)
            priceEnabledState = true
        if(model.AssessmentSort != null)
            assessmentEnabledState = true
        if(model.ReviewsSort != null)
            reviewsEnabledState = true
    }

    fun onEvaluationContraindicationCheck(number: Int){
        evaluationContraindicationStates.find { it.isSelected }?.let { item ->
            item.isSelected = false
        }
        evaluationContraindicationStates.find { it.Number == number }?.let { item ->
            item.isSelected = true
        }
    }

    fun onEvaluationSideEffectCheck(number: Int){
        evaluationSideEffectStates.find { it.isSelected }?.let { item ->
            item.isSelected = false
        }
        evaluationSideEffectStates.find { it.Number == number }?.let { item ->
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

    fun getSelectionModel(model: SelectionParameterModel): SelectionParameterModel{
        model.evaluationContraindication = evaluationContraindicationStates.find { item -> item.isSelected }?.Number
        model.evaluationSideEffect = evaluationSideEffectStates.find { item -> item.isSelected }?.Number
        model.evaluationPrise = evaluationPriceStates.find { item -> item.isSelected }?.Number
        model.evaluationAssessment = evaluationAssessmentStates.find { item -> item.isSelected }?.Number
        model.evaluationReviews = evaluationReviewsStates.find { item -> item.isSelected }?.Number
        return model
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DisApplication)
                var productRepository = application.container.productRepository
                SelectionEvaluationViewModel(productRepository = productRepository)
            }
        }
    }
}