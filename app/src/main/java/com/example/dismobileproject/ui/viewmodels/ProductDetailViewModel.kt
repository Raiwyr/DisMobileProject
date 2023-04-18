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
import com.example.dismobileproject.data.model.ProductModel
import com.example.dismobileproject.data.repositories.ProductRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface ProductDetailUiState{
    data class Success(val product: ProductModel): ProductDetailUiState
    object Loading: ProductDetailUiState
    object NoResult: ProductDetailUiState
    object Error: ProductDetailUiState
}

sealed interface InitProductDetailState{
    object Init: InitProductDetailState
    object NoInit: InitProductDetailState
}

class ProductDetailViewModel(
    private val productRepository: ProductRepository
): ViewModel() {

    var productDetailUiState: ProductDetailUiState by mutableStateOf(ProductDetailUiState.Loading)
        private set

    var initProductDetailState: InitProductDetailState by mutableStateOf(InitProductDetailState.NoInit)
        private set

    fun  initViewModel(productId: Int){
        initProductDetailState = InitProductDetailState.Init
        getProduct(productId)
    }

    fun getProduct(productId: Int){
        viewModelScope.launch {
            productDetailUiState = ProductDetailUiState.Loading
            productDetailUiState = try {
                var product = productRepository.getProduct(productId)
                if (product == null)
                    ProductDetailUiState.NoResult
                else
                    ProductDetailUiState.Success(product)
            }
            catch (e: IOException){
                ProductDetailUiState.Error
            }
            catch (e: HttpException){
                ProductDetailUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DisApplication)
                var productRepository = application.container.productRepository
                ProductDetailViewModel(productRepository = productRepository)
            }
        }
    }
}