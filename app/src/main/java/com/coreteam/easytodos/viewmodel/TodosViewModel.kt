package com.coreteam.easytodos.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coreteam.easytodos.App
import com.coreteam.easytodos.model.Todo
import com.coreteam.easytodos.repository.TodoRepository
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TodosViewModel : ViewModel() {

    var todoList = MutableLiveData<List<Todo>>()

    @Inject
    lateinit var todoRepository: TodoRepository
    @Inject
    lateinit var auth: FirebaseAuth

    init {
        App.instance.mainComponent.inject(this)
    }

    private val _isInserted = MutableLiveData<Boolean>()
    val isInserted: LiveData<Boolean>
        get() = _isInserted


    // TODO: Fix unattended check results from rxjava subscriptions
    @SuppressLint("CheckResult")
    fun insertTodo(description: String) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val newTodo = Todo(0, description, false, currentUser.uid)
            Observable.fromCallable { todoRepository.insert(newTodo) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    _isInserted.postValue(true)
                },
                    { e ->
                        Log.wtf("MAINVIEWMODEL", e.message)
                        _isInserted.postValue(false)
                    }
                )
        }
    }

    @SuppressLint("CheckResult")
    fun updateTodoCompletion(todoId: Int, completed: Boolean) {
        Observable.fromCallable { todoRepository.updateCompletion(!completed, todoId) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                fetchTodos()
                Log.wtf("UPDATE", "We made it")
            }, {
                Log.wtf("ERROR", it.message)
            })
    }

    @SuppressLint("CheckResult")
    fun fetchTodos() {
        val currentUser = auth.currentUser
        todoRepository.fetchAll(currentUser!!.uid)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                todoList.postValue(it)
            }, { e -> Log.wtf("ERROR", e.message) })
    }

    fun deleteTodo(todoId: Int) {
        Observable.fromCallable { todoRepository.deleteTodo(todoId) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { fetchTodos() }
    }
}