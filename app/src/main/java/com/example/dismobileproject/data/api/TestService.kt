package com.example.dismobileproject.data.api

import com.example.dismobileproject.data.model.TestModel
import retrofit2.http.GET
import retrofit2.http.Path

interface TestService {
    @GET("Test/{id}")
    suspend fun testSearch(
        @Path("id") id: Int
    ): TestModel

    @GET("Test")
    suspend fun getTests(): List<TestModel>
}