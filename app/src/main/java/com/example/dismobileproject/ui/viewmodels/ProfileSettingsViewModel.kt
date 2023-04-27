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
import com.example.dismobileproject.data.model.UserInfoModel
import com.example.dismobileproject.data.repositories.UserRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface ProfileUiState{
    object Success: ProfileUiState
    object Loading: ProfileUiState
    object NoResult: ProfileUiState
    object Error: ProfileUiState
}

class ProfileSettingsViewModel(
    private  val userRepository: UserRepository
): ViewModel() {

    var userInfo by mutableStateOf<UserInfoModel>(UserInfoModel())

    var profileUiState:ProfileUiState by mutableStateOf(ProfileUiState.Loading)

    var initView by mutableStateOf(false)

    fun initView(userId: Int){
        getUserInfo(userId)
        initView = true
    }

    fun getUserInfo(userId: Int){
        viewModelScope.launch {
            profileUiState = ProfileUiState.Loading
            profileUiState = try {
                val userInfoModel = userRepository.getUserInfo(userId)

                userInfo = userInfoModel

                ProfileUiState.Success
            }
            catch (e: IOException){
                ProfileUiState.Error
            }
            catch (e: HttpException){
                ProfileUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DisApplication)
                var userRepository = application.container.userRepository
                ProfileSettingsViewModel(userRepository = userRepository)
            }
        }
    }
}