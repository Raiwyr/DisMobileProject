package com.example.dismobileproject.data.api

import com.example.dismobileproject.data.networkmodel.Category
import com.example.dismobileproject.data.networkmodel.Contraindication
import com.example.dismobileproject.data.networkmodel.Indication
import com.example.dismobileproject.data.networkmodel.SideEffect
import retrofit2.http.GET

interface ProductParameterService {
    @GET("product/parameters/indication")
    suspend fun getIndications(): List<Indication>

    @GET("product/parameters/contraindication")
    suspend fun getContraindications(): List<Contraindication>

    @GET("product/parameters/sideeffect")
    suspend fun getSideEffects(): List<SideEffect>

    @GET("product/parameters/category")
    suspend fun getCategories(): List<Category>
}