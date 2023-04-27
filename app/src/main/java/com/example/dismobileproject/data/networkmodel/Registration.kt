package com.example.dismobileproject.data.networkmodel

import com.google.gson.annotations.SerializedName

data class Registration(
    @SerializedName("FullName"  ) var FullName  : String,
    @SerializedName("BirthDate" ) var BirthDate : String,
    @SerializedName("Phone"     ) var Phone     : String,
    @SerializedName("GenderId"  ) var GenderId  : Int,
    @SerializedName("Username"  ) var Username  : String,
    @SerializedName("Password"  ) var Password  : String,
)
