package com.example.dismobileproject.data.repositories

import com.example.dismobileproject.data.api.UserService
import com.example.dismobileproject.data.model.*
import com.example.dismobileproject.data.networkmodel.Order
import com.example.dismobileproject.data.networkmodel.ProductHeader
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
            gender = userInfo.Gender
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