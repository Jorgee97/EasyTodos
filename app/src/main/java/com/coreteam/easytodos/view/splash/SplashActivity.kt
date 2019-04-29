package com.coreteam.easytodos.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.coreteam.easytodos.R
import com.coreteam.easytodos.view.login.LoginActivity
import com.coreteam.easytodos.view.main.MainActivity
import com.coreteam.easytodos.viewmodel.SplashViewModel

class SplashActivity : AppCompatActivity() {

    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar!!.hide()

        splashViewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)

        splashViewModel.validateIfUserIsLoggedIn()
        splashViewModel.isUserLoggedIn.observe(this, Observer {
            if (it) {
                val mainIntent = Intent(this, MainActivity::class.java)
                Handler().postDelayed(Runnable {
                    startActivity(mainIntent)
                    finish()
                }, 1000)
            }
            else {
                val loginIntent = Intent(this, LoginActivity::class.java)
                Handler().postDelayed(Runnable {
                    startActivity(loginIntent)
                    finish()
                }, 1000)
            }
        })
    }
}
