package com.example.dismobileproject.ui.screens.profile

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.example.dismobileproject.ui.navigation.NavigationController
import com.example.dismobileproject.ui.navigation.Screen
import com.example.dismobileproject.ui.screens.TestProfileScreen
import com.example.dismobileproject.ui.screens.profile.ProfileScreen

@Composable
fun TestContainer(
) {
    NavigationController(
        startDestination = Screen.Profile.screenName,
        screens = listOf(
            Pair(Screen.Profile.screenName, { nav, _, _ -> ProfileScreen(nav) }),
            Pair(Screen.ProfileTest.screenName, { _, _, _ -> TestProfileScreen() })
        )
    )
}