package com.example.dismobileproject.data.model

import com.google.gson.annotations.SerializedName

data class SelectionParameterModel(
    @SerializedName("IndicationId") var IndicationId: Int?,
    @SerializedName("ContraindicationIds") var ContraindicationIds: List<Int>?,
    @SerializedName("SideEffectIds") var SideEffectIds: List<Int>?,
    @SerializedName("PriseSort") var PriseSort: Boolean?,
    @SerializedName("AssessmentSort") var AssessmentSort: Boolean?,
    @SerializedName("ReviewsSort") var ReviewsSort: Boolean?,
    @SerializedName("CountResults") var CountResults: Int?,
    @SerializedName("Availability") var Availability: Boolean?,

    @SerializedName("evaluationContraindication") var evaluationContraindication: Int?,
    @SerializedName("evaluationSideEffect") var evaluationSideEffect: Int?,
    @SerializedName("evaluationPrise") var evaluationPrise: Int?,
    @SerializedName("evaluationAssessment") var evaluationAssessment: Int?,
    @SerializedName("evaluationReviews") var evaluationReviews: Int?
)
