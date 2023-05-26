package com.example.dismobileproject.ui.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.InitializerViewModelFactoryBuilder
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dismobileproject.DisApplication
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewModelScope
import com.example.dismobileproject.data.model.ProductHeaderModel
import com.example.dismobileproject.data.model.ProductModel
import com.example.dismobileproject.data.model.filter.FilterModel
import com.example.dismobileproject.data.repositories.ProductRepository
import com.example.dismobileproject.data.repositories.UserRepository
import com.example.dismobileproject.ui.viewmodels.models.ProductListModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface ProductUiState{
    object Success: ProductUiState
    object Loading: ProductUiState
    object NoResult: ProductUiState
    object Error: ProductUiState
}

sealed interface GetProductState{
    object Search: GetProductState
    object Category: GetProductState
    object NoInit: GetProductState
}

sealed interface FilterState{
    object Init: FilterState
    object NoInit: FilterState
}

sealed interface FilterSetState{
    object Set: FilterSetState
    object NoSet: FilterSetState
}

class FilterValue(
    val Name: String,
    val Value: Int,
    initialChecked: Boolean = false
) {
    var isSelected by mutableStateOf(initialChecked)
}

class ProductsViewModel(
    private  val productRepository: ProductRepository,
    private  val userRepository: UserRepository
): ViewModel() {

    var productUiState: ProductUiState by mutableStateOf(ProductUiState.Loading)
        private set

    var getProductState: GetProductState by mutableStateOf(GetProductState.NoInit)
        private set

    var filterState: FilterState by mutableStateOf(FilterState.NoInit)
        private set

    var filterSetState: FilterSetState by mutableStateOf(FilterSetState.NoSet)
        private set

    var productList by mutableStateOf(mutableStateListOf<ProductListModel>())
        private set

    //region filter parameters
    var minPriceState by mutableStateOf("")
    var maxPriceState by mutableStateOf("")
    var indicationsState by mutableStateOf(mutableListOf<FilterValue>())
    var releaseFormsState by mutableStateOf(mutableListOf<FilterValue>())
    var quantityPackageState by mutableStateOf(mutableListOf<FilterValue>())
    var manufacturersState by mutableStateOf(mutableListOf<FilterValue>())
    //endregion

    fun setMinPrice(value: String){
        minPriceState = value
    }

    fun setMaxPrice(value: String){
        maxPriceState = value
    }

    fun InitViewModel(parameter: String, userId: Int?){
        getProductState = GetProductState.Search
        getProducts(
            searchText = parameter,
            userId = userId
        )
    }

    fun InitViewModel(parameter: Int, userId: Int?){
        getProductState = GetProductState.Category
        getProducts(
            categoryId = parameter,
            userId = userId
        )
    }

    fun getProducts(searchText: String, userId: Int?, filter: FilterModel? = null){
        viewModelScope.launch {
            productUiState = ProductUiState.Loading
            productUiState = try {
                var products = productRepository.searchProducts(searchText, filter)
                if (products.isEmpty())
                    ProductUiState.NoResult
                else {
                    var productModels = products.map {
                        ProductListModel(
                            id = it.id ?: 0,
                            name = it.name ?: "",
                            price = it.price ?: 0,
                            assessment = it.assessment ?: 0,
                            count = it.count ?: 0,
                            imageName = it.imageName ?: ""
                        )
                    }
                    if (userId != null && userId >= 0){
                        var shopCart = userRepository.getShopCart(userId)
                        for(index in productModels.indices){
                            if(shopCart.find { it.id == productModels[index].id } != null)
                                productModels[index].isAddToShopCart = true
                        }
                    }

                    productList = productModels.toMutableStateList()

                    ProductUiState.Success
                }
            }
            catch (e: IOException){
                ProductUiState.Error
            }
            catch (e: HttpException){
                ProductUiState.Error
            }
        }
    }

    fun getProducts(categoryId: Int, userId: Int?, filter: FilterModel? = null){
        viewModelScope.launch {
            productUiState = ProductUiState.Loading
            productUiState = try {
                var products = productRepository.getProductByCategoryId(categoryId, filter)
                if (products.isEmpty())
                    ProductUiState.NoResult
                else{
                    var productModels = products.map {
                        ProductListModel(
                            id = it.id ?: 0,
                            name = it.name ?: "",
                            price = it.price ?: 0,
                            assessment = it.assessment ?: 0,
                            count = it.count ?: 0,
                            imageName = it.imageName ?: ""
                        )
                    }
                    if (userId != null && userId >= 0){
                        var shopCart = userRepository.getShopCart(userId)
                        for(index in productModels.indices){
                            if(shopCart.find { it.id == productModels[index].id } != null)
                                productModels[index].isAddToShopCart = true
                        }
                    }

                    productList = productModels.toMutableStateList()

                    ProductUiState.Success
                }
            }
            catch (e: IOException){
                ProductUiState.Error
            }
            catch (e: HttpException){
                ProductUiState.Error
            }
        }
    }

    fun getFilters(ids: List<Int>){
        viewModelScope.launch {
            try {
                var filters = productRepository.getProductFilters(ids)

                filters.indications?.forEach {
                    indicationsState.add(
                        FilterValue(
                            Name = it?.name ?: "",
                            Value = it?.id ?: 0
                        )
                    )
                }
                filters.releaseForms?.forEach {
                    releaseFormsState.add(
                        FilterValue(
                            Name = it?.name ?: "",
                            Value = it?.id ?: 0
                        )
                    )
                }
                filters.quantityPackage?.forEach {
                    quantityPackageState.add(
                        FilterValue(
                            Name = it.toString(),
                            Value = it ?: 0
                        )
                    )
                }
                filters.manufacturers?.forEach {
                    manufacturersState.add(
                        FilterValue(
                            Name = it?.name ?: "",
                            Value = it?.id ?: 0
                        )
                    )
                }
                filterState = FilterState.Init
            }
            catch (e: IOException){

            }
        }
    }

    fun onIndicationCheck(index: Int){
        indicationsState[index].isSelected = !indicationsState[index].isSelected
    }

    fun onReleaseFormCheck(index: Int){
        releaseFormsState[index].isSelected = !releaseFormsState[index].isSelected
    }

    fun onQuantityPackageCheck(index: Int){
        quantityPackageState[index].isSelected = !quantityPackageState[index].isSelected
    }

    fun onManufacturerCheck(index: Int){
        manufacturersState[index].isSelected = !manufacturersState[index].isSelected
    }

    fun getProductsByFilter(searchText: String, userId: Int?){
        var minPrice = minPriceState.toIntOrNull()
        var maxPrice = maxPriceState.toIntOrNull()
        filterSetState = FilterSetState.Set
        var filter = FilterModel(
            MinPrice = minPrice,
            MaxPrice = if((maxPrice ?: -1) > (minPrice ?: 0)) maxPrice else null,
            IndicationsIds =
                if(indicationsState.any { it.isSelected })
                    ArrayList(indicationsState.filter { it.isSelected }.map { it.Value })
                else
                    null,
            ReleaseFormsIds =
                if(releaseFormsState.any { it.isSelected })
                    ArrayList(releaseFormsState.filter { it.isSelected }.map { it.Value })
                else
                    null,
            QuantityPackage =
                if(quantityPackageState.any { it.isSelected })
                    ArrayList(quantityPackageState.filter { it.isSelected }.map { it.Value })
                else
                    null,
            ManufacturersIds =
                if(manufacturersState.any { it.isSelected })
                    ArrayList(manufacturersState.filter { it.isSelected }.map { it.Value })
                else
                    null
        )
        getProducts(
            searchText = searchText,
            userId = userId,
            filter = filter
        )
    }

    fun getProductsByFilter(categoryId: Int, userId: Int?){
        var minPrice = minPriceState.toIntOrNull()
        var maxPrice = maxPriceState.toIntOrNull()
        filterSetState = FilterSetState.Set
        var filter = FilterModel(
            MinPrice = minPrice,
            MaxPrice = if((maxPrice ?: -1) > (minPrice ?: 0)) maxPrice else null,
            IndicationsIds =
            if(indicationsState.any { it.isSelected })
                ArrayList(indicationsState.filter { it.isSelected }.map { it.Value })
            else
                null,
            ReleaseFormsIds =
            if(releaseFormsState.any { it.isSelected })
                ArrayList(releaseFormsState.filter { it.isSelected }.map { it.Value })
            else
                null,
            QuantityPackage =
            if(quantityPackageState.any { it.isSelected })
                ArrayList(quantityPackageState.filter { it.isSelected }.map { it.Value })
            else
                null,
            ManufacturersIds =
            if(manufacturersState.any { it.isSelected })
                ArrayList(manufacturersState.filter { it.isSelected }.map { it.Value })
            else
                null
        )
        getProducts(
            categoryId = categoryId,
            userId = userId,
            filter = filter
        )
    }

    fun resetFilters(searchText: String, userId: Int?){
        filterSetState = FilterSetState.NoSet
        getProducts(
            searchText = searchText,
            userId = userId
        )
    }

    fun resetFilters(categoryId: Int, userId: Int?){
        filterSetState = FilterSetState.NoSet
        getProducts(
            categoryId = categoryId,
            userId = userId
        )
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as DisApplication)
                var productRepository = application.container.productRepository
                var userRepository = application.container.userRepository
                ProductsViewModel(
                    productRepository = productRepository,
                    userRepository = userRepository
                )
            }
        }
    }
}