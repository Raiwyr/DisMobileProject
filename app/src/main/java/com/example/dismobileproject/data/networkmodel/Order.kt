package com.example.dismobileproject.data.networkmodel

import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("Id"            ) var Id            : Int?                    = null,
    @SerializedName("OrderDate"     ) var OrderDate     : String?                 = null,
    @SerializedName("OrderStatus"   ) var OrderStatus   : String?                 = null,
    @SerializedName("GrandTotal"    ) var GrandTotal    : Int?                    = null,
    @SerializedName("ProductModels" ) var ProductModels : ArrayList<OrderProduct> = arrayListOf(),
)
