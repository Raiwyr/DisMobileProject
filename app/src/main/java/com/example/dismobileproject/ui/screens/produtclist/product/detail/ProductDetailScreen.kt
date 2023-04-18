package com.example.dismobileproject.ui.screens.produtclist.product.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dismobileproject.data.repositories.CategoryUiState
import com.example.dismobileproject.ui.screens.catalog.CategoryListScreen
import com.example.dismobileproject.ui.screens.common.ErrorScreen
import com.example.dismobileproject.ui.screens.common.LoadingScreen
import com.example.dismobileproject.ui.screens.common.NotResultsScreen
import com.example.dismobileproject.ui.viewmodels.InitProductDetailState
import com.example.dismobileproject.ui.viewmodels.ProductDetailUiState
import com.example.dismobileproject.ui.viewmodels.ProductDetailViewModel
import com.example.dismobileproject.ui.widgets.OnlyBackBar
import com.example.dismobileproject.R

@ExperimentalMaterialApi
@Composable
fun ProductDetailScreen(
    navController: NavController,
    productId: Int?
) {
    var productDetailViewModel: ProductDetailViewModel = viewModel(factory = ProductDetailViewModel.Factory)
    var productDetailUiState = productDetailViewModel.productDetailUiState
    var initProductDetailState = productDetailViewModel.initProductDetailState

    var productId by remember { mutableStateOf (productId ?: -1 )}

    if(initProductDetailState == InitProductDetailState.NoInit)
        productDetailViewModel.initViewModel(productId)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            OnlyBackBar(
                onBackClick = { navController.popBackStack() }
            )
        }
    ) {
            paddingValues ->
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)){
            when(productDetailUiState){
                is ProductDetailUiState.Loading -> LoadingScreen()
                is ProductDetailUiState.Success -> ProductDescriptionScreen(product = productDetailUiState.product)
                is ProductDetailUiState.Error -> ErrorScreen(retryAction = { productDetailViewModel.getProduct(productId) })
                is ProductDetailUiState.NoResult -> NotResultsScreen()
            }
        }
    }
}