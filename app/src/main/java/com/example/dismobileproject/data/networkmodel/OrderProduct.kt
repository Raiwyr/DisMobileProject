package com.example.dismobileproject.data.networkmodel

import com.google.gson.annotations.SerializedName

data class OrderProduct(
    @SerializedName("Name"  ) var Name  : String? = null,
    @SerializedName("Price" ) var Price : Int?     = null,
    @SerializedName("Count" ) var Count : Int?    = null,
)
