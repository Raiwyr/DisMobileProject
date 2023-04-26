package com.example.dismobileproject.ui.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.dismobileproject.ui.navigation.Router
import com.example.dismobileproject.ui.navigation.Screen

@Composable
fun NoAuthorizationScreen(
    router: Router
){
    Box(modifier = Modifier.fillMaxSize()){
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = { router.routeTo(Screen.Authorization.screenName) }
        ) {

        }
    }
}