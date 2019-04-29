package com.coreteam.easytodos.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.coreteam.easytodos.R
import com.coreteam.easytodos.viewmodel.TodosViewModel
import kotlinx.android.synthetic.main.activity_update_todo.*

class UpdateTodoActivity : AppCompatActivity() {

    lateinit var todosViewModel: TodosViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_todo)

        val todoId = intent.extras.get("TODO_ID").toString()
        val actualDescription = intent.extras.get("ACTUAL_DESCRIPTION").toString()

        edit_description.setText(actualDescription)
        todosViewModel = ViewModelProviders.of(this).get(TodosViewModel::class.java)

        btn_update.setOnClickListener {
            val newDescription = edit_description.text.toString()

            todosViewModel.updateTodoDescription(todoId, newDescription)
            finish()
        }
    }
}
