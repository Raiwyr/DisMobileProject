package com.example.dismobileproject.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dismobileproject.DisApplication
import com.example.dismobileproject.data.model.PostReviewModel
import com.example.dismobileproject.data.model.ProductModel
import com.example.dismobileproject.data.repositories.ProductRepository
import com.example.dismobileproject.data.repositories.UserRepository
import com.example.dismobileproject.ui.viewmodels.models.PrdouctDescriptionModel
import com.example.dismobileproject.ui.viewmodels.models.ReviewDescriptionModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

sealed interface ProductDetailUiState{
    object Success: ProductDetailUiState
    object Loading: ProductDetailUiState
    object NoResult: ProductDetailUiState
    object Error: ProductDetailUiState
}

sealed interface InitProductDetailState{
    object Init: InitProductDetailState
    object NoInit: InitProductDetailState
}

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private  val userRepository: UserRepository
): ViewModel() {

    var productDetailUiState: ProductDetailUiState by mutableStateOf(ProductDetailUiState.Loading)
        private set

    var initProductDetailState: InitProductDetailState by mutableStateOf(InitProductDetailState.NoInit)
        private set

    var productState by mutableStateOf<PrdouctDescriptionModel?>(null)

    fun  initViewModel(productId: Int, userId: Int?){
        initProductDetailState = InitProductDetailState.Init
        getProduct(productId, userId)
    }

    fun getProduct(productId: Int, userId: Int?){
        viewModelScope.launch {
            productDetailUiState = ProductDetailUiState.Loading
            productDetailUiState = try {
                var product = productRepository.getProduct(productId)
                if (product == null)
                    ProductDetailUiState.NoResult
                else {
                    var productModel = PrdouctDescriptionModel(
                        id = product.id ?: 0,
                        name = product.name ?: "",
                        composition = product.composition ?: "",
                        dosage = product.dosage ?: "",
                        quantity = product.quantity ?: 0,
                        price = product.price ?: 0,
                        productType = product.productType ?: "",
                        releaseForm = product.releaseForm ?: "",
                        indication = product.indication?.map { it -> it ?: "" } ?: listOf(),
                        contraindication = product.contraindication?.map { it -> it ?: "" } ?: listOf(),
                        sideEffect = product.sideEffect?.map { it -> it ?: "" } ?: listOf(),
                        review = product.review?.map { it ->
                            ReviewDescriptionModel(
                                assessment = it?.assessment ?: 0,
                                message = it?.message ?: "",
                                userName = it?.userNsme ?: "",
                                dateReview = it?.dateReview ?: LocalDate.now()
                            )
                        } ?: listOf(),
                        manufacturer = product.manufacturer ?: ""
                    )
                    if (userId != null && userId >= 0){
                        var shopCart = userRepository.getShopCart(userId)
                        if(shopCart.find { it.id == product.id } != null)
                            productModel.isAddToShopCart = true
                    }

                    productModel.review = productModel.review.sortedBy { it.dateReview }

                    productState = productModel

                    ProductDetailUiState.Success
                }
            }
            catch (e: IOException){
                ProductDetailUiState.Error
            }
            catch (e: HttpException){
                ProductDetailUiState.Error
            }
        }
    }

    fun addProductToShopCart(userId: Int){
        viewModelScope.launch {
            try {
                productState.let {
                    if(it?.id != null){
                        userRepository.postProductToShopCart(userId, it.id)
                        productState?.isAddToShopCart = true
                    }
                }
            }
            catch (e: IOException){
                return@launch
            }
            catch (e: HttpException){
                return@launch
            }
        }
    }

    fun postReview(assessment: Int, name: String, message: String, userId: Int){
        if(assessment > 0){
            val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
            var productId = productState?.id ?: -1
            if(productId < 0)
                return
            var review = PostReviewModel(
                UserId = userId,
                UserName = name.ifEmpty { "Аноним" },
                ProductId = productId,
                Assessment = assessment,
                Message = message,
                DateReview = LocalDateTime.now().format(inputFormatter)
            )
            viewModelScope.launch {
                try {
                    userRepository.postReview(review);
                    getProduct(productId = productId, userId = userId)
                }
                catch (e: IOException){
                    return@launch
                }
                catch (e: HttpException){
                    return@launch
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DisApplication)
                var productRepository = application.container.productRepository
                var userRepository = application.container.userRepository
                ProductDetailViewModel(
                    productRepository = productRepository,
                    userRepository = userRepository
                )
            }
        }
    }
}