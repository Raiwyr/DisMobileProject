package com.example.dismobileproject.ui.screens.catalog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dismobileproject.R
import com.example.dismobileproject.ui.navigation.BottomScreen
import com.example.dismobileproject.ui.navigation.Screen
import com.example.dismobileproject.ui.screens.home.HomeScreen
import com.example.dismobileproject.ui.screens.produtclist.product.ProductsScreen
import com.example.dismobileproject.ui.screens.produtclist.product.detail.ProductDetailScreen
import com.example.dismobileproject.ui.screens.produtclist.search.SearchScreen

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun CategoryNavContainer(){
    val navController = rememberNavController()

    val searchRoute = stringResource(R.string.route_param_search)
    val categoryIdRoute = stringResource(R.string.route_param_category_id)
    val productIdRoute = stringResource(R.string.route_param_product_id)

    NavHost(
        navController = navController,
        startDestination = BottomScreen.Category.screenName
    ) {
        composable(BottomScreen.Category.screenName) { CategoryScreen(navController) }
        composable(Screen.Search.screenName) { SearchScreen(navController = navController) }
        composable(
            Screen.Products.screenName+"?$searchRoute={$searchRoute}&$categoryIdRoute={$categoryIdRoute}",
            arguments = listOf(
                navArgument(searchRoute) {
                    nullable = true
                    type = NavType.StringType
                    defaultValue = null
                },
                navArgument(categoryIdRoute) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
                backStackEntry ->
            ProductsScreen(
                navController,
                searchText = backStackEntry.arguments?.getString(searchRoute),
                categoryId = backStackEntry.arguments?.getInt(categoryIdRoute)
            )
        }
        composable(
            Screen.ProductDetail.screenName + "?$productIdRoute={$productIdRoute}",
            arguments = listOf(
                navArgument(productIdRoute) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ){
                backStackEntry ->
            ProductDetailScreen(
                navController,
                backStackEntry.arguments?.getInt(productIdRoute)
            )
        }
    }
}