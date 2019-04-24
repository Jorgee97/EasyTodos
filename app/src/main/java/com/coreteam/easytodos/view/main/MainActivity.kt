package com.coreteam.easytodos.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coreteam.easytodos.R
import com.coreteam.easytodos.view.add.AddTodoActivity
import com.coreteam.easytodos.viewmodel.TodosViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.todo_item.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var mainViewModel: TodosViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        setupViewModel()
        fetchAndObserveTodos()
    }

    override fun onRestart() {
        super.onRestart()
        fetchAndObserveTodos()
    }

    private fun setupRecyclerView() {
        todos_recycler.layoutManager = LinearLayoutManager(this)
        todos_recycler.hasFixedSize()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(this).get(TodosViewModel::class.java)
    }

    private fun fetchAndObserveTodos() {
        mainViewModel.fetchTodos()
        mainViewModel.todoList.observe(this, Observer {
            val adapter = MainTodosAdapter(it) {todo, _ ->
                mainViewModel.updateTodoCompletion(todo.todoId, todo.completed)
                Log.wtf("OOPS", todo.completed.toString())
            }
            todos_recycler.adapter = adapter
        })
    }

    override fun onClick(v: View?) {
        when(v) {
            btnAddTodo -> {
                val addTodoIntent = Intent(this, AddTodoActivity::class.java)
                startActivity(addTodoIntent)
            }
        }
    }
}
