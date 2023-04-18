package com.example.dismobileproject.data.repositories

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dismobileproject.DisApplication
import com.example.dismobileproject.data.model.CategoryModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.dismobileproject.data.model.ProductModel
import com.example.dismobileproject.ui.viewmodels.ProductUiState
import com.example.dismobileproject.ui.viewmodels.ProductsViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface CategoryUiState{
    data class Success(val categories: List<CategoryModel>): CategoryUiState
    object Loading: CategoryUiState
    object NoResult: CategoryUiState
    object Error: CategoryUiState
}

class CategoryViewModel(
    private val productParameterRepository: ProductParameterRepository
): ViewModel() {

    var categoryUiState: CategoryUiState by mutableStateOf(CategoryUiState.Loading)
        private set

    fun getCategories(){
        viewModelScope.launch {
            categoryUiState = CategoryUiState.Loading
            categoryUiState = try {
                var categoryList = productParameterRepository.getCategories()
                if (categoryList.isEmpty())
                    CategoryUiState.NoResult
                else
                    CategoryUiState.Success(categoryList)
            }
            catch (e: IOException){
                CategoryUiState.Error
            }
            catch (e: HttpException){
                CategoryUiState.Error
            }
        }
    }

    init {
        getCategories()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DisApplication)
                var productParameterRepository = application.container.productParameterRepository
                CategoryViewModel(productParameterRepository = productParameterRepository)
            }
        }
    }
}