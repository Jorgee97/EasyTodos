package com.coreteam.easytodos.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.coreteam.easytodos.viewmodel.MainViewModel
import com.coreteam.easytodos.R

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        Log.wtf("MAINACTIVITY", "Hello from mainactivity")
        mainViewModel.insertTodo()

        mainViewModel.todoList.observe(this, Observer {
            for (item in it) {
                Log.wtf("MAINACTIVITY", item.description)
            }
        })

    }
}
