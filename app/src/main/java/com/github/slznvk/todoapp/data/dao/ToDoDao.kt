package com.github.slznvk.todoapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.github.slznvk.todoapp.data.entity.ToDoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    @Query("SELECT * FROM ToDoEntity ORDER BY id DESC")
    fun getAll(): Flow<List<ToDoEntity>>

    @Query("SELECT * FROM ToDoEntity WHERE id = :id")
    fun getItemById(id: String): ToDoEntity

    @Upsert
    fun saveItem(todoItem: ToDoEntity): Long

    @Query("DELETE FROM ToDoEntity WHERE id = :id")
    fun removeById(id: String)
}