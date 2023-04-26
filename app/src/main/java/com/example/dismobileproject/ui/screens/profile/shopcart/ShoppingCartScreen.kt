package com.example.dismobileproject.ui.screens.profile.shopcart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dismobileproject.R
import com.example.dismobileproject.ui.navigation.Screen
import com.example.dismobileproject.ui.screens.common.ErrorScreen
import com.example.dismobileproject.ui.screens.common.LoadingScreen
import com.example.dismobileproject.ui.screens.common.NotResultsScreen
import com.example.dismobileproject.ui.screens.produtclist.product.ProductListScreen
import com.example.dismobileproject.ui.viewmodels.*
import com.example.dismobileproject.ui.widgets.OnlyBackBar
import com.google.gson.Gson
import com.example.dismobileproject.ui.preference.getLoggedUserId

@Composable
fun ShoppingCartScreen(
    navController: NavController
){
    var viewModel: ShoppingCartViewModel = viewModel(factory = ShoppingCartViewModel.Factory)

    var shopCartUiState = viewModel.shopCartUiState

    val productsRoute = stringResource(id = R.string.route_param_shopcart_products)

    var userId = getLoggedUserId(LocalContext.current)

    if(!viewModel.initViewModel && userId >= 0)
        viewModel.initViewModel(userId)

    Scaffold(
        topBar = {
            OnlyBackBar(
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        val ROUTE_FARMING_ORDER = "${Screen.FormingOrder.screenName}?$productsRoute={$productsRoute}"
                        val productList = viewModel.shopCartList.filter { it.isSelected }.map {
                            ProductShopCartToOrder(
                                id = it.id,
                                name = it.name,
                                price = it.price,
                                selectedCount = it.selectedCount
                            )
                        }

                        val productListJson = Gson().toJson(productList)

                        navController.navigate(
                            ROUTE_FARMING_ORDER.replace("{$productsRoute}", productListJson)
                        )
                    },
                    modifier = Modifier
                        .padding(5.dp)
                        .size(height = 45.dp, width = 250.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.action_element_color)),
                    shape = CircleShape
                ) {
                    Text(
                        text = "Заказать",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
        }
    ) {
            paddingValues ->
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)){
            when(shopCartUiState){
                is ShopCartUiState.Loading -> LoadingScreen()
                is ShopCartUiState.Success -> { ShopCartListScreen(viewModel) }
                is ShopCartUiState.Error -> ErrorScreen(retryAction = {
                    if(userId >= 0)
                        viewModel.getShopCart(userId)
                })
                is ShopCartUiState.NoResult -> NotResultsScreen()
            }
        }
    }
}