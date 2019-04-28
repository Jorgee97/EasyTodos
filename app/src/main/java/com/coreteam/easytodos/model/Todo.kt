package com.coreteam.easytodos.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class Todo(
    @PrimaryKey
    val todoId: String,
    val description: String,
    val completed: Boolean,
    val userId: String
) {
    constructor() : this("", "", false, "")
}