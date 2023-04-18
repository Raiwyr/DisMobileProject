package com.example.dismobileproject.ui.screens.catalog

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dismobileproject.data.repositories.CategoryUiState
import com.example.dismobileproject.data.repositories.CategoryViewModel
import com.example.dismobileproject.ui.navigation.Router
import com.example.dismobileproject.ui.navigation.Screen
import com.example.dismobileproject.ui.screens.common.ErrorScreen
import com.example.dismobileproject.ui.screens.common.LoadingScreen
import com.example.dismobileproject.ui.screens.common.NotResultsScreen
import com.example.dismobileproject.ui.screens.produtclist.product.ProductListScreen
import com.example.dismobileproject.ui.viewmodels.GetProductState
import com.example.dismobileproject.ui.viewmodels.ProductUiState
import com.example.dismobileproject.ui.viewmodels.ProductsViewModel
import com.example.dismobileproject.ui.widgets.SearchBar
import kotlinx.coroutines.launch

@Composable
fun CategoryScreen(
    navController: NavController
) {
    var categoryViewModel: CategoryViewModel = viewModel(factory = CategoryViewModel.Factory)
    var categoryUiState = categoryViewModel.categoryUiState

    Scaffold(
        topBar = {
            SearchBar(
                searchText = "",
                isEnabledSearch = false,
                isDisplayFilter = false,
                isDisplayBackButton = false,
                onSearchFieldClick = {navController.navigate((Screen.Search.screenName))}
            )
        }
    ) {
            paddingValues ->
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)){
            when(categoryUiState){
                is CategoryUiState.Loading -> LoadingScreen()
                is CategoryUiState.Success -> CategoryListScreen(navController, categories = categoryUiState.categories)
                is CategoryUiState.Error -> ErrorScreen(retryAction = { categoryViewModel.getCategories() })
                is CategoryUiState.NoResult -> NotResultsScreen()
            }
        }
    }
}