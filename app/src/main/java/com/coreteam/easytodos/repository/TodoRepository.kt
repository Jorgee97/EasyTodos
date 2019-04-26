package com.coreteam.easytodos.repository

import com.coreteam.easytodos.model.Todo
import com.coreteam.easytodos.model.TodoDao
import io.reactivex.Completable
import io.reactivex.Observable

class TodoRepository(private val todoDao: TodoDao) {
    fun fetchAll(userId: String) : Observable<List<Todo>> {
        return todoDao.getTodos(userId)
    }

    fun insert(todo: Todo)  {
        return todoDao.insertTodo(todo)
    }

    fun updateCompletion(completed: Boolean, todoId: Int){
        return todoDao.updateTodoComplete(completed, todoId)
    }

    fun updateDescription(description: String, todoId: Int) {
        return todoDao.updateTodoDescription(description, todoId)
    }

    fun deleteTodo(todoId: Int) {
        return todoDao.deleteTodoById(todoId)
    }
}