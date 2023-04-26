package com.example.dismobileproject.data.model

import com.google.gson.annotations.SerializedName

data class ProductToOrderModel(
    @SerializedName("Id"    ) var Id    : Int? = null,
    @SerializedName("Count" ) var Count : Int? = null,
)
