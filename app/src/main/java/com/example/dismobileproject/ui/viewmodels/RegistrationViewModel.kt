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
import com.example.dismobileproject.data.model.RegistrationModel
import com.example.dismobileproject.data.repositories.UserRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate

class RegistrationParameterModel(
    initialFullName: String = "",
    initialBirthDate: LocalDate = LocalDate.now(),
    initialPhone: String = "",
    initialUsername: String = "",
    initialPassword: String = "",
){
    var fullName by mutableStateOf(initialFullName)
    var birthDate by mutableStateOf(initialBirthDate)
    var phone by mutableStateOf(initialPhone)
    var username by mutableStateOf(initialUsername)
    var password by mutableStateOf(initialPassword)
}

class RegistrationViewModel(
    private  val userRepository: UserRepository
): ViewModel() {

    var gendersState by mutableStateOf(mutableListOf<GenderSelectedModel>())

    var userInfoState: RegistrationParameterModel  by mutableStateOf(RegistrationParameterModel())

    var initView by mutableStateOf(false)

    fun getGenders(){
        viewModelScope.launch {
            try {
                var gendersModel = userRepository.getGenders()
                gendersState = gendersModel.map { it ->
                    GenderSelectedModel(
                        id = it.id ?: 0,
                        name = it.name ?: ""
                    )
                }.toMutableList()
                initView = true
            }
            catch (e: IOException){
                return@launch
            }
            catch (e: HttpException){
                return@launch
            }
        }
    }

    fun checkGender(genderId: Int){
        gendersState.find { it.isSelected }?.let { item ->
            item.isSelected = false
        }
        gendersState.find { it.id == genderId }?.let { item ->
            item.isSelected = true
        }
    }

    fun registrationUser(){
        viewModelScope.launch {
            try {
                var genderId = gendersState.firstOrNull() { it.isSelected }?.id

                if (genderId == null)
                    return@launch

                var registrationModel = RegistrationModel(
                    fullName = userInfoState.fullName,
                    birthDate = userInfoState.birthDate,
                    phone = userInfoState.phone,
                    genderId = genderId,
                    username = userInfoState.username,
                    password = userInfoState.password
                )

                userRepository.registrationUser(registrationModel)
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
                var userRepository = application.container.userRepository
                RegistrationViewModel(userRepository = userRepository)
            }
        }
    }
}