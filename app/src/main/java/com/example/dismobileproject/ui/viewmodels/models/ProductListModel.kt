package com.example.dismobileproject.ui.viewmodels.models

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class ProductListModel(
    var id: Int,
    var name: String,
    var price: Int,
    var assessment: Int,
    var count: Int,
    initialAddToShopCart: Boolean = false
){
    var isAddToShopCart by mutableStateOf(initialAddToShopCart)
}