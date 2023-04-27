package com.example.dismobileproject.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import com.example.dismobileproject.ui.viewmodels.ProfileViewModel
import com.example.dismobileproject.R
import com.example.dismobileproject.ui.navigation.Router
import com.example.dismobileproject.ui.navigation.Screen
import com.example.dismobileproject.ui.preference.getLoggedUserId

@Composable
fun ProfileMenuScreen(
    viewModel: ProfileViewModel,
    navController: NavController,
    router: Router
){

    var userName = viewModel.userName

    val completedOrderRoute = stringResource(R.string.route_param_completed_order)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(20.dp)
                    .size(80.dp),
                painter = painterResource(id = R.drawable.profile_icon),
                tint = colorResource(id = R.color.action_element_color),
                contentDescription = "")
            Text(
                text = userName,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
            thickness = 1.dp,
            color = Color.LightGray
        )
        Column(
            modifier = Modifier
                .weight(6f)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                modifier = Modifier.padding(15.dp),
                text = "Меню",
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()

                    .padding(top = 15.dp, start = 10.dp, end = 10.dp)
                    .requiredHeight(80.dp),
                elevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .clickable { navController.navigate(Screen.ShoppingCart.screenName) }
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(id = R.drawable.shop_basket),
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(start = 20.dp),
                        text = "Корзина",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, start = 10.dp, end = 10.dp)
                    .requiredHeight(80.dp),
                elevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .clickable { navController.navigate(Screen.Order.screenName + "?$completedOrderRoute=${false}") }
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(id = R.drawable.box_icon),
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(start = 20.dp),
                        text = "Заказы",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, start = 10.dp, end = 10.dp)
                    .requiredHeight(80.dp),
                elevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .clickable { navController.navigate(Screen.Order.screenName + "?$completedOrderRoute=${true}") }
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(id = R.drawable.box_icon),
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(start = 20.dp),
                        text = "История заказов",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, start = 10.dp, end = 10.dp)
                    .requiredHeight(80.dp),
                elevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .clickable { router.routeTo(Screen.ProfileSettings.screenName) }
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.settings_icon),
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(start = 20.dp),
                        text = "Профиль",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, start = 10.dp, end = 10.dp)
                    .requiredHeight(80.dp)
                    .clickable(onClick = { }),
                elevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .clickable { /*TODO: add action*/ }
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(id = R.drawable.exclamation_icon),
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(start = 20.dp),
                        text = "О аптеке",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}