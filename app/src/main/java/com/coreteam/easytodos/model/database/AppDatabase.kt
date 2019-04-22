package com.coreteam.easytodos.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.coreteam.easytodos.model.Todo
import com.coreteam.easytodos.model.TodoDao
import com.coreteam.easytodos.model.User
import com.coreteam.easytodos.model.UserDao

@Database(entities = [(Todo::class), (User::class)], version = 1, exportSchema = false)
abstract class  AppDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun todoDao(): TodoDao
}