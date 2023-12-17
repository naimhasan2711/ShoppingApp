package com.example.android.shoppingapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


//This is the application class
@HiltAndroidApp
class MyShoppingApp:Application() {
    override fun onCreate() {
        super.onCreate()
    }
}