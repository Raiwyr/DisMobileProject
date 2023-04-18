package com.example.dismobileproject.data.api

import com.example.dismobileproject.data.model.SelectionParameterModel
import com.example.dismobileproject.data.networkmodel.Product
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {
    @GET("product/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ): Product

    @GET("product")
    suspend fun getProducts(): List<Product>

    @GET("product/search")
    suspend fun productSearch(
        @Query("param") param: String
    ): List<Product>

    @GET("selection")
    suspend fun selectProduct(
        @Header("model") model: String
    ): List<Product>

    @GET("product/category/{id}")
    suspend fun getProductsByCategoryId(
        @Path("id") id: Int
    ): List<Product>
}