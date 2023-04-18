package com.example.dismobileproject.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.InitializerViewModelFactoryBuilder
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dismobileproject.DisApplication
import androidx.lifecycle.viewmodel.initializer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.dismobileproject.data.model.ProductHeaderModel
import com.example.dismobileproject.data.model.ProductModel
import com.example.dismobileproject.data.repositories.ProductRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface ProductUiState{
    data class Success(val productSearch: List<ProductHeaderModel>): ProductUiState
    object Loading: ProductUiState
    object NoResult: ProductUiState
    object Error: ProductUiState
}

sealed interface GetProductState{
    object Search: GetProductState
    object Category: GetProductState
    object SelectionResult: GetProductState
    object NoInit: GetProductState
}


class ProductsViewModel(
    private  val productRepository: ProductRepository
): ViewModel() {

    var productUiState: ProductUiState by mutableStateOf(ProductUiState.Loading)
        private set

    var getProductState: GetProductState by mutableStateOf(GetProductState.NoInit)
        private set

    fun InitViewModel(parameter: String){
        getProductState = GetProductState.Search
        getProducts(parameter)
    }

    fun InitViewModel(parameter: Int){
        getProductState = GetProductState.Category
        getProducts(parameter)
    }

    fun InitViewModel(parameter: List<Int>){
        getProductState = GetProductState.SelectionResult
        getProducts(parameter)
    }

    fun getProducts(searchText: String){
        viewModelScope.launch {
            productUiState = ProductUiState.Loading
            productUiState = try {
                var productList = productRepository.searchProducts(searchText)
                if (productList.isEmpty())
                    ProductUiState.NoResult
                else
                    ProductUiState.Success(productList)
            }
            catch (e: IOException){
                ProductUiState.Error
            }
            catch (e: HttpException){
                ProductUiState.Error
            }
        }
    }

    fun getProducts(categoryId: Int){
        viewModelScope.launch {
            productUiState = ProductUiState.Loading
            productUiState = try {
                var productList = productRepository.getProductByCategoryId(categoryId)
                if (productList.isEmpty())
                    ProductUiState.NoResult
                else
                    ProductUiState.Success(productList)
            }
            catch (e: IOException){
                ProductUiState.Error
            }
            catch (e: HttpException){
                ProductUiState.Error
            }
        }
    }

    fun getProducts(productIds: List<Int>){
        /*TODO:Loading products by ids*/
    }



    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as DisApplication)
                var productRepository = application.container.productRepository
                ProductsViewModel(productRepository = productRepository)
            }
        }
    }
}