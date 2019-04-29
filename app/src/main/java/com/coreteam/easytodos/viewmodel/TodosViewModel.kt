package com.coreteam.easytodos.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coreteam.easytodos.App
import com.coreteam.easytodos.model.Todo
import com.coreteam.easytodos.repository.TodoRepository
import com.coreteam.easytodos.util.StringGenerator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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

    var currentUser: FirebaseUser?

    init {
        App.instance.mainComponent.inject(this)

        currentUser = auth.currentUser
    }

    private val _isInserted = MutableLiveData<Boolean>()
    val isInserted: LiveData<Boolean>
        get() = _isInserted

    fun logout() {
        auth.signOut()
    }

    // TODO: Fix unattended check results from rxjava subscriptions
    @SuppressLint("CheckResult")
    fun insertTodo(description: String) {
        if (currentUser != null) {
            val newTodo = Todo(StringGenerator().AlphanumericGenerator(), description, false, currentUser!!.uid)
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
    fun updateTodoCompletion(todoId: String, completed: Boolean) {
        Observable.fromCallable { todoRepository.updateCompletion(!completed, todoId, currentUser!!.uid) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                fetchTodos()
                Log.wtf("UPDATE", "We made it")
            }, {
                Log.wtf("ERROR", it.message)
            })
    }

    fun updateTodoDescription(todoId: String, description: String) {
        Observable.fromCallable { todoRepository.updateDescription(description, todoId, currentUser!!.uid) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    @SuppressLint("CheckResult")
    fun fetchTodos() {
        todoRepository.fetchAll(currentUser!!.uid)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                todoList.postValue(it)
            }, { e -> Log.wtf("ERROR", e.message) })
    }

    fun deleteTodo(todoId: String) {
        Observable.fromCallable { todoRepository.deleteTodo(todoId, currentUser!!.uid) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { fetchTodos() }
    }
}