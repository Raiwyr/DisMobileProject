package com.example.dismobileproject.data.model

import com.google.gson.annotations.SerializedName

data class TestModel(
    @SerializedName("Id") val id: Int,
    @SerializedName("Name") val name: String?,
    @SerializedName("Surname") val surname: String?
)
