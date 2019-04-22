package com.coreteam.easytodos.viewmodel

import android.annotation.SuppressLint
import android.util.Log
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

class MainViewModel: ViewModel() {
    @Inject
    lateinit var todoRepository: TodoRepository
    @Inject
    lateinit var auth: FirebaseAuth

    var todoList = MutableLiveData<List<Todo>>()

    init {
        App.instance.mainComponent.inject(this)
    }

    @SuppressLint("CheckResult")
    fun fetchTodos(userId: Int) {
        todoRepository.fetchAll(userId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.i("TODOSSSSSS", it.get(0).description)
                todoList.postValue(it)
            }, {
                e -> Log.wtf("ERROR", e.message)
            })
    }


    @SuppressLint("CheckResult")
    fun insertTodo() {
        val newTodo = Todo( 0, "this is a todo", false, 1)

        Observable.fromCallable { todoRepository.insert(newTodo) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ m ->
                    Log.wtf("MAINVIEWMODEL", "being call: ${newTodo.description}")
                    fetchTodos(newTodo.userId)
                },
                {
                    e -> Log.wtf("MAINVIEWMODEL", e.message)
                }
            )
    }

}