package com.example.dismobileproject.ui.screens.produtclist.search

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dismobileproject.R
import com.example.dismobileproject.ui.navigation.Screen
import com.example.dismobileproject.ui.widgets.SearchField
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.os.bundleOf
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.dismobileproject.ui.widgets.SearchBar
import com.example.dismobileproject.ui.navigation.navigate

@Composable
fun SearchScreen(
    navController: NavController
//    previousNavController: NavController
){
    val searchRoute = stringResource(R.string.route_param_search)

    var text by remember { mutableStateOf ("") }
    Scaffold(
        topBar = {
            SearchBar(
                searchText = text,
                onSearchTextChanged = {value -> text = value},
                onBackClick = {
//                    if(!navController.popBackStack())
//                        previousNavController.popBackStack()
                    navController.popBackStack()
                },
                onDoneAction = { navController.navigate(Screen.Products.screenName+"?$searchRoute=$text") }
            )
        }
    ) {
        paddingValues ->
        Box(modifier = Modifier.padding(paddingValues))
    }
}