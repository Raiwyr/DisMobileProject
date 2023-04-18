package com.example.dismobileproject.data.model

data class ProductModel(
    var id: Int?,
    var name: String?,
    var composition: String? = null,
    var dosage: String? = null,
    var quantity: Int? = null,
    var price: Int?,
    var productType: String? = null,
    var releaseForm: String? = null,
    var indication: List<String?>? = null,
    var contraindication: List<String?>? = null,
    var review: List<ReviewModel?>? = null,
    var manufacturer: String? = null
)
