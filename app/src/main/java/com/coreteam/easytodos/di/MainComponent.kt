package com.coreteam.easytodos.di

import com.coreteam.easytodos.view.main.MainActivity
import com.coreteam.easytodos.di.module.AppModule
import com.coreteam.easytodos.di.module.DataModule
import com.coreteam.easytodos.viewmodel.LoginViewModel
import com.coreteam.easytodos.viewmodel.SplashViewModel
import com.coreteam.easytodos.viewmodel.TodosViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, AppModule::class])
interface MainComponent {
    fun inject(splashViewModel: SplashViewModel)
    fun inject(loginViewModel: LoginViewModel)
    fun inject(todosViewModel: TodosViewModel)
}
