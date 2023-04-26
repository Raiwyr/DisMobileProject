package com.example.dismobileproject.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dismobileproject.DisApplication
import com.example.dismobileproject.data.repositories.UserRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface UserAuthorizationState{
    object Authorization: UserAuthorizationState
    object NoAuthorization: UserAuthorizationState
}

class ProfileViewModel(
    private  val userNetworkRepository: UserRepository
): ViewModel() {

    var userAuthorizationState: UserAuthorizationState by mutableStateOf(UserAuthorizationState.NoAuthorization)
        private set

    var userName by mutableStateOf("")
        private set

    fun checkAuthorization(userAuthorized: Boolean, id: Int){
        if (userAuthorized){
            if(userAuthorizationState != UserAuthorizationState.Authorization){
                getUserInfo(id)
                userAuthorizationState = UserAuthorizationState.Authorization
            }
        }
        else{
            if(userAuthorizationState != UserAuthorizationState.NoAuthorization)
                userAuthorizationState = UserAuthorizationState.NoAuthorization
        }
    }

    fun getUserInfo(id: Int){
        viewModelScope.launch {
            try {
                var userInfo = userNetworkRepository.getUserInfo(id)
                userName = userInfo.fullName ?: ""
            }
            catch (e: IOException){
                return@launch
            }
            catch (e: HttpException){
                return@launch
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DisApplication)
                var userNetworkRepository = application.container.userRepository
                ProfileViewModel(
                    userNetworkRepository = userNetworkRepository
                )
            }
        }
    }
}