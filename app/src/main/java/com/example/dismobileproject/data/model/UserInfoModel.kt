package com.example.dismobileproject.data.model

import java.time.LocalDate

data class UserInfoModel(
    var id: Int?,
    var fullName: String? = null,
    var birthDate: LocalDate?,
    var phone: String? = null,
    var gender: String? = null,
)
