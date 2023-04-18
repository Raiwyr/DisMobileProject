package com.example.dismobileproject.ui.screens.produtclist.product

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.dismobileproject.R
import com.example.dismobileproject.data.model.ProductHeaderModel
import com.example.dismobileproject.data.model.ProductModel
import com.example.dismobileproject.ui.navigation.Screen

@Composable
fun ProductListScreen(
    navController: NavController,
    products: List<ProductHeaderModel>,
    modifier: Modifier = Modifier
){
    val productIdRoute = stringResource(R.string.route_param_product_id)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
//            .verticalScroll(
//                rememberScrollState()
//            )
    ){
        itemsIndexed(products){
            _, product ->
            ProductsCard(
                product = product,
                onButtonClick = {},
                onCardClick = {navController.navigate(Screen.ProductDetail.screenName + "?$productIdRoute=${product.id}")}
            )
        }
    }
}