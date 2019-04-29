package com.coreteam.easytodos.repository

import android.util.Log
import com.coreteam.easytodos.model.Todo
import com.coreteam.easytodos.model.TodoDao
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TodoRepository(private val todoDao: TodoDao, private val firebaseDatabase: FirebaseDatabase) {
    fun fetchAll(userId: String) : Observable<List<Todo>> {
        val todosReference = firebaseDatabase.getReference("users").child(userId).child("todos")
        val listener = object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (item in p0.children){
                    val todo = item.getValue(Todo::class.java)
                    Log.wtf("ValueFromFirebase", todo!!.description)
                    updateLocalDatabase(todo)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.wtf("ErrorOnFirebaseFetch", p0.message)
            }
        }
        todosReference.addValueEventListener(listener)
        return todoDao.getTodos(userId)
    }

    fun updateLocalDatabase(todo: Todo) {
        Observable.fromCallable { todoDao.insertTodo(todo) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun insert(todo: Todo)  {
        Log.wtf("REPOSITORY", todo.userId)
        firebaseDatabase.getReference("users").child(todo.userId).child("todos").child(todo.todoId).setValue(todo)
        return todoDao.insertTodo(todo)
    }

    fun updateCompletion(completed: Boolean, todoId: String, userId: String){
        firebaseDatabase.getReference("users").child(userId).child("todos").child(todoId).child("completed").setValue(completed)
        return todoDao.updateTodoComplete(completed, todoId)
    }

    fun updateDescription(description: String, todoId: String, userId: String) {
        firebaseDatabase.getReference("users").child(userId).child("todos").child(todoId).child("description").setValue(description)
        return todoDao.updateTodoDescription(description, todoId)
    }

    fun deleteTodo(todoId: String, userId: String) {
        firebaseDatabase.getReference("users").child(userId).child("todos").child(todoId).removeValue()
        return todoDao.deleteTodoById(todoId)
    }
}