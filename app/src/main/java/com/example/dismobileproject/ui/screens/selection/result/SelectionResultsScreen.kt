package com.example.dismobileproject.ui.screens.selection.result

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dismobileproject.data.model.SelectionParameterModel
import com.example.dismobileproject.ui.navigation.Screen
import com.example.dismobileproject.ui.screens.common.ErrorScreen
import com.example.dismobileproject.ui.screens.common.LoadingScreen
import com.example.dismobileproject.ui.screens.common.NotResultsScreen
import com.example.dismobileproject.ui.screens.produtclist.product.ProductListScreen
import com.example.dismobileproject.ui.screens.produtclist.product.filter.FilterScreen
import com.example.dismobileproject.ui.viewmodels.*
import com.example.dismobileproject.ui.widgets.OnlyBackBar
import com.example.dismobileproject.ui.widgets.SearchBar
import kotlinx.coroutines.launch

@Composable
fun SelectionResultsScreen(
    selectionModel: SelectionParameterModel?,
    navController: NavController
){
    var selectionResultsViewModel: SelectionResultsViewModel = viewModel(factory = SelectionResultsViewModel.Factory)
    var selectionProductUiState = selectionResultsViewModel.selectionProductUiState
    var initState = selectionResultsViewModel.initState

    if(initState == InitState.NoInit) {
        selectionResultsViewModel.getProducts(selectionModel)
    }

    Scaffold(
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
            when(selectionProductUiState){
                is SelectionProductUiState.Loading -> LoadingScreen()
                is SelectionProductUiState.Success -> ProductListScreen(products = selectionProductUiState.products, navController = navController)
                is SelectionProductUiState.Error -> ErrorScreen(retryAction = { selectionResultsViewModel.getProducts(selectionModel) })
                is SelectionProductUiState.NoResult -> NotResultsScreen()
            }
        }
    }
}