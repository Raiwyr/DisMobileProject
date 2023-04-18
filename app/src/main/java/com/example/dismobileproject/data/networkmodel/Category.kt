package com.example.dismobileproject.data.networkmodel

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("Id"   ) var Id   : Int?    = null,
    @SerializedName("Name" ) var Name : String? = null
)
