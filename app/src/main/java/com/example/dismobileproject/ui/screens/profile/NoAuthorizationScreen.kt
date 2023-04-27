package com.example.dismobileproject.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dismobileproject.R
import com.example.dismobileproject.ui.navigation.Router
import com.example.dismobileproject.ui.navigation.Screen

@Composable
fun NoAuthorizationScreen(
    router: Router
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            modifier = Modifier
                .width(300.dp)
                .padding(bottom = 20.dp),
            text = stringResource(id = R.string.not_autorixation_text),
            fontSize = 25.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Button(
            onClick = { router.routeTo(Screen.Authorization.screenName) },
            modifier = Modifier
                .size(height = 60.dp, width = 200.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.action_element_color)),
            shape = CircleShape
        ) {
            Text(
                text = "Войти",
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }
}