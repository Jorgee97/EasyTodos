package com.coreteam.easytodos.di.module

import android.content.Context
import androidx.room.PrimaryKey
import androidx.room.Room
import com.coreteam.easytodos.model.database.AppDatabase
import com.coreteam.easytodos.repository.TodoRepository
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "easy_todo.db")
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideTodoRepository(appDatabase: AppDatabase) : TodoRepository {
        return TodoRepository(appDatabase.todoDao())
    }

    @Singleton
    @Provides
    fun provideFirebaseAuthInstance(context: Context) : FirebaseAuth {
        FirebaseApp.initializeApp(context)
        return FirebaseAuth.getInstance()
    }
}