package com.coreteam.easytodos.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    val id: Int,
    val userId: String,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String
)