package com.example.dismobileproject.data.networkmodel

import com.example.dismobileproject.data.networkmodel.*
import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("Id"               ) var Id               : Int?                        = null,
    @SerializedName("Name"             ) var Name             : String?                     = null,
    @SerializedName("ImageName"        ) var ImageName        : String?                     = null,
    @SerializedName("Composition"      ) var Composition      : String?                     = null,
    @SerializedName("Dosage"           ) var Dosage           : String?                     = null,
    @SerializedName("ExpirationDate"   ) var ExpirationDate   : String?                     = null,
    @SerializedName("Availability"     ) var Availability     : Availability?               = Availability(),
    @SerializedName("ProductType"      ) var ProductType      : ProductType?                = ProductType(),
    @SerializedName("ReleaseForm"      ) var ReleaseForm      : ReleaseForm?                = ReleaseForm(),
    @SerializedName("Indication"       ) var Indication       : ArrayList<Indication>       = arrayListOf(),
    @SerializedName("Contraindication" ) var Contraindication : ArrayList<Contraindication> = arrayListOf(),
    @SerializedName("SideEffect"       ) var SideEffect       : ArrayList<SideEffect>       = arrayListOf(),
    @SerializedName("Review"           ) var Review           : ArrayList<Review>           = arrayListOf(),
    @SerializedName("Manufacturer"     ) var Manufacturer     : Manufacturer?               = Manufacturer()

)
