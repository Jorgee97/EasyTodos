package com.coreteam.easytodos.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val todoId: Int = 0,
    val description: String,
    val completed: Boolean,
    val userId: Int
)