package com.example.dismobileproject.ui.screens.produtclist.product

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dismobileproject.R
import com.example.dismobileproject.data.model.ProductHeaderModel
import com.example.dismobileproject.data.model.ProductModel
import com.example.dismobileproject.ui.navigation.Screen
import com.example.dismobileproject.ui.preference.getLoggedUserId
import com.example.dismobileproject.ui.viewmodels.OrderViewModel
import com.example.dismobileproject.ui.viewmodels.ProductListViewModel
import com.example.dismobileproject.ui.viewmodels.models.ProductListModel

@Composable
fun ProductListScreen(
    navController: NavController,
    products: List<ProductListModel>,
    modifier: Modifier = Modifier
){
    var viewModel: ProductListViewModel = viewModel(factory = ProductListViewModel.Factory)

    val productIdRoute = stringResource(R.string.route_param_product_id)

    var userId = getLoggedUserId(LocalContext.current)

    if(!viewModel.initView)
        viewModel.setList(products)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
//            .verticalScroll(
//                rememberScrollState()
//            )
    ){
        itemsIndexed(viewModel.productList){
            _, product ->
            ProductsCard(
                product = product,
                onButtonClick = {
                    if(!product.isAddToShopCart){
                        viewModel.addProductToShopCart(userId, product.id)
                    }
                },
                onCardClick = {navController.navigate(Screen.ProductDetail.screenName + "?$productIdRoute=${product.id}")}
            )
        }
    }
}