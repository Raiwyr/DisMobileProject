package com.example.dismobileproject.ui.viewmodels.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class PrdouctDescriptionModel(
    var id: Int,
    var name: String,
    var composition: String,
    var dosage: String,
    var quantity: Int,
    var price: Int,
    var productType: String,
    var releaseForm: String,
    var indication: List<String>,
    var contraindication: List<String>,
    var review: List<ReviewDescriptionModel>,
    var manufacturer: String,
    initialAddToShopCart: Boolean = false
) {
    var isAddToShopCart by mutableStateOf(initialAddToShopCart)
}