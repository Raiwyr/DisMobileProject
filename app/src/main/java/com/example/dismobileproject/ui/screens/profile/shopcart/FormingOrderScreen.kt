package com.example.dismobileproject.ui.screens.profile.shopcart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dismobileproject.R
import com.example.dismobileproject.ui.navigation.BottomScreen
import com.example.dismobileproject.ui.navigation.Screen
import com.example.dismobileproject.ui.screens.common.ErrorScreen
import com.example.dismobileproject.ui.screens.common.LoadingScreen
import com.example.dismobileproject.ui.screens.common.NotResultsScreen
import com.example.dismobileproject.ui.viewmodels.FormingOrderViewModel
import com.example.dismobileproject.ui.viewmodels.ProductShopCart
import com.example.dismobileproject.ui.viewmodels.ProductShopCartToOrder
import com.example.dismobileproject.ui.viewmodels.ShopCartUiState
import com.example.dismobileproject.ui.widgets.OnlyBackBar
import com.google.gson.Gson
import com.example.dismobileproject.ui.preference.getLoggedUserId

@Composable
fun FormingOrderScreen(
    navController: NavController,
    products: List<ProductShopCartToOrder>?
){
    var viewModel: FormingOrderViewModel = viewModel(factory = FormingOrderViewModel.Factory)

    var userId = getLoggedUserId(LocalContext.current)

    val completedOrderRoute = stringResource(R.string.route_param_completed_order)

    var allProductCount = 0
    products?.forEach {
        allProductCount += it.price * it.selectedCount
    }

    val scrollState = rememberScrollState()

    // Smooth scroll to specified pixels on first composition
    LaunchedEffect(Unit) { scrollState.animateScrollTo(10000) }

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
                        if(products != null && products.isNotEmpty()){
                            viewModel.orderProducts(userId, products)
                            navController.navigate(Screen.Order.screenName + "?$completedOrderRoute=${false}"){
                                popUpTo(BottomScreen.Profile.screenName)
                            }
                        }
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 2.dp, start = 10.dp, end = 10.dp),
                    elevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 10.dp),
                            text = "Товары(${products?.count() ?: " "})",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )

                        products?.forEach {
                            Row(
                                modifier = Modifier.padding(bottom = 10.dp,start = 10.dp, end = 10.dp).fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier.weight(8f),
                                    text = "${it.name}(${it.selectedCount})",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Row(
                                    modifier = Modifier.weight(2f).padding(top = 4.dp, start = 10.dp, bottom = 10.dp),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        modifier = Modifier,
                                        text = (it.selectedCount * it.price).toString(),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Image(
                                        painter = painterResource(id = R.drawable.ruble_icon),
                                        modifier = Modifier
                                            .height(15.dp)
                                            .width(16.dp),
                                        contentDescription = ""
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.padding(bottom = 10.dp, start = 10.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier,
                                text = "Общая стоимость: $allProductCount",
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Image(
                                painter = painterResource(id = R.drawable.ruble_icon),
                                modifier = Modifier
                                    .height(22.dp)
                                    .width(23.dp),
                                contentDescription = ""
                            )
                        }
                    }
                }
            }
        }
    }
}