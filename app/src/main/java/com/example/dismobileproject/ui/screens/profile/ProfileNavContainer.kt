package com.example.dismobileproject.ui.screens.profile

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dismobileproject.R
import com.example.dismobileproject.ui.navigation.BottomScreen
import com.example.dismobileproject.ui.navigation.Router
import com.example.dismobileproject.ui.navigation.Screen
import com.example.dismobileproject.ui.screens.profile.description.DescriptionScreen
import com.example.dismobileproject.ui.screens.profile.profilesettings.ProfileEditScreen
import com.example.dismobileproject.ui.screens.profile.order.OrderScreen
import com.example.dismobileproject.ui.screens.profile.profilesettings.ProfileSettingsScreen
import com.example.dismobileproject.ui.screens.profile.shopcart.FormingOrderScreen
import com.example.dismobileproject.ui.screens.profile.shopcart.ShoppingCartScreen
import com.example.dismobileproject.ui.viewmodels.ProductShopCartToOrder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ExperimentalMaterialApi
@Composable
fun ProfileNavContainer(
    router: Router
){
    val navController = rememberNavController()

    val productsRoute = stringResource(id = R.string.route_param_shopcart_products)
    val completedOrderRoute = stringResource(R.string.route_param_completed_order)

//    logOutUser(LocalContext.current)

    NavHost(
        navController = navController,
        startDestination = BottomScreen.Profile.screenName
    ) {
        composable(BottomScreen.Profile.screenName) { ProfileScreen(navController = navController, router = router) }
        composable(Screen.ShoppingCart.screenName) { ShoppingCartScreen(navController) }
        composable(Screen.Description.screenName) { DescriptionScreen(navController) }
        composable(Screen.FormingOrder.screenName + "?$productsRoute={$productsRoute}") {
                backStackEntry ->
            val productsJson =  backStackEntry.arguments?.getString(productsRoute)
            val typeToken = object : TypeToken<List<ProductShopCartToOrder>>() {}.type
            val products = Gson().fromJson<List<ProductShopCartToOrder>>(productsJson, typeToken)
            FormingOrderScreen( navController = navController, products = products)
        }
        composable(
            Screen.Order.screenName + "?$completedOrderRoute={$completedOrderRoute}",
            arguments = listOf(
                navArgument(completedOrderRoute) {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) {
                backStackEntry ->
            OrderScreen(
                navController,
                backStackEntry.arguments?.getBoolean(completedOrderRoute)
            )
        }
    }
}