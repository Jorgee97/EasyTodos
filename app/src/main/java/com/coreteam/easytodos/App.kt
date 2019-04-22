package com.coreteam.easytodos

import android.app.Application
import com.coreteam.easytodos.di.*
import com.coreteam.easytodos.di.module.AppModule
import com.coreteam.easytodos.di.module.DataModule
import com.google.firebase.FirebaseApp

class App : Application() {

    lateinit var mainComponent : MainComponent

    companion object {
        lateinit var instance : App private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        mainComponent = DaggerMainComponent.builder()
            .appModule(AppModule(this))
            .dataModule(DataModule())
            .build()
    }
}