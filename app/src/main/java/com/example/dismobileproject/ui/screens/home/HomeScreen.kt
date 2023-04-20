package com.example.dismobileproject.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dismobileproject.R
import com.example.dismobileproject.ui.navigation.Screen
import com.example.dismobileproject.ui.widgets.SearchField

@Composable
fun HomeScreen(
    navController: NavController
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(3f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
            )
//            SearchField(
//                Modifier.fillMaxWidth(0.9f)
//                    .clickable { navController.navigate(Screen.Search.screenName)},
//                enable = false)
            SearchField(isEnable = false, modifier = Modifier.height(50.dp).fillMaxWidth(0.9f).clickable { navController.navigate(
                Screen.Search.screenName) }, text = "")
            Button(
                onClick = { navController.navigate(Screen.SelectionParameter.screenName) },
                modifier = Modifier
                    .size(height = 60.dp, width = 250.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.action_element_color)),
                shape = CircleShape
            ) {
                Text(
                    text = stringResource(id = R.string.string_select_product),
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(2f)
        )
    }
}

