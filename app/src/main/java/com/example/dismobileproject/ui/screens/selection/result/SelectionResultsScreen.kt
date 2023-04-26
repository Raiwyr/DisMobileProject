package com.example.dismobileproject.ui.screens.selection.result

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dismobileproject.data.model.SelectionParameterModel
import com.example.dismobileproject.ui.navigation.Screen
import com.example.dismobileproject.ui.preference.getLoggedUserId
import com.example.dismobileproject.ui.preference.isUserLogged
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
    var viewModel: SelectionResultsViewModel = viewModel(factory = SelectionResultsViewModel.Factory)
    var selectionProductUiState = viewModel.selectionProductUiState
    var initState = viewModel.initState

    var userId: Int? = null
    if(isUserLogged(LocalContext.current))
        userId = getLoggedUserId(LocalContext.current)

    if(initState == InitState.NoInit) {
        viewModel.getProducts(selectionModel, userId)
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
                is SelectionProductUiState.Success -> ProductListScreen(products = viewModel.productList, navController = navController)
                is SelectionProductUiState.Error -> ErrorScreen(retryAction = { viewModel.getProducts(selectionModel, userId) })
                is SelectionProductUiState.NoResult -> NotResultsScreen()
            }
        }
    }
}