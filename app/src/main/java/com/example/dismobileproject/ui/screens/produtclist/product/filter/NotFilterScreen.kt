package com.example.dismobileproject.ui.screens.produtclist.product.filter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun NotFilterScreen(){
    Box(modifier = Modifier.fillMaxSize()){
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Фильтры не загружены"
        )
    }
}