package com.coreteam.easytodos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coreteam.easytodos.App
import com.google.firebase.auth.*
import javax.inject.Inject

class LoginViewModel: ViewModel() {

    @Inject
    lateinit var auth: FirebaseAuth

    init {
        App.instance.mainComponent.inject(this)
    }

    private val _isPasswordValid = MutableLiveData<Boolean>()
    val isPasswordValid:LiveData<Boolean>
        get() = _isPasswordValid

    private val _registerOrLoginSuccessful = MutableLiveData<Boolean>()
    val registerOrLoginSuccessful: LiveData<Boolean>
        get() = _registerOrLoginSuccessful

    var message = MutableLiveData<String>()

    var password = ""
        set(value) {
            field = value
            validatePasswordLength()
        }

    private fun validatePasswordLength() {
        if (password.length >= 8) _isPasswordValid.postValue(true)
        else _isPasswordValid.postValue(false)
    }

    fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    message.postValue("")
                    _registerOrLoginSuccessful.postValue(true)
                } else {
                    val exception = it.exception
                    if (exception is FirebaseAuthUserCollisionException) {
                        loginUser(email, password)
                    } else {
                        _registerOrLoginSuccessful.postValue(false)
                        message.postValue("There was an error trying to create the user, please try again.")
                    }
                }
            }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    message.postValue("")
                    _registerOrLoginSuccessful.postValue(true)
                } else {
                    val exception = it.exception
                    if (exception is FirebaseAuthInvalidUserException)
                        message.postValue("Invalid email was provided. Please provide a valid one.")
                    else if (exception is FirebaseAuthInvalidCredentialsException)
                        message.postValue("Invalid password. Please try again.")

                    _registerOrLoginSuccessful.postValue(false)
                }
            }
    }
}