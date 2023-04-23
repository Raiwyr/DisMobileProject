package com.example.dismobileproject.data.networkmodel.filter

import com.google.gson.annotations.SerializedName

data class Parameter(
    @SerializedName("Id"   ) var Id   : Int?    = null,
    @SerializedName("Name" ) var Name : String? = null
)
