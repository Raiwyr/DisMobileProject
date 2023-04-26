package com.example.dismobileproject.data.model

import java.time.LocalDate


data class OrderModel(
    var id: Int?,
    var orderDate: LocalDate?,
    var orderStatus: String? = null,
    var grandTotal: Int?,
    var productModels: List<OrderProductModel?>? = null
)
