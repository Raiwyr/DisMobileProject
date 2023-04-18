package com.example.dismobileproject.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.dismobileproject.ui.navigation.Screen

@Composable
fun TestScreen(
    mainNavController: NavController
){
    Box(modifier = Modifier.fillMaxSize()){
        Button(
            onClick = {
                mainNavController.navigate(Screen.Main.screenName)
            },
            modifier = Modifier.wrapContentHeight()
        ) {

        }
    }
}