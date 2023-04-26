package com.example.dismobileproject.ui.screens.profile.order

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dismobileproject.R
import com.example.dismobileproject.ui.preference.getLoggedUserId
import com.example.dismobileproject.ui.screens.common.ErrorScreen
import com.example.dismobileproject.ui.screens.common.LoadingScreen
import com.example.dismobileproject.ui.screens.common.NotResultsScreen
import com.example.dismobileproject.ui.viewmodels.OrderUiState
import com.example.dismobileproject.ui.viewmodels.OrderViewModel
import com.example.dismobileproject.ui.viewmodels.ShopCartUiState
import com.example.dismobileproject.ui.viewmodels.ShoppingCartViewModel
import com.example.dismobileproject.ui.widgets.OnlyBackBar

@Composable
fun OrderScreen(
    navController: NavController,
    completedOrders: Boolean?
){
    var viewModel: OrderViewModel = viewModel(factory = OrderViewModel.Factory)

    var orderUiState = viewModel.orderUiState

    var userId = getLoggedUserId(LocalContext.current)

    if(!viewModel.initState)
        viewModel.initViewModel(userId, completedOrders ?: false)

    Scaffold(
        topBar = {
            OnlyBackBar(
                onBackClick = { navController.popBackStack() }
            )
        }
    ){
            paddingValues ->
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)){
            when(orderUiState){
                is OrderUiState.Loading -> LoadingScreen()
                is OrderUiState.Success -> { OrderListScreen(viewModel.orderList) }
                is OrderUiState.Error -> ErrorScreen(retryAction = {
                    if(userId >= 0)
                        viewModel.getOrders(userId, completedOrders ?: false)
                })
                is OrderUiState.NoResult -> NotResultsScreen()
            }
        }
    }
}