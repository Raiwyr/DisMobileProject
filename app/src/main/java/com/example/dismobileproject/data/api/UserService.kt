package com.example.dismobileproject.data.api

import com.example.dismobileproject.data.networkmodel.*
import retrofit2.http.*

interface UserService {
    @GET("user/auth")
    suspend fun UserAuthentication(
        @Query("login") login: String,
        @Query("pass") pass: String
    ): Int?

    @GET("user/info")
    suspend fun getUserInfo(
        @Query("id") id: Int
    ): UserInfo

    @GET("user/shopcart")
    suspend fun getShopCart(
        @Query("userId") userId: Int
    ): List<ProductHeader>

    @POST("user/shopcart")
    suspend fun postProductToShopCart(
        @Query("userId") userId: Int,
        @Query("productId") productId: Int
    ): Boolean

    @POST("user/order")
    suspend fun postProductToOrder(
        @Query("userId") userId: Int,
        @Body products: String
    ): Boolean

    @GET("user/order/notcompleted")
    suspend fun getNotCompletedOrders(
        @Query("userId") userId: Int
    ): List<Order>

    @GET("user/order/completed")
    suspend fun getCompletedOrders(
        @Query("userId") userId: Int
    ): List<Order>

    @POST("user/review")
    suspend fun postReview(
        @Body review: String
    ): Boolean

    @GET("user/genders")
    suspend fun getGenders(
    ): List<Gender>

    @PUT("user/info")
    suspend fun putUserInfo(
        @Body model: String
    ): Boolean

    @POST("user")
    suspend fun postUser(
        @Body model: String
    ): Boolean
}