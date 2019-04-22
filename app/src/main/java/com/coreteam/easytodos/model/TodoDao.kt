package com.coreteam.easytodos.model

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface TodoDao {

    @Query("SELECT * FROM Todo WHERE userId = :userId")
    fun getTodos(userId: Int) : Observable<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodo(todo: Todo)

    @Query("DELETE FROM Todo WHERE todoId = :todoId")
    fun deleteTodoById(todoId: Int)
}
