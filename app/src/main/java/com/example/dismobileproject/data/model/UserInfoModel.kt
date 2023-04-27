package com.example.dismobileproject.data.model

import java.time.LocalDate

data class UserInfoModel(
    var id: Int? = null,
    var fullName: String? = null,
    var birthDate: LocalDate? = null,
    var phone: String? = null,
    var gender: GenderModel? = null,
)
