package com.example.dismobileproject.data.repositories

import com.example.dismobileproject.data.api.ProductService
import com.example.dismobileproject.data.model.ProductHeaderModel
import com.example.dismobileproject.data.model.ProductModel
import com.example.dismobileproject.data.model.ReviewModel
import com.example.dismobileproject.data.model.SelectionParameterModel
import com.example.dismobileproject.data.networkmodel.Product
import java.math.RoundingMode
import java.time.LocalDate
import java.time.format.DateTimeFormatter


interface ProductRepository {
    suspend fun getProduct(id: Int): ProductModel
    suspend fun getProductHeaders(): List<ProductHeaderModel>
    suspend fun searchProducts(searchString: String): List<ProductHeaderModel>
    suspend fun selectProducts(model: String): List<ProductHeaderModel>
    suspend fun getProductByCategoryId(id: Int): List<ProductHeaderModel>
}

class NetworkProductRepository(
    private  val productService: ProductService
): ProductRepository{
    override suspend fun getProduct(id: Int): ProductModel {
        val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
        var netProduct = productService.getProductById(id)
        return ProductModel(
            id = netProduct.Id,
            name = netProduct.Name,
            composition = netProduct.Composition,
            dosage = netProduct.Dosage,
            quantity = netProduct.Availability?.Quantity,
            price = netProduct.Availability?.Price,
            productType = netProduct.ProductType?.Name,
            releaseForm = netProduct.ReleaseForm?.Name,
            indication = netProduct.Indication?.map {it.Name},
            contraindication = netProduct.Contraindication?.map {it.Name},
            review = netProduct.Review.map { it ->
                ReviewModel(
                    id = it.Id,
                    assessment = it.Assessment,
                    message = it.Message,
                    userId = it.UserId,
                    userNsme = it.UserName,
                    dateReview = LocalDate.parse(it.DateReview, inputFormatter)
                )
            },
            manufacturer = netProduct.Manufacturer?.Name
        )
    }

    override suspend fun getProductHeaders(): List<ProductHeaderModel> = productService.getProducts().map { it ->
        mapProductToProductHeaderModel(it)
    }

    override suspend fun searchProducts(searchString: String): List<ProductHeaderModel> = productService.productSearch(searchString).map { it ->
        mapProductToProductHeaderModel(it)
    }

    override suspend fun selectProducts(model: String): List<ProductHeaderModel> = productService.selectProduct(model).map { it ->
        mapProductToProductHeaderModel(it)
    }

    override suspend fun getProductByCategoryId(id: Int): List<ProductHeaderModel> = productService.getProductsByCategoryId(id).map { it ->
        mapProductToProductHeaderModel(it)
    }

    private fun mapProductToProductHeaderModel(product: Product): ProductHeaderModel{
        val sumAssessments = product.Review.sumOf { it.Assessment ?: 0 }?.toDouble()
        val countAssessments = product.Review?.count()
        val assessment = if(sumAssessments == null || countAssessments == null) null else sumAssessments / countAssessments
        return ProductHeaderModel(
            id = product.Id,
            name = product.Name,
            price = product.Availability?.Price,
            assessment = assessment?.toBigDecimal()?.setScale(0, RoundingMode.HALF_UP)?.toInt() ?: 0
        )
    }
}