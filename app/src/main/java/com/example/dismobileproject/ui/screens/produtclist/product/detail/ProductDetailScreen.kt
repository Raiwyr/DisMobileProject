package com.example.dismobileproject.ui.screens.produtclist.product.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
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
import com.example.dismobileproject.ui.screens.produtclist.product.filter.FilterScreen
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun ProductDetailScreen(
    navController: NavController,
    productId: Int?
) {
    var viewModel: ProductDetailViewModel = viewModel(factory = ProductDetailViewModel.Factory)
    var productDetailUiState = viewModel.productDetailUiState
    var initProductDetailState = viewModel.initProductDetailState

    var productId by remember { mutableStateOf (productId ?: -1 )}

    if(initProductDetailState == InitProductDetailState.NoInit)
        viewModel.initViewModel(productId)

    val focusManager = LocalFocusManager.current

    val sheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden,
        confirmStateChange = {
            if(it == ModalBottomSheetValue.Hidden)
                focusManager.clearFocus()
            true
        }
    )
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetElevation = 8.dp,
        sheetShape = RoundedCornerShape(
            bottomStart = 0.dp,
            bottomEnd = 0.dp,
            topStart = 12.dp,
            topEnd = 12.dp
        ),
        sheetContent = {
            ReviewWriteScreen(viewModel::postReview)
        }
    ){
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
                    is ProductDetailUiState.Success -> ProductDescriptionScreen(
                        product = productDetailUiState.product,
                        onWriteReviewAction = {
                            coroutineScope.launch { sheetState.animateTo(ModalBottomSheetValue.Expanded) }
                        }
                    )
                    is ProductDetailUiState.Error -> ErrorScreen(retryAction = { viewModel.getProduct(productId) })
                    is ProductDetailUiState.NoResult -> NotResultsScreen()
                }
            }
        }
    }
}