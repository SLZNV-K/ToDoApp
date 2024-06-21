package com.github.slznvk.todoapp.data.repository

import com.github.slznvk.todoapp.data.dao.ToDoDao
import com.github.slznvk.todoapp.domain.dto.TodoItem
import com.github.slznvk.todoapp.domain.repository.TodoRepository
import com.github.slznvk.todoapp.data.entity.ToDoEntity.Companion.fromDto
import com.github.slznvk.todoapp.data.entity.toDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val dao: ToDoDao,
) : TodoRepository {

    override fun getTodoItems(): Flow<List<TodoItem>> = dao.getAll().map { it.toDto() }

    override fun getItemById(id: String): TodoItem = dao.getItemById(id).toDto()

    override fun saveItem(item: TodoItem) {
        dao.saveItem(fromDto(item))
    }

    override fun removeItemById(id: String) {
        dao.removeById(id)
    }
}