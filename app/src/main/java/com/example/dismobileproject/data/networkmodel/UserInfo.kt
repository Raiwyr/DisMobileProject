package com.example.dismobileproject.data.networkmodel

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("Id"        ) var Id        : Int?       = null,
    @SerializedName("FullName"  ) var FullName  : String?    = null,
    @SerializedName("BirthDate" ) var BirthDate : String?    = null,
    @SerializedName("Phone"     ) var Phone     : String?    = null,
    @SerializedName("Gender"    ) var Gender    : String?    = null,

)
