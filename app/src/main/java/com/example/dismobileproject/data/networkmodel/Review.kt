package com.example.dismobileproject.data.networkmodel

import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("Id"         ) var Id         : Int?    = null,
    @SerializedName("Assessment" ) var Assessment : Int?    = null,
    @SerializedName("Message"    ) var Message    : String? = null,
    @SerializedName("UserId"     ) var UserId     : Int?    = null,
)
