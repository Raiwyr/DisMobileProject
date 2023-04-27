package com.example.dismobileproject.ui.screens.profile.profilesettings

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
import com.example.dismobileproject.R
import com.example.dismobileproject.data.model.UserInfoModel
import com.example.dismobileproject.ui.viewmodels.EditParameterViewModel
import com.example.dismobileproject.ui.widgets.TextFieldDate

@ExperimentalMaterialApi
@Composable
fun EditParameterScreen(
    userInfo: UserInfoModel
){
    var viewModel: EditParameterViewModel = viewModel(factory = EditParameterViewModel.Factory)
//    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")


    if (!viewModel.initView)
        viewModel.setUserInfo(userInfo)

    var gendersState = viewModel.gendersState

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(viewModel.userInfoState.gender) }

    var colors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = colorResource(id = R.color.action_element_color),
        unfocusedBorderColor = colorResource(id = R.color.action_element_color),
        focusedLabelColor = colorResource(id = R.color.action_element_color),
        unfocusedLabelColor = colorResource(id = R.color.action_element_color),
        disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
        backgroundColor = Color.Transparent,
        disabledBorderColor = colorResource(id = R.color.action_element_color),
        disabledLabelColor = colorResource(id = R.color.action_element_color),
    )

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { viewModel.updateUserInfo() },
                    modifier = Modifier
                        .padding(25.dp)
                        .size(height = 45.dp, width = 250.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.action_element_color)),
                    shape = CircleShape
                ) {
                    Text(
                        text = "Изменить",
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
                    gendersState.forEachIndexed() { index, gender ->
                        DropdownMenuItem(
                            modifier = Modifier,
                            onClick = {
                                selectedOptionText = gender.name
                                viewModel.checkGender(index)
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