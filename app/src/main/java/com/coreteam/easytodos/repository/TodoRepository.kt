package com.coreteam.easytodos.repository

import com.coreteam.easytodos.model.Todo
import com.coreteam.easytodos.model.TodoDao
import io.reactivex.Completable
import io.reactivex.Observable

class TodoRepository(private val todoDao: TodoDao) {
    fun fetchAll(userId: Int) : Observable<List<Todo>> {
        return todoDao.getTodos(userId)
    }

    fun insert(todo: Todo)  {
        return todoDao.insertTodo(todo)
    }
}