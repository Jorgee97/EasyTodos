package com.coreteam.easytodos.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coreteam.easytodos.App
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SplashViewModel : ViewModel() {

    var isUserLoggedIn = MutableLiveData<Boolean>()

    @Inject
    lateinit var auth: FirebaseAuth

    init {
        App.instance.mainComponent.inject(this)
    }

    fun validateIfUserIsLoggedIn() {
        val currentUser = auth.currentUser
        if (currentUser != null) isUserLoggedIn.postValue(true)
        else isUserLoggedIn.postValue(false)
    }
}