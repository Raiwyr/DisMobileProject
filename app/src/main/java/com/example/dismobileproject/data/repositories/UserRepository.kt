package com.example.dismobileproject.data.repositories

import com.example.dismobileproject.data.api.UserService
import com.example.dismobileproject.data.model.*
import com.example.dismobileproject.data.networkmodel.*
import com.google.gson.Gson
import java.time.LocalDate
import java.time.format.DateTimeFormatter

interface UserRepository {
    suspend fun userAuthentication(login: String, password: String): Int?
    suspend fun getUserInfo(id: Int): UserInfoModel
    suspend fun getShopCart(userId: Int): List<ProductHeaderModel>
    suspend fun postProductToShopCart(userId: Int, productId: Int): Boolean
    suspend fun postProductToOrder(userId: Int, products: List<ProductToOrderModel>): Boolean
    suspend fun getNotCompletedOrders(userId: Int): List<OrderModel>
    suspend fun getCompletedOrders(userId: Int): List<OrderModel>
    suspend fun postReview(review: PostReviewModel): Boolean
    suspend fun getGenders(): List<GenderModel>
    suspend fun updateUserInfo(userInfoModel: UserInfoModel): Boolean
    suspend fun registrationUser(registrationModel: RegistrationModel): Boolean
}

class UserNetworkRepository(
    private  val userService: UserService
): UserRepository{

    override suspend fun userAuthentication(login: String, password: String): Int? =
        userService.UserAuthentication(login, password)

    override suspend fun getUserInfo(id: Int): UserInfoModel {
        val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
        var userInfo = userService.getUserInfo(id)
        return UserInfoModel(
            id = userInfo.Id,
            fullName = userInfo.FullName,
            birthDate = LocalDate.parse(userInfo.BirthDate, inputFormatter),
            phone = userInfo.Phone,
            gender = GenderModel(
                id = userInfo.Gender?.Id,
                name = userInfo.Gender?.Name
            )
        )
    }

    override suspend fun getShopCart(userId: Int): List<ProductHeaderModel> =
        userService.getShopCart(userId).map { mapProductToProductHeaderModel(it) }

    override suspend fun postProductToShopCart(userId: Int, productId: Int): Boolean =
        userService.postProductToShopCart(userId, productId)

    override suspend fun postProductToOrder(userId: Int, products: List<ProductToOrderModel>): Boolean {
        val productsJson = Gson().toJson(products)
        return  userService.postProductToOrder(userId, productsJson)
    }

    override suspend fun getNotCompletedOrders(userId: Int): List<OrderModel> = ordersMap(userService.getNotCompletedOrders(userId))

    override suspend fun getCompletedOrders(userId: Int): List<OrderModel> = ordersMap(userService.getCompletedOrders(userId))
    override suspend fun postReview(review: PostReviewModel): Boolean {
        val reviewJson = Gson().toJson(review)
        return userService.postReview(reviewJson)
    }

    override suspend fun getGenders(): List<GenderModel> =
        userService.getGenders().map { it -> GenderModel(
            id = it.Id,
            name = it.Name
        ) }

    override suspend fun updateUserInfo(userInfoModel: UserInfoModel): Boolean {
        val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
        var userInfo = UserInfo(
            Id = userInfoModel.id,
            FullName = userInfoModel.fullName,
            BirthDate = userInfoModel.birthDate?.atStartOfDay()?.format(inputFormatter),
            Phone = userInfoModel.phone,
            Gender = Gender(
                Id = userInfoModel.gender?.id,
                Name = userInfoModel.gender?.name
            )
        )
        var userInfoJson = Gson().toJson(userInfo)
        return  userService.putUserInfo(userInfoJson)
    }

    override suspend fun registrationUser(registrationModel: RegistrationModel): Boolean {
        val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
        var registration = Registration(
            FullName = registrationModel.fullName,
            BirthDate = registrationModel.birthDate.atStartOfDay().format(inputFormatter),
            Phone = registrationModel.phone,
            GenderId = registrationModel.genderId,
            Username = registrationModel.username,
            Password = registrationModel.password
        )
        var registrationJson = Gson().toJson(registration)
        return  userService.postUser(registrationJson)
    }

    private fun ordersMap(orders: List<Order>): List<OrderModel>{
        val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
        return orders.map { order ->
            OrderModel(
                id = order.Id,
                orderDate = LocalDate.parse(order.OrderDate, inputFormatter),
                orderStatus = order.OrderStatus,
                grandTotal = order.GrandTotal,
                productModels = order.ProductModels.map { product ->
                    OrderProductModel(
                        Name = product.Name,
                        Price = product.Price,
                        Count = product.Count
                    )
                }
            )
        }
    }

    private fun mapProductToProductHeaderModel(product: ProductHeader): ProductHeaderModel{
        return ProductHeaderModel(
            id = product.Id,
            name = product.Name,
            price = product.Price,
            assessment = product.Assessment,
            count = product.Count
        )
    }
}