package com.coreteam.easytodos.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.coreteam.easytodos.R
import com.coreteam.easytodos.view.main.MainActivity
import com.coreteam.easytodos.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        
        btnLogin.isEnabled = false
        password.doAfterTextChanged { text -> loginViewModel.password = text?.toString() ?: "" }

        loginViewModel.isPasswordValid.observe(this, Observer {
            btnLogin.isEnabled = it ?: false
        })

        btnLogin.setOnClickListener {
            loginViewModel.createUser(email.text.toString(), password.text.toString())
            progressBar.visibility = View.VISIBLE

            loginViewModel.registerOrLoginSuccessful.observe(this, Observer {
                if (it) {
                    progressBar.visibility = View.GONE
                    val mainIntent = Intent(this, MainActivity::class.java)
                    startActivity(mainIntent)
                    finish()
                } else {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, loginViewModel.message.value, Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}
