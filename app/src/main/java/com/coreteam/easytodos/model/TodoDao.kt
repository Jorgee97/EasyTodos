package com.coreteam.easytodos.model

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface TodoDao {

    @Query("SELECT * FROM Todo WHERE userId = :userId")
    fun getTodos(userId: String) : Observable<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodo(todo: Todo)

    @Query("UPDATE Todo SET completed = :completed WHERE todoId = :todoId")
    fun updateTodoComplete(completed: Boolean, todoId: String)

    @Query("UPDATE Todo SET description = :description WHERE todoId = :todoId")
    fun updateTodoDescription(description: String, todoId: String)

    @Query("DELETE FROM Todo WHERE todoId = :todoId")
    fun deleteTodoById(todoId: String)
}
