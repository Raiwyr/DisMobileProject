package com.example.dismobileproject

import android.app.Application
import com.example.dismobileproject.data.api.AppContainer
import com.example.dismobileproject.data.api.DefaultAppContainer

class DisApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}