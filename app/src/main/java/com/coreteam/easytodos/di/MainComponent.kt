package com.coreteam.easytodos.di

import com.coreteam.easytodos.view.MainActivity
import com.coreteam.easytodos.viewmodel.MainViewModel
import com.coreteam.easytodos.di.module.AppModule
import com.coreteam.easytodos.di.module.DataModule
import com.coreteam.easytodos.di.module.ViewModelModule
import com.coreteam.easytodos.viewmodel.LoginViewModel
import com.coreteam.easytodos.viewmodel.SplashViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, AppModule::class, ViewModelModule::class])
interface MainComponent {
    fun inject(mainViewModel: MainViewModel)
    fun inject(splashViewModel: SplashViewModel)
    fun inject(mainActivity: MainActivity)
    fun inject(loginViewModel: LoginViewModel)
}
