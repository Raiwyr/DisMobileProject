package com.example.dismobileproject.data.networkmodel

import com.google.gson.annotations.SerializedName

data class Availability(
    @SerializedName("Id"       ) var Id       : Int?    = null,
    @SerializedName("Quantity" ) var Quantity : Int?    = null,
    @SerializedName("Price"    ) var Price    : Int? = null
)
