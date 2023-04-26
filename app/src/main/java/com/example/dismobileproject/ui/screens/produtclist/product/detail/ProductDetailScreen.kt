package com.example.dismobileproject.ui.screens.produtclist.product.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.dismobileproject.ui.preference.getLoggedUserId
import com.example.dismobileproject.ui.preference.isUserLogged
import com.example.dismobileproject.ui.screens.produtclist.product.filter.FilterScreen
import com.example.dismobileproject.ui.widgets.SimpleTextWithBorder
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

    var userId: Int? = null
    if(isUserLogged(LocalContext.current))
        userId = getLoggedUserId(LocalContext.current)

    var productId by remember { mutableStateOf (productId ?: -1 )}

    if(initProductDetailState == InitProductDetailState.NoInit)
        viewModel.initViewModel(productId, userId)

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
            },
            bottomBar = {
                if(productDetailUiState == ProductDetailUiState.Success){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Spacer(modifier = Modifier.weight(2f))
                        if(viewModel.productState?.isAddToShopCart != true){
                            Button(
                                onClick = {
                                    if(userId != null){
                                        viewModel.addProductToShopCart(userId)
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.action_element_color)),
                                shape = CircleShape,
                                modifier = Modifier
                                    .padding(top = 3.dp, bottom = 3.dp)
                                    .weight(6f)
                                    .requiredHeight(45.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.add_cart),
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp
                                )
                            }
                        }
                        else{
                            SimpleTextWithBorder(
                                modifier = Modifier
                                    .padding(top = 3.dp, bottom = 3.dp)
                                    .weight(6f)
                                    .requiredHeight(45.dp),
                                text = "В корзине",
                                textColor = colorResource(id = R.color.action_element_color),
                                fontSize = 20.sp
                            )
                        }
                        Spacer(modifier = Modifier.weight(2f))
                    }
                }
            }

        ) {
                paddingValues ->
            Surface(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)){
                when(productDetailUiState){
                    is ProductDetailUiState.Loading -> LoadingScreen()
                    is ProductDetailUiState.Success -> {
                        if(viewModel.productState != null){
                            ProductDescriptionScreen(
                                product = viewModel.productState!!,
                                onWriteReviewAction = {
                                    coroutineScope.launch { sheetState.animateTo(ModalBottomSheetValue.Expanded) }
                                }
                            )
                        }
                        else{
                            NotResultsScreen()
                        }
                    }
                    is ProductDetailUiState.Error -> ErrorScreen(retryAction = { viewModel.getProduct(productId, userId) })
                    is ProductDetailUiState.NoResult -> NotResultsScreen()
                }
            }
        }
    }
}