package com.example.dismobileproject.data.repositories

import com.example.dismobileproject.data.api.TestService
import com.example.dismobileproject.data.model.TestModel


interface TestRepository {
    suspend fun getTest(id: Int): TestModel
    suspend fun getTests(): List<TestModel>

}
class NetworkTestRepository(
    private  val testService: TestService
): TestRepository{
    override suspend fun getTest(id: Int): TestModel = testService.testSearch(id)

    override suspend fun getTests(): List<TestModel> = testService.getTests()
}