package com.example.dismobileproject.data.model

import com.example.dismobileproject.data.networkmodel.Gender
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class RegistrationModel(
    var fullName: String,
    var birthDate: LocalDate,
    var phone: String,
    var genderId: Int,
    var username: String,
    var password: String,
)
