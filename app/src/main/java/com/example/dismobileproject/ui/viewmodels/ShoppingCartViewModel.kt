package com.example.dismobileproject.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.example.dismobileproject.data.model.ProductToOrderModel
import com.example.dismobileproject.data.repositories.UserRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface ShopCartUiState{
    object Success: ShopCartUiState
    object Loading: ShopCartUiState
    object NoResult: ShopCartUiState
    object Error: ShopCartUiState
}

class ProductShopCart(
    var id: Int,
    var name: String,
    var price: Int,
    var assessment: Int,
    var allCount: Int,
    var imageName: String,
    initialCount: Int = 1,
    initialChecked: Boolean = false
){
    var selectedCount by mutableStateOf(initialCount)
    var isSelected by mutableStateOf(initialChecked)
}

data class ProductShopCartToOrder(
    var id: Int,
    var name: String,
    var price: Int,
    var selectedCount: Int
)

class ShoppingCartViewModel(
    private  val userRepository: UserRepository
): ViewModel() {

    var shopCartUiState: ShopCartUiState by mutableStateOf(ShopCartUiState.Loading)
        private set

    var shopCartList by mutableStateOf(mutableStateListOf<ProductShopCart>())
        private set

    var initViewModel by  mutableStateOf(false)
        private set

    fun initViewModel(userId: Int){
        initViewModel = true
        getShopCart(userId)
    }

    fun noInit(){
        initViewModel = false
    }

    fun increaseCountProduct(id: Int){
        shopCartList.find { it.id == id }?.let { item ->
            item.selectedCount += 1
        }
    }

    fun reduceCountProduct(id: Int){
        shopCartList.find { it.id == id }?.let { item ->
            item.selectedCount -= 1
        }
    }

    fun onProductCheck(id: Int){
        shopCartList.find { it.id == id }?.let { item ->
            item.isSelected = !item.isSelected
        }
    }

    fun getShopCart(userId: Int){
        viewModelScope.launch {
            shopCartUiState = ShopCartUiState.Loading
            shopCartUiState = try {
                var productList = userRepository.getShopCart(userId)
                if (productList.isEmpty())
                    ShopCartUiState.NoResult
                else {
                    productList.forEach {
                        shopCartList.add(ProductShopCart(
                            id = it.id ?: -1,
                            name = it.name ?: "",
                            price = it.price ?: 0,
                            assessment = it.assessment ?: 0,
                            allCount = it.count ?: 0,
                            imageName = it.imageName ?: ""
                        ))
                    }
                    ShopCartUiState.Success
                }
            }
            catch (e: IOException){
                ShopCartUiState.Error
            }
            catch (e: HttpException){
                ShopCartUiState.Error
            }
        }
    }

    fun orderProducts(userId: Int){
        viewModelScope.launch {
            try {
                var productList = shopCartList.filter { it.isSelected }.map {
                    ProductToOrderModel(
                        Id = it.id,
                        Count = it.selectedCount
                    )
                }

                var result = userRepository.postProductToOrder(userId, productList)
                if(result){
                    productList.forEach {item ->
                        shopCartList.removeIf { it.id == item.Id }
                    }
                }
            }
            catch (e: IOException){
                return@launch
            }
            catch (e: HttpException){
                return@launch
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DisApplication)
                var userRepository = application.container.userRepository
                ShoppingCartViewModel(userRepository = userRepository)
            }
        }
    }
}