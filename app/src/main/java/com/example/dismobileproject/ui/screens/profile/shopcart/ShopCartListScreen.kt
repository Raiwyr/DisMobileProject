package com.example.dismobileproject.ui.screens.profile.shopcart

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.dismobileproject.data.model.ProductHeaderModel
import com.example.dismobileproject.ui.navigation.Screen
import com.example.dismobileproject.ui.screens.produtclist.product.ProductsCard
import com.example.dismobileproject.ui.viewmodels.ProductShopCart
import com.example.dismobileproject.ui.viewmodels.ShoppingCartViewModel

@Composable
fun ShopCartListScreen(
    viewModel: ShoppingCartViewModel
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ){
        itemsIndexed(viewModel.shopCartList){
                _, product ->
            ShopCartCard(
                product = product,
                onCheckedChange = { viewModel.onProductCheck(product.id) },
                onMinusClick = { viewModel.reduceCountProduct(product.id) },
                onPlusClick = { viewModel.increaseCountProduct(product.id) }
            )
        }
    }
}