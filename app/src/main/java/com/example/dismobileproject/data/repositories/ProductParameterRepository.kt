package com.example.dismobileproject.data.repositories

import com.example.dismobileproject.data.api.ProductParameterService
import com.example.dismobileproject.data.model.CategoryModel
import com.example.dismobileproject.data.model.ContraindicationModel
import com.example.dismobileproject.data.model.IndicationModel
import com.example.dismobileproject.data.model.ProductModel
import com.example.dismobileproject.data.networkmodel.Contraindication
import com.example.dismobileproject.data.networkmodel.Indication

interface ProductParameterRepository {
    suspend fun getIndications(): List<IndicationModel>
    suspend fun getContraindications(): List<ContraindicationModel>
    suspend fun getCategories(): List<CategoryModel>
}

class NetworkProductParameterRepository(
    private  val productParameterService: ProductParameterService
): ProductParameterRepository{
    override suspend fun getIndications(): List<IndicationModel> = productParameterService.getIndications().map { it ->
        IndicationModel(
            id = it.Id,
            name = it.Name
        )
    }

    override suspend fun getContraindications(): List<ContraindicationModel> = productParameterService.getContraindications().map { it ->
        ContraindicationModel(
            id = it.Id,
            name = it.Name
        )
    }

    override suspend fun getCategories(): List<CategoryModel> = productParameterService.getCategories().map { it ->
        CategoryModel(
            id = it.Id,
            name = it.Name
        )
    }

}