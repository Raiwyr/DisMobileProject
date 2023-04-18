package com.example.dismobileproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dismobileproject.ui.navigation.Screen
import com.example.dismobileproject.ui.navigation.createExternalRouter
import com.example.dismobileproject.ui.navigation.navigate
import com.example.dismobileproject.ui.screens.MainScreen
import com.example.dismobileproject.ui.screens.TestScreen
import com.example.dismobileproject.ui.theme.DisMobileProjectTheme

class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DisMobileProjectTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Screen.Main.screenName) {
                    composable(Screen.Main.screenName) {
                        MainScreen(
                            createExternalRouter { screen, params ->
                                navController.navigate(screen, params)
                            }
                        )
                    }
                    composable(Screen.Test.screenName){
                        TestScreen(navController)
                    }
                }
            }
        }
    }
}