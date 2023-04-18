package com.example.dismobileproject.data.model

import java.time.LocalDate
import java.util.Date

data class ReviewModel(
    var id: Int?,
    var assessment: Int?,
    var message: String? = null,
    var userId: Int?,
    var userNsme: String? = null,
    var dateReview: LocalDate?
)
