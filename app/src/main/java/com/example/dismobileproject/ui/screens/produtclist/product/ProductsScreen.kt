package com.example.dismobileproject.ui.screens.produtclist.product

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

@ExperimentalLayoutApi
@ExperimentalMaterialApi
@Composable
fun ProductsScreen(
    navController: NavController,
    searchText: String? = null,
    categoryId: Int? = null
){
    var viewModel: ProductsViewModel = viewModel(factory = ProductsViewModel.Factory)
    var productUiState = viewModel.productUiState
    var getProductState = viewModel.getProductState
    var filterState = viewModel.filterState

    var text by remember { mutableStateOf (searchText ?: "") }
    var category by remember { mutableStateOf (categoryId ?: -1) }

    if(getProductState == GetProductState.NoInit) {
        if (!searchText.isNullOrEmpty()) {
            viewModel.InitViewModel(text)
        } else if ((categoryId ?: -1) > 0) {
            viewModel.InitViewModel(category)
        }
    }

    var coroutineScope = rememberCoroutineScope()
    var skipHalfExpanded by remember { mutableStateOf(true) }
    val bottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden, skipHalfExpanded = skipHalfExpanded)

    ModalBottomSheetLayout(
        sheetState = bottomState,
        sheetElevation = 8.dp,
        sheetShape = RoundedCornerShape(
            bottomStart = 0.dp,
            bottomEnd = 0.dp,
            topStart = 12.dp,
            topEnd = 12.dp
        ),
        sheetContent = {
            FilterScreen(viewModel, text, category)
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
                    is ProductUiState.Success ->{
                        ProductListScreen(products = productUiState.productSearch, navController = navController)
                        if(filterState == FilterState.NoInit)
                            viewModel.getFilters(productUiState.productSearch.map { it.id!! })
                    }
                    is ProductUiState.Error -> ErrorScreen(retryAction = {
                        when(getProductState){
                            is GetProductState.Search -> {
                                when(viewModel.filterSetState){
                                    is FilterSetState.Set -> viewModel.getProductsByFilter(text)
                                    is FilterSetState.NoSet -> viewModel.getProducts(text)
                                }
                            }
                            is GetProductState.Category -> {
                                when(viewModel.filterSetState){
                                    is FilterSetState.Set -> viewModel.getProductsByFilter(category)
                                    is FilterSetState.NoSet -> viewModel.getProducts(category)
                                }
                            }
                            is GetProductState.NoInit -> {}
                        }
                    })
                    is ProductUiState.NoResult -> NotResultsScreen()
                }
            }
        }
    }
}