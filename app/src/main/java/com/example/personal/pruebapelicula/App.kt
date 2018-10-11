package com.example.personal.pruebapelicula

import android.app.Application
import com.example.personal.pruebapelicula.di.appModule
import org.koin.android.ext.android.startKoin

class App:Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
    }
}