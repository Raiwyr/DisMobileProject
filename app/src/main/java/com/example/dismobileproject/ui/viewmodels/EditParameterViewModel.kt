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
import com.example.dismobileproject.data.model.GenderModel
import com.example.dismobileproject.data.model.UserInfoModel
import com.example.dismobileproject.data.repositories.UserRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate

class GenderSelectedModel(
    var id: Int,
    var name: String,
    initialChecked: Boolean = false
){
    var isSelected by mutableStateOf(initialChecked)
}

class UserParameterModel(
    var id: Int? = null,
    initialFullName: String = "",
    initialBirthDate: LocalDate = LocalDate.now(),
    initialPhone: String = "",
    initialGender: String = ""
){
    var fullName by mutableStateOf(initialFullName)
    var birthDate by mutableStateOf(initialBirthDate)
    var phone by mutableStateOf(initialPhone)
    var gender by mutableStateOf(initialGender)
}

class EditParameterViewModel(
    private  val userRepository: UserRepository
): ViewModel() {

    var gendersState by mutableStateOf(mutableListOf<GenderSelectedModel>())

    var userInfoState: UserParameterModel  by mutableStateOf(UserParameterModel())

    var initView by mutableStateOf(false)

    fun setUserInfo(userInfo: UserInfoModel){
        userInfoState = UserParameterModel(
            id = userInfo.id,
            initialFullName = userInfo.fullName ?: "",
            initialBirthDate = userInfo.birthDate ?: LocalDate.now(),
            initialPhone = userInfo.phone ?: "",
            initialGender = userInfo.gender?.name ?: ""
        )
        viewModelScope.launch {
            try {
                var gendersModel = userRepository.getGenders()
                gendersState = gendersModel.map { it ->
                    GenderSelectedModel(
                        id = it.id ?: 0,
                        name = it.name ?: ""
                    )
                }.toMutableList()
                gendersState.filter { it.id == userInfo.gender?.id }.forEach { it.isSelected = true }
            }
            catch (e: IOException){
                return@launch
            }
            catch (e: HttpException){
                return@launch
            }
        }
        initView = true
    }

    fun checkGender(indexGender: Int){
        for(index in gendersState.indices)
            gendersState[index].isSelected = index == indexGender
    }

    fun updateUserInfo(){
        viewModelScope.launch {
            try {
                var gender = gendersState.find { it.isSelected }
                var userInfo = UserInfoModel(
                    id = userInfoState.id,
                    fullName = userInfoState.fullName,
                    birthDate = userInfoState.birthDate,
                    phone = userInfoState.phone,
                    gender =
                    if(gender != null)
                        GenderModel(
                            id = gender.id,
                            name = gender.name
                        )
                    else
                        null
                )
                userRepository.updateUserInfo(userInfo)
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
                EditParameterViewModel(userRepository = userRepository)
            }
        }
    }
}