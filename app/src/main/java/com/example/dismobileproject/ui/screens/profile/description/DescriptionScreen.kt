package com.example.dismobileproject.ui.screens.profile.description

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dismobileproject.R
import com.example.dismobileproject.ui.screens.common.ErrorScreen
import com.example.dismobileproject.ui.screens.common.LoadingScreen
import com.example.dismobileproject.ui.screens.common.NotResultsScreen
import com.example.dismobileproject.ui.screens.profile.profilesettings.SettingParameterScreen
import com.example.dismobileproject.ui.viewmodels.ProfileUiState
import com.example.dismobileproject.ui.widgets.OnlyBackBar

@Composable
fun DescriptionScreen(
    navController: NavController
){
    var scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            OnlyBackBar(
                backgroundColor = colorResource(id = R.color.action_element_color),
                onBackClick = { navController.popBackStack() }
            )
        }
    ){
            paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 5.dp),
                text = "О аптеке",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
                text = " " + stringResource(id = R.string.pharmacy_description),
                fontSize = 22.sp,
                textAlign = TextAlign.Justify
            )
            Text(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
                text = "Контактная информация",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 10.dp),
                text = " " + stringResource(id = R.string.contact_information),
                fontSize = 22.sp
            )
        }
    }


}