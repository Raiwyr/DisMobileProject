package com.example.dismobileproject.ui.screens.profile.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dismobileproject.R
import com.example.dismobileproject.ui.screens.common.ErrorScreen
import com.example.dismobileproject.ui.screens.common.LoadingScreen
import com.example.dismobileproject.ui.screens.common.NotResultsScreen
import com.example.dismobileproject.ui.screens.profile.profilesettings.EditParameterScreen
import com.example.dismobileproject.ui.viewmodels.EditUiState
import com.example.dismobileproject.ui.viewmodels.ProfileEditViewModel
import com.example.dismobileproject.ui.viewmodels.RegistrationViewModel
import com.example.dismobileproject.ui.widgets.OnlyBackBar
import com.example.dismobileproject.ui.widgets.TextFieldDate

@ExperimentalMaterialApi
@Composable
fun RegistrationScreen(
    navController: NavController
){
    var viewModel: RegistrationViewModel = viewModel(factory = RegistrationViewModel.Factory)

    if (!viewModel.initView)
        viewModel.getGenders()

    var gendersState = viewModel.gendersState

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("") }

    var colors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = colorResource(id = R.color.action_element_color),
        unfocusedBorderColor = colorResource(id = R.color.action_element_color),
        focusedLabelColor = colorResource(id = R.color.action_element_color),
        unfocusedLabelColor = colorResource(id = R.color.action_element_color),
        disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
        backgroundColor = Color.Transparent,
        disabledBorderColor = colorResource(id = R.color.action_element_color),
        disabledLabelColor = colorResource(id = R.color.action_element_color)
    )

    Scaffold(
        topBar = {
            OnlyBackBar(
                backgroundColor = colorResource(id = R.color.action_element_color),
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        viewModel.registrationUser()
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .padding(25.dp)
                        .size(height = 45.dp, width = 250.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.action_element_color)),
                    shape = CircleShape
                ) {
                    Text(
                        text = "Регистрация",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
        }
    ){
            paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(top = 15.dp, start = 15.dp, end = 15.dp),
                value = viewModel.userInfoState.username,
                label = { Text("Имя пользователя") },
                onValueChange = {value -> viewModel.userInfoState.username = value },
                colors = colors
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(top = 15.dp, start = 15.dp, end = 15.dp),
                value = viewModel.userInfoState.password,
                label = { Text("Пароль") },
                onValueChange = {value -> viewModel.userInfoState.password = value },
                colors = colors
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(top = 15.dp, start = 15.dp, end = 15.dp),
                value = viewModel.userInfoState.fullName,
                label = { Text("ФИО") },
                onValueChange = {value -> viewModel.userInfoState.fullName = value },
                colors = colors
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(top = 15.dp, start = 15.dp, end = 15.dp),
                value = viewModel.userInfoState.phone,
                label = { Text("Телефон") },
                onValueChange = { value -> viewModel.userInfoState.phone = value },
                colors = colors
            )

            TextFieldDate(
                modifier = Modifier.fillMaxWidth().padding(top = 15.dp, start = 15.dp, end = 15.dp),
                value = viewModel.userInfoState.birthDate,
                label = { Text("Дата рождения") },
                onValueChange = { value -> viewModel.userInfoState.birthDate = value },
                colors = colors
            )

            ExposedDropdownMenuBox(
                modifier = Modifier.fillMaxWidth().padding(top = 15.dp, start = 15.dp, end = 15.dp),
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    value = selectedOptionText,
                    onValueChange = { },
                    label = { Text("Пол") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                    colors = colors
                )
                DropdownMenu(
                    modifier = Modifier.exposedDropdownSize(true),
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    gendersState.forEach { gender ->
                        DropdownMenuItem(
                            modifier = Modifier,
                            onClick = {
                                selectedOptionText = gender.name
                                viewModel.checkGender(gender.id)
                                expanded = false
                            }
                        ) {
                            Text(text = gender.name)
                        }
                    }
                }
            }
        }
    }
}