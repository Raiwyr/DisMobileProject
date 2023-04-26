package com.example.dismobileproject.data.repositories

import com.example.dismobileproject.data.api.ProductService
import com.example.dismobileproject.data.model.ProductHeaderModel
import com.example.dismobileproject.data.model.ProductModel
import com.example.dismobileproject.data.model.ReviewModel
import com.example.dismobileproject.data.model.SelectionParameterModel
import com.example.dismobileproject.data.model.filter.FilterModel
import com.example.dismobileproject.data.model.filter.FilterParameterModel
import com.example.dismobileproject.data.model.filter.ParameterModel
import com.example.dismobileproject.data.networkmodel.Product
import com.example.dismobileproject.data.networkmodel.ProductHeader
import com.google.gson.Gson
import java.math.RoundingMode
import java.time.LocalDate
import java.time.format.DateTimeFormatter


interface ProductRepository {
    suspend fun getProduct(id: Int): ProductModel
    suspend fun searchProducts(searchString: String, filter: FilterModel? = null): List<ProductHeaderModel>
    suspend fun getProductByCategoryId(id: Int, filter: FilterModel? = null): List<ProductHeaderModel>
    suspend fun selectProducts(model: SelectionParameterModel): List<ProductHeaderModel>
    suspend fun getProductFilters(productIds: List<Int>): FilterParameterModel
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

    override suspend fun searchProducts(searchString: String, filter: FilterModel?): List<ProductHeaderModel> =
        productService.productSearch(
            param = searchString,
            filter = if(filter != null) Gson().toJson(filter) else ""
        ).map { mapProductToProductHeaderModel(it) }


    override suspend fun getProductByCategoryId(id: Int, filter: FilterModel?): List<ProductHeaderModel> =
        productService.getProductsByCategoryId(
            id = id,
            filter = if(filter != null) Gson().toJson(filter) else ""
        ).map { mapProductToProductHeaderModel(it) }

    override suspend fun selectProducts(model: SelectionParameterModel): List<ProductHeaderModel> =
        productService.selectProduct(Gson().toJson(model)).map { mapProductToProductHeaderModel(it) }

    override suspend fun getProductFilters(productIds: List<Int>): FilterParameterModel {
        val productIdsJson = Gson().toJson(productIds)
        var filterParameter = productService.getProductFilters(productIdsJson)

        return FilterParameterModel(
            indications = filterParameter.Indications.map {
                ParameterModel(
                    id = it.Id,
                    name = it.Name
                )
            },
            releaseForms = filterParameter.ReleaseForms.map {
                ParameterModel(
                    id = it.Id,
                    name = it.Name
                )
            },
            manufacturers = filterParameter.Manufacturers.map {
                ParameterModel(
                    id = it.Id,
                    name = it.Name
                )
            },
            quantityPackage = filterParameter.QuantityPackage
        )
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