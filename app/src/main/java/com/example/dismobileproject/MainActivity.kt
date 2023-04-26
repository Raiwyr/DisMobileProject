package com.example.dismobileproject

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dismobileproject.ui.navigation.Screen
import com.example.dismobileproject.ui.navigation.createExternalRouter
import com.example.dismobileproject.ui.navigation.navigate
import com.example.dismobileproject.ui.screens.MainScreen
import com.example.dismobileproject.ui.screens.TestScreen
import com.example.dismobileproject.ui.screens.profile.account.AuthorizationScreen
import com.example.dismobileproject.ui.screens.profile.account.RegistrationScreen
import com.example.dismobileproject.ui.theme.DisMobileProjectTheme

class MainActivity : ComponentActivity() {
    @ExperimentalLayoutApi
    @ExperimentalComposeUiApi
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
                    composable(Screen.Authorization.screenName) { AuthorizationScreen(navController = navController) }
                    composable(Screen.Registration.screenName) { RegistrationScreen(navController = navController) }
                }
            }
        }
    }
}