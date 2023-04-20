package com.example.dismobileproject.ui.screens.produtclist.product

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dismobileproject.ui.navigation.Screen
import com.example.dismobileproject.ui.screens.common.ErrorScreen
import com.example.dismobileproject.ui.screens.common.LoadingScreen
import com.example.dismobileproject.ui.screens.common.NotResultsScreen
import com.example.dismobileproject.ui.screens.produtclist.product.filter.FilterScreen
import com.example.dismobileproject.ui.viewmodels.*
import com.example.dismobileproject.ui.widgets.SearchBar
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ProductsScreen(
    navController: NavController,
    searchText: String? = null,
    listProductIds: List<Int>? = null,
    categoryId: Int? = null
){
    var productsViewModel: ProductsViewModel = viewModel(factory = ProductsViewModel.Factory)
    var productUiState = productsViewModel.productUiState
    var getProductState = productsViewModel.getProductState

    var text by remember { mutableStateOf (searchText ?: "") }
    var category by remember { mutableStateOf (categoryId ?: -1) }
    var productIds by remember { mutableStateOf (listProductIds ?: listOf()) }

    if(getProductState == GetProductState.NoInit) {
        if (!searchText.isNullOrEmpty()) {
            productsViewModel.InitViewModel(text)
        } else if ((categoryId ?: -1) > 0) {
            productsViewModel.InitViewModel(category)
        } else if (!productIds.isNullOrEmpty()){
            productsViewModel.InitViewModel(productIds)
        }
    }

    var coroutineScope = rememberCoroutineScope()
    val bottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetState = bottomState,
        sheetContent = {
            FilterScreen()
        }
    ) {
        Scaffold(
            topBar = {
                SearchBar(
                    searchText = text,
                    onBackClick = {
                        navController.popBackStack()
//                    navController.navigateUp()
                    },
                    isEnabledSearch = false,
                    isDisplayFilter = true,
                    onFilterClick = {
                        coroutineScope.launch { bottomState.show() }
                    },
                    onSearchFieldClick = {navController.navigate((Screen.Search.screenName))}
                )
            }
        ) {
                paddingValues ->
            Surface(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)){
                when(productUiState){
                    is ProductUiState.Loading -> LoadingScreen()
                    is ProductUiState.Success -> ProductListScreen(products = productUiState.productSearch, navController = navController)
                    is ProductUiState.Error -> ErrorScreen(retryAction = {
                        when(getProductState){
                            is GetProductState.Search -> productsViewModel.getProducts(text)
                            is GetProductState.Category -> productsViewModel.getProducts(category)
                            is GetProductState.SelectionResult -> productsViewModel.getProducts(productIds)
                            is GetProductState.NoInit -> {}
                        }
                    })
                    is ProductUiState.NoResult -> NotResultsScreen()
                }
            }
        }
    }
}