package com.example.dismobileproject.data.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class PostReviewModel(
    @SerializedName("UserId") var UserId: Int?,
    @SerializedName("UserName") var UserName: String?,
    @SerializedName("ProductId") var ProductId: Int?,
    @SerializedName("Assessment") var Assessment: Int?,
    @SerializedName("Message") var Message: String?,
    @SerializedName("DateReview") var DateReview: String?,
)
