package com.example.dismobileproject.data.networkmodel.filter

import com.google.gson.annotations.SerializedName

data class FilterParameter(
    @SerializedName("ReleaseForms"    ) var ReleaseForms    : ArrayList<Parameter> = arrayListOf(),
    @SerializedName("Indications"     ) var Indications     : ArrayList<Parameter> = arrayListOf(),
    @SerializedName("QuantityPackage" ) var QuantityPackage : ArrayList<Int>       = arrayListOf(),
    @SerializedName("Manufacturers"   ) var Manufacturers   : ArrayList<Parameter> = arrayListOf()
)
