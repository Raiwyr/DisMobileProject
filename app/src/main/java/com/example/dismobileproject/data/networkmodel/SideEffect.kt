package com.example.dismobileproject.data.networkmodel

import com.google.gson.annotations.SerializedName

data class SideEffect(
    @SerializedName("Id"   ) var Id   : Int?    = null,
    @SerializedName("Name" ) var Name : String? = null
)
