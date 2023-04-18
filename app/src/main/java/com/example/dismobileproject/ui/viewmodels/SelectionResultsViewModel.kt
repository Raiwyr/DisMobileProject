package com.example.dismobileproject.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dismobileproject.DisApplication
import com.example.dismobileproject.data.model.ProductHeaderModel
import com.example.dismobileproject.data.model.ProductModel
import com.example.dismobileproject.data.model.SelectionParameterModel
import com.example.dismobileproject.data.repositories.ProductRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface SelectionProductUiState{
    data class Success(val products: List<ProductHeaderModel>): SelectionProductUiState
    object Loading: SelectionProductUiState
    object NoResult: SelectionProductUiState
    object Error: SelectionProductUiState
}

sealed interface InitState{
    object Init: InitState
    object NoInit: InitState
}

class SelectionResultsViewModel(
    private  val productRepository: ProductRepository
): ViewModel() {

    var selectionProductUiState: SelectionProductUiState by mutableStateOf(SelectionProductUiState.Loading)
        private set

    var initState: InitState by mutableStateOf(InitState.NoInit)
        private set

    fun getProducts(selectionModel: SelectionParameterModel?){
        if(selectionModel == null)
            return
        if(initState == InitState.NoInit)
            initState = InitState.Init
        viewModelScope.launch {
            selectionProductUiState = SelectionProductUiState.Loading
            selectionProductUiState = try {
                val selectionParameterJson = Gson().toJson(selectionModel)
                var productList = productRepository.selectProducts(selectionParameterJson)
                if (productList.isEmpty())
                    SelectionProductUiState.NoResult
                else
                    SelectionProductUiState.Success(productList)
            }
            catch (e: IOException){
                SelectionProductUiState.Error
            }
            catch (e: HttpException){
                SelectionProductUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DisApplication)
                var productRepository = application.container.productRepository
                SelectionResultsViewModel(productRepository = productRepository)
            }
        }
    }
}