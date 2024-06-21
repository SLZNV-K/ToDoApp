package com.github.slznvk.todoapp.di

import android.content.Context
import androidx.room.Room
import com.github.slznvk.todoapp.data.dao.ToDoDao
import com.github.slznvk.todoapp.data.db.AppDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DbModule {

    @Singleton
    @Provides
    fun provideDb(
        @ApplicationContext
        context: Context
    ): AppDb = Room.databaseBuilder(context, AppDb::class.java, "app.db")
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

    @Provides
    fun provideDao(appDb: AppDb): ToDoDao = appDb.todoDao()
}