package com.coreteam.easytodos.view.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.coreteam.easytodos.R
import com.coreteam.easytodos.viewmodel.TodosViewModel
import kotlinx.android.synthetic.main.activity_add_todo.*

class AddTodoActivity : AppCompatActivity() {

    lateinit var todoViewModel: TodosViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        todoViewModel = ViewModelProviders.of(this).get(TodosViewModel::class.java)

        btnAddTodo.setOnClickListener {
            todoViewModel.insertTodo(todoDescription.text.toString())
            todoViewModel.isInserted.observe(this, Observer {
                if (it) finish()
                else {
                    Toast.makeText(this, "We could insert to the database.", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
    }
}
