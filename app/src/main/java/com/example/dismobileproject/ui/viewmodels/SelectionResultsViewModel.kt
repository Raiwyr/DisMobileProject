package com.example.dismobileproject.ui.viewmodels

import androidx.compose.runtime.*
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
import com.example.dismobileproject.data.repositories.UserRepository
import com.example.dismobileproject.ui.viewmodels.models.ProductListModel
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface SelectionProductUiState{
    object Success: SelectionProductUiState
    object Loading: SelectionProductUiState
    object NoResult: SelectionProductUiState
    object Error: SelectionProductUiState
}

sealed interface InitState{
    object Init: InitState
    object NoInit: InitState
}

class SelectionResultsViewModel(
    private  val productRepository: ProductRepository,
    private  val userRepository: UserRepository
): ViewModel() {

    var selectionProductUiState: SelectionProductUiState by mutableStateOf(SelectionProductUiState.Loading)
        private set

    var initState: InitState by mutableStateOf(InitState.NoInit)
        private set

    var productList by mutableStateOf(mutableStateListOf<ProductListModel>())
        private set

    fun getProducts(selectionModel: SelectionParameterModel?, userId: Int?){
        if(selectionModel == null)
            return
        if(initState == InitState.NoInit)
            initState = InitState.Init
        viewModelScope.launch {
            selectionProductUiState = SelectionProductUiState.Loading
            selectionProductUiState = try {
                var products = productRepository.selectProducts(selectionModel)
                if (products.isEmpty())
                    SelectionProductUiState.NoResult
                else{
                    var productModels = products.map {
                        ProductListModel(
                            id = it.id ?: 0,
                            name = it.name ?: "",
                            price = it.price ?: 0,
                            assessment = it.assessment ?: 0,
                            count = it.count ?: 0,
                            imageName = it.imageName ?: ""
                        )
                    }
                    if (userId != null && userId >= 0){
                        var shopCart = userRepository.getShopCart(userId)
                        for(index in productModels.indices){
                            if(shopCart.find { it.id == productModels[index].id } != null)
                                productModels[index].isAddToShopCart = true
                        }
                    }

                    productList = productModels.toMutableStateList()

                    SelectionProductUiState.Success
                }
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
                var userRepository = application.container.userRepository
                SelectionResultsViewModel(
                    productRepository = productRepository,
                    userRepository = userRepository
                )
            }
        }
    }
}