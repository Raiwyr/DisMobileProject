package com.example.dismobileproject.data.api

import com.example.dismobileproject.data.model.SelectionParameterModel
import com.example.dismobileproject.data.networkmodel.Product
import com.example.dismobileproject.data.networkmodel.ProductHeader
import com.example.dismobileproject.data.networkmodel.filter.FilterParameter
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {
    @GET("product/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ): Product

    @POST("product/search")
    suspend fun productSearch(
        @Query("param") param: String,
        @Body filter: String = ""
    ): List<ProductHeader>

    @POST("product/category")
    suspend fun getProductsByCategoryId(
        @Query("id") id: Int,
        @Body filter: String = ""
    ): List<ProductHeader>

    @POST("selection")
    suspend fun selectProduct(
        @Body model: String
    ): List<ProductHeader>

    @POST("product/filter")
    suspend fun getProductFilters(
        @Body productIds: String = ""
    ): FilterParameter

    @GET("product/image/{name}")
    suspend fun getProductImage(
        @Path("name") name: String
    ): String
}