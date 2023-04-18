package com.example.dismobileproject.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.dismobileproject.ui.navigation.Screen

@Composable
fun ProfileScreen(
    navController: NavController
){
    Column() {
        Button(onClick = { navController.navigate(Screen.ProfileTest.screenName) }) {

        }
    }
}