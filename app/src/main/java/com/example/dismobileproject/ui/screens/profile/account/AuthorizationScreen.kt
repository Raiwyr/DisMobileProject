package com.example.dismobileproject.ui.screens.profile.account

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dismobileproject.R
import com.example.dismobileproject.ui.navigation.BottomScreen
import com.example.dismobileproject.ui.navigation.Screen
import com.example.dismobileproject.ui.viewmodels.AuthorizationViewModel
import com.example.dismobileproject.ui.viewmodels.ProductsViewModel
import com.example.dismobileproject.ui.viewmodels.ProfileViewModel
import com.example.dismobileproject.ui.viewmodels.UserAuthorizedState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.dismobileproject.ui.preference.logInUser

@Composable
fun AuthorizationScreen(
    navController: NavController
){
    var viewModel: AuthorizationViewModel = viewModel(factory = AuthorizationViewModel.Factory)

    var userAuthorizedState = viewModel.userAuthorizedState

    when(userAuthorizedState){
        is UserAuthorizedState.Authorized -> {
            logInUser(context = LocalContext.current, userId = userAuthorizedState.id)
            navController.navigateUp()
        }
        is UserAuthorizedState.IncorrectData ->{}
        is UserAuthorizedState.ErrorAuthorized ->{}
        is UserAuthorizedState.NotAuthorized ->{}
    }

    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ){

        Icon(
            modifier = Modifier
                .size(60.dp)
                .clickable { navController.popBackStack() }
                .padding(top = 15.dp, end = 15.dp)
                .align(Alignment.TopEnd),
            painter = painterResource(id = R.drawable.close_icon),
            tint = Color.LightGray,
            contentDescription = ""
        )

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                modifier = Modifier
                    .padding(bottom = 15.dp),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                text = "Авторизация"
            )

            OutlinedTextField(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .width(300.dp),
                shape = CircleShape,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorResource(id = R.color.action_element_color),
                    unfocusedBorderColor = colorResource(id = R.color.action_element_color)
                ),
                value = login,
                onValueChange = { value -> login = value },
                placeholder = { Text(text = "Логин") }
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .width(300.dp),
                shape = CircleShape,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorResource(id =R.color.action_element_color),
                    unfocusedBorderColor = colorResource(id =R.color.action_element_color)),
                value = password,
                onValueChange = { value -> password = value },
                placeholder = { Text(text = "Пароль") }
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = { viewModel.authorizeUser(login, password) },
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .size(height = 50.dp, width = 250.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.action_element_color)),
                shape = CircleShape
            ) {
                Text(
                    text = "Войти",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
            OutlinedButton(
                onClick = { navController.navigate(Screen.Registration.screenName) },
                modifier = Modifier
                    .size(height = 50.dp, width = 250.dp),
                border = BorderStroke(1.dp, colorResource(id = R.color.action_element_color)),
                shape = CircleShape
            ) {
                Text(
                    text = "Регистрация",
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.action_element_color)
                )
            }
        }
    }
}