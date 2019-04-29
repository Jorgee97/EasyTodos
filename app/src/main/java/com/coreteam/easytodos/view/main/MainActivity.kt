package com.coreteam.easytodos.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coreteam.easytodos.R
import com.coreteam.easytodos.util.SwipeToDeleteHelper
import com.coreteam.easytodos.view.add.AddTodoActivity
import com.coreteam.easytodos.view.login.LoginActivity
import com.coreteam.easytodos.viewmodel.TodosViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.todo_item.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var mainViewModel: TodosViewModel
    private lateinit var mAdapter : MainTodosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        setupViewModel()
        fetchAndObserveTodos()
        setupSwipeToDelete()
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
            mAdapter = MainTodosAdapter(it) {todo, _ ->
                mainViewModel.updateTodoCompletion(todo.todoId, todo.completed)
                Log.wtf("OOPS", todo.completed.toString())
            }
            todos_recycler.adapter = mAdapter
        })
    }

    private fun setupSwipeToDelete() {
        val swipeToDeleteHelper = object : SwipeToDeleteHelper(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val swipedPosition = viewHolder.adapterPosition

                val deletedItem = mAdapter.getItemAtPosition(swipedPosition)
                mAdapter.removeItem(swipedPosition)

                mainViewModel.deleteTodo(deletedItem.todoId)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteHelper)
        itemTouchHelper.attachToRecyclerView(todos_recycler)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId) {
            R.id.btn_logout -> {
                mainViewModel.logout()
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)
                finish()

                return  true
            }
        }

        return super.onOptionsItemSelected(item)
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
