package com.example.android.shoppingapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyShoppingApp:Application() {
    override fun onCreate() {
        super.onCreate()
    }
}