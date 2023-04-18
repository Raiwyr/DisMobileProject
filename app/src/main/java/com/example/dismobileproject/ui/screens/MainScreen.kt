package com.example.dismobileproject.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.dismobileproject.ui.navigation.Router
import com.example.dismobileproject.ui.navigation.Screen
import com.example.dismobileproject.ui.screens.TestScreen
import com.example.dismobileproject.ui.screens.catalog.CategoryNavContainer
import com.example.dismobileproject.ui.screens.catalog.CategoryScreen
import com.example.dismobileproject.ui.screens.home.HomeNavContainer
import com.example.dismobileproject.ui.screens.home.HomeScreen
import com.example.dismobileproject.ui.screens.profile.TestContainer

@ExperimentalMaterialApi
@Composable
fun MainScreen(
    router: Router
) {
    val navController = rememberNavController()
    val bottomItems = listOf(Screen.Home, Screen.Category, Screen.Profile)

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            androidx.compose.material.BottomNavigation(
                backgroundColor = Color.White
            ) {
                bottomItems.forEach { screen ->
                    BottomNavigationItem(
                        selected = currentRoute == screen.screenName,
                        onClick = {
                            navController.navigate(screen.screenName){
                                popUpTo(navController.graph.findStartDestination().id){
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = { Text(stringResource(id = screen.titleResourceId)) },
                        icon = {
                            Icon(
                                painter = painterResource(id = screen.iconId),
                                contentDescription = "Icon"
                            )
                        },
                        selectedContentColor = Color.Magenta,
                        unselectedContentColor = Color.Gray
                    )
                }
            }
        }
    ) {
        paddingValues ->
        NavHost(
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
            navController = navController,
            startDestination = Screen.Home.screenName
        ) {
            composable(Screen.Home.screenName) { HomeNavContainer() }
            composable(Screen.Category.screenName) { CategoryNavContainer() }
            composable(Screen.Profile.screenName) { TestContainer() }
        }
    }
}