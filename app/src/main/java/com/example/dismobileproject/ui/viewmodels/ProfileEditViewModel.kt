package com.example.dismobileproject.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dismobileproject.DisApplication
import com.example.dismobileproject.data.model.UserInfoModel
import com.example.dismobileproject.data.repositories.UserRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface EditUiState{
    object Success: EditUiState
    object Loading: EditUiState
    object NoResult: EditUiState
    object Error: EditUiState
}

class ProfileEditViewModel(
    private  val userRepository: UserRepository
): ViewModel() {

    var editUiState:EditUiState by mutableStateOf(EditUiState.Loading)

    var userInfo by mutableStateOf<UserInfoModel>(UserInfoModel())

    var initView by mutableStateOf(false)

    fun initView(userId: Int){
        getUserInfo(userId)
        initView = true
    }

    fun getUserInfo(userId: Int){
        viewModelScope.launch {
            editUiState = EditUiState.Loading
            editUiState = try {
                val userInfoModel = userRepository.getUserInfo(userId)

                userInfo = userInfoModel

                EditUiState.Success
            }
            catch (e: IOException){
                EditUiState.Error
            }
            catch (e: HttpException){
                EditUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DisApplication)
                var userRepository = application.container.userRepository
                ProfileEditViewModel(userRepository = userRepository)
            }
        }
    }
}