package com.example.dismobileproject.ui.screens.profile.order

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.dismobileproject.data.model.OrderModel
import com.example.dismobileproject.ui.screens.profile.shopcart.ShopCartCard

@Composable
fun OrderListScreen(
    orders: List<OrderModel>
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ){
        itemsIndexed(orders){
                _, order ->
            OrderCard(order = order)
        }
    }
}