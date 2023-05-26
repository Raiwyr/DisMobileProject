package com.example.dismobileproject.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dismobileproject.DisApplication
import com.example.dismobileproject.data.repositories.ProductRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface DataUiState
{
    object Loading: DataUiState
    object Success: DataUiState
    object Error: DataUiState
}

class ProductCardViewModel(
    private val productRepository: ProductRepository
): ViewModel() {

    var imageLoadState: DataUiState by mutableStateOf(DataUiState.Loading)

    var initViewModel by mutableStateOf(false)

    var byteImageState: ByteArray by mutableStateOf(byteArrayOf())

    fun initViewModel(name: String){
        getImage(name)
        initViewModel = true
    }

    fun getImage(name: String){
        imageLoadState = DataUiState.Loading
        viewModelScope.launch {
            try {

                if(name.isEmpty()) {
                    imageLoadState = DataUiState.Error
                    return@launch
                }

                var byteImage = productRepository.getProductImage(name)

                if(byteImage.isNotEmpty()){
                    byteImageState = byteImage
                    imageLoadState = DataUiState.Success
                }
                else{
                    imageLoadState = DataUiState.Error
                }
            }
            catch (e: IOException){
                imageLoadState = DataUiState.Error
            }
            catch (e: HttpException){
                imageLoadState = DataUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DisApplication)
                var productRepository = application.container.productRepository
                ProductCardViewModel(
                    productRepository = productRepository
                )
            }
        }
    }
}