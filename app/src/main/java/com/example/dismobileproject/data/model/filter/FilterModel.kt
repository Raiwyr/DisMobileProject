package com.example.dismobileproject.data.model.filter

import com.example.dismobileproject.data.networkmodel.filter.Parameter
import com.google.gson.annotations.SerializedName

data class FilterModel(
    @SerializedName("MinPrice"        ) var MinPrice         : Int?,
    @SerializedName("MaxPrice"        ) var MaxPrice         : Int?,
    @SerializedName("ReleaseFormsIds" ) var ReleaseFormsIds  : ArrayList<Int>?,
    @SerializedName("IndicationsIds"  ) var IndicationsIds   : ArrayList<Int>?,
    @SerializedName("QuantityPackage" ) var QuantityPackage  : ArrayList<Int>?,
    @SerializedName("ManufacturersIds") var ManufacturersIds : ArrayList<Int>?
)
