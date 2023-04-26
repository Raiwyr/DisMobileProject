package com.example.dismobileproject.ui.viewmodels.models

import java.time.LocalDate

data class ReviewDescriptionModel(
    var assessment: Int,
    var message: String,
    var userName: String,
    var dateReview: LocalDate
)
