package com.example.dismobileproject.ui.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dismobileproject.DisApplication
import com.example.dismobileproject.data.repositories.UserRepository
import com.example.dismobileproject.ui.viewmodels.models.ProductListModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ProductListViewModel(
    private  val userRepository: UserRepository
): ViewModel() {

    var initView by mutableStateOf(false)

    var productList by mutableStateOf(mutableStateListOf<ProductListModel>())
        private set

    fun setList(products: List<ProductListModel>){
        productList = products.toMutableStateList()
        initView = true
    }

    fun addProductToShopCart(userId: Int, productId: Int){
        viewModelScope.launch {
            try {
                userRepository.postProductToShopCart(userId, productId)
                productList.find { it.id == productId }?.let { item ->
                    item.isAddToShopCart = true
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
                ProductListViewModel(userRepository = userRepository)
            }
        }
    }
}