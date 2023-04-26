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
import com.example.dismobileproject.data.model.OrderModel
import com.example.dismobileproject.data.repositories.UserRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface OrderUiState{
    object Success: OrderUiState
    object Loading: OrderUiState
    object NoResult: OrderUiState
    object Error: OrderUiState
}

class OrderViewModel(
    private  val userRepository: UserRepository
): ViewModel() {

    var orderUiState: OrderUiState by mutableStateOf(OrderUiState.Loading)
        private set

    var orderList by mutableStateOf(mutableListOf<OrderModel>())

    var initState by mutableStateOf(false)

    fun initViewModel(userId: Int, getCompletedOrders: Boolean){
        initState = true
        getOrders(userId, getCompletedOrders)
    }

    fun getOrders(userId: Int, getCompletedOrders: Boolean){
        viewModelScope.launch {
            orderUiState = OrderUiState.Loading
            orderUiState = try {
                var orders =
                    if (getCompletedOrders)
                        userRepository.getCompletedOrders(userId)
                    else
                        userRepository.getNotCompletedOrders(userId)
                if (orders.isEmpty())
                    OrderUiState.NoResult
                else {
                    orderList = orders.toMutableList()
                    OrderUiState.Success
                }
            }
            catch (e: IOException){
                OrderUiState.Error
            }
            catch (e: HttpException){
                OrderUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DisApplication)
                var userRepository = application.container.userRepository
                OrderViewModel(userRepository = userRepository)
            }
        }
    }
}