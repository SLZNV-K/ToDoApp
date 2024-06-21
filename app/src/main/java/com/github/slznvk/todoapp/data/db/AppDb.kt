package com.github.slznvk.todoapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.slznvk.todoapp.data.dao.ToDoDao
import com.github.slznvk.todoapp.data.entity.ToDoEntity

@Database(
    entities = [ToDoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDb : RoomDatabase() {
    abstract fun todoDao(): ToDoDao
}
