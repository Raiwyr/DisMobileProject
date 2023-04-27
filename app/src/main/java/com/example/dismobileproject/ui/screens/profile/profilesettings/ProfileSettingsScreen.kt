package com.example.dismobileproject.ui.screens.profile.profilesettings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dismobileproject.ui.preference.getLoggedUserId
import com.example.dismobileproject.ui.screens.common.ErrorScreen
import com.example.dismobileproject.ui.screens.common.LoadingScreen
import com.example.dismobileproject.ui.screens.common.NotResultsScreen
import com.example.dismobileproject.ui.viewmodels.EditParameterViewModel
import com.example.dismobileproject.ui.viewmodels.EditUiState
import com.example.dismobileproject.ui.viewmodels.ProfileSettingsViewModel
import com.example.dismobileproject.ui.viewmodels.ProfileUiState
import com.example.dismobileproject.ui.widgets.OnlyBackBar
import com.example.dismobileproject.R

@Composable
fun ProfileSettingsScreen(
    navController: NavController
){
    var viewModel: ProfileSettingsViewModel = viewModel(factory = ProfileSettingsViewModel.Factory)

    var userId = getLoggedUserId(LocalContext.current)

    if (!viewModel.initView)
        viewModel.initView(userId)

    Scaffold(
        topBar = {
            OnlyBackBar(
                backgroundColor = colorResource(id = R.color.action_element_color),
                onBackClick = { navController.popBackStack() }
            )
        }
    ){
            paddingValues ->
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)){
            when(viewModel.profileUiState){
                is ProfileUiState.Loading -> LoadingScreen()
                is ProfileUiState.Success -> { SettingParameterScreen(viewModel.userInfo, navController) }
                is ProfileUiState.Error -> ErrorScreen(retryAction = {
                    if(userId >= 0)
                        viewModel.getUserInfo(userId)
                })
                is ProfileUiState.NoResult -> NotResultsScreen()
            }
        }
    }
}