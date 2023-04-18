package com.example.dismobileproject.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.dismobileproject.R

@Composable
fun OnlyBackBar(
    onBackClick: () -> Unit = {}
){
    TopAppBar(backgroundColor = Color.White) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            IconButton(
                onClick = { onBackClick() },
                Modifier
                    .height(20.dp)
                    .width(20.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back),
                    tint = Color.Black,
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}