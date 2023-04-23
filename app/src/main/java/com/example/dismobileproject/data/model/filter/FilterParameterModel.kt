package com.example.dismobileproject.data.model.filter

import com.example.dismobileproject.data.networkmodel.filter.Parameter

data class FilterParameterModel(
    var releaseForms: List<ParameterModel?>? = null,
    var indications: List<ParameterModel?>? = null,
    var quantityPackage: List<Int?>? = null,
    var manufacturers: List<ParameterModel?>?= null
)
