package com.example.dismobileproject.ui.screens.home

import androidx.compose.animation.ExperimentalAnimationApi
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
import com.example.dismobileproject.data.model.SelectionParameterModel
import com.example.dismobileproject.ui.navigation.NavigationController
import com.example.dismobileproject.ui.navigation.Screen
import com.example.dismobileproject.ui.screens.TestProfileScreen
import com.example.dismobileproject.ui.screens.catalog.CategoryScreen
import com.example.dismobileproject.ui.screens.produtclist.product.ProductsScreen
import com.example.dismobileproject.ui.screens.produtclist.product.detail.ProductDetailScreen
import com.example.dismobileproject.ui.screens.produtclist.search.SearchScreen
import com.example.dismobileproject.ui.screens.profile.ProfileScreen
import com.example.dismobileproject.ui.screens.profile.TestContainer
import com.example.dismobileproject.ui.screens.selection.SelectionProductScreen
import com.example.dismobileproject.ui.screens.selection.SelectionResultsScreen
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.Moshi

@ExperimentalMaterialApi
@Composable
fun HomeNavContainer(){
    val navController = rememberNavController()

    val selectionRoute = stringResource(R.string.route_param_selection_model)
    val searchRoute = stringResource(R.string.route_param_search)
    val productIdRoute = stringResource(R.string.route_param_product_id)

    NavHost(
        navController = navController,
        startDestination = Screen.Home.screenName
    ) {
        composable(Screen.Home.screenName) { HomeScreen(navController) }
        composable(Screen.Search.screenName) {SearchScreen(navController = navController)}
        composable( Screen.Products.screenName+"?$searchRoute={$searchRoute}") {
                backStackEntry ->
            ProductsScreen(
                navController,
                backStackEntry.arguments?.getString(searchRoute)
            )
        }
        composable(Screen.Selection.screenName) { SelectionProductScreen(navController) }
        composable(Screen.SelectionResults.screenName + "?$selectionRoute={$selectionRoute}") {
                backStackEntry ->
            val selectionParameterJson =  backStackEntry.arguments?.getString(selectionRoute)
            val typeToken = object : TypeToken<SelectionParameterModel>() {}.type
            val selectionParameter = Gson().fromJson<SelectionParameterModel>(selectionParameterJson, typeToken)
            SelectionResultsScreen(
            selectionModel = selectionParameter,
            navController = navController
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