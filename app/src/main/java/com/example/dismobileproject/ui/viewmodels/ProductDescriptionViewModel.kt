package com.example.dismobileproject.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dismobileproject.DisApplication
import com.example.dismobileproject.data.repositories.UserRepository

class ProductDescriptionViewModel(
    private  val userRepository: UserRepository
): ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DisApplication)
                var userRepository = application.container.userRepository
                ProductDescriptionViewModel(userRepository = userRepository)
            }
        }
    }
}