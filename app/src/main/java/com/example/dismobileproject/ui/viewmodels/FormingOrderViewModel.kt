package com.example.dismobileproject.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dismobileproject.DisApplication
import com.example.dismobileproject.data.model.ProductToOrderModel
import com.example.dismobileproject.data.repositories.UserRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class FormingOrderViewModel(
    private  val userRepository: UserRepository
): ViewModel() {

    fun orderProducts(userId: Int, products: List<ProductShopCartToOrder>){
        viewModelScope.launch {
            try {
                var productList = products.map {
                    ProductToOrderModel(
                        Id = it.id,
                        Count = it.selectedCount
                    )
                }

                userRepository.postProductToOrder(userId, productList)
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
                FormingOrderViewModel(userRepository = userRepository)
            }
        }
    }
}