package com.example.dismobileproject.data.networkmodel

import com.google.gson.annotations.SerializedName

data class ProductHeader(
    @SerializedName("Id"         ) var Id         : Int?    = null,
    @SerializedName("Name"       ) var Name       : String? = null,
    @SerializedName("Price"      ) var Price      : Int?    = null,
    @SerializedName("Assessment" ) var Assessment : Int?    = null,
    @SerializedName("Count"      ) var Count      : Int?    = null,
    @SerializedName("ImageName"  ) var ImageName  : String? = null,
)
