package com.github.slznvk.todoapp.domain.repository

import com.github.slznvk.todoapp.domain.dto.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodoItems(): Flow<List<TodoItem>>
    fun getItemById(id: String): TodoItem
    fun saveItem(item: TodoItem)
    fun removeItemById(id: String)
}