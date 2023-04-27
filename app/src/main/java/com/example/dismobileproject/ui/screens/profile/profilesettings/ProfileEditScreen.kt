package com.example.dismobileproject.ui.screens.profile.profilesettings

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dismobileproject.R
import com.example.dismobileproject.ui.preference.getLoggedUserId
import com.example.dismobileproject.ui.screens.common.ErrorScreen
import com.example.dismobileproject.ui.screens.common.LoadingScreen
import com.example.dismobileproject.ui.screens.common.NotResultsScreen
import com.example.dismobileproject.ui.viewmodels.EditUiState
import com.example.dismobileproject.ui.viewmodels.ProfileEditViewModel
import com.example.dismobileproject.ui.widgets.OnlyBackBar

@ExperimentalMaterialApi
@Composable
fun ProfileEditScreen(
    navController: NavController
){

    var viewModel: ProfileEditViewModel = viewModel(factory = ProfileEditViewModel.Factory)

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
            when(viewModel.editUiState){
                is EditUiState.Loading -> LoadingScreen()
                is EditUiState.Success -> { EditParameterScreen(viewModel.userInfo) }
                is EditUiState.Error -> ErrorScreen(retryAction = {
                    if(userId >= 0)
                        viewModel.getUserInfo(userId)
                })
                is EditUiState.NoResult -> NotResultsScreen()
            }
        }
    }
}