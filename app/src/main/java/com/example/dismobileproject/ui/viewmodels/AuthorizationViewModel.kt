package com.example.dismobileproject.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dismobileproject.DisApplication
import com.example.dismobileproject.data.repositories.UserRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface UserAuthorizedState{
    data class Authorized(val id: Int): UserAuthorizedState
    object IncorrectData: UserAuthorizedState
    object ErrorAuthorized: UserAuthorizedState
    object NotAuthorized: UserAuthorizedState
}

class AuthorizationViewModel(
    private  val userNetworkRepository: UserRepository
): ViewModel() {

    var userAuthorizedState: UserAuthorizedState by mutableStateOf(UserAuthorizedState.NotAuthorized)
        private set

    fun authorizeUser(login: String, password: String){
        viewModelScope.launch {
            userAuthorizedState = try {
                var userId = userNetworkRepository.userAuthentication(login, password)
                if(userId != null) {
                    UserAuthorizedState.Authorized(userId)
                }
                else
                    UserAuthorizedState.IncorrectData
            }
            catch (e: IOException){
                UserAuthorizedState.ErrorAuthorized
            }
            catch (e: HttpException){
                UserAuthorizedState.ErrorAuthorized
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DisApplication)
                var userNetworkRepository = application.container.userRepository
                AuthorizationViewModel(
                    userNetworkRepository = userNetworkRepository
                )
            }
        }
    }
}