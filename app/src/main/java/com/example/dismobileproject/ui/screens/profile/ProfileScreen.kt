package com.example.dismobileproject.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dismobileproject.ui.navigation.Router
import com.example.dismobileproject.ui.navigation.Screen
import com.example.dismobileproject.ui.viewmodels.ProductsViewModel
import com.example.dismobileproject.ui.viewmodels.ProfileViewModel
import com.example.dismobileproject.ui.viewmodels.UserAuthorizationState
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local
import com.example.dismobileproject.ui.preference.isUserLogged
import com.example.dismobileproject.ui.preference.getLoggedUserId

@Composable
fun ProfileScreen(
    navController: NavController,
    router: Router
){
    var viewModel: ProfileViewModel = viewModel(factory = ProfileViewModel.Factory)

    viewModel.checkAuthorization(isUserLogged(LocalContext.current), getLoggedUserId(LocalContext.current))

    var userAuthorizationState = viewModel.userAuthorizationState

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        when(userAuthorizationState){
            is UserAuthorizationState.Authorization -> { ProfileMenuScreen(viewModel, navController, router)}
            is UserAuthorizationState.NoAuthorization -> { NoAuthorizationScreen(router = router) }
        }
    }
}