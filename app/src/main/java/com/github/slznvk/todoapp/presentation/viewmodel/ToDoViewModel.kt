package com.github.slznvk.todoapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.slznvk.todoapp.domain.dto.Importance
import com.github.slznvk.todoapp.domain.dto.TodoItem
import com.github.slznvk.todoapp.domain.repository.TodoRepository
import com.github.slznvk.todoapp.presentation.model.StateModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    val todoItems = repository.getTodoItems()
    val filteredItems = MutableStateFlow(listOf<TodoItem>())

    private val _dataState = MutableStateFlow(StateModel())
    val dataState: StateFlow<StateModel>
        get() = _dataState

    private val _pickedItem = MutableStateFlow(empty)
    val pickedItem: Flow<TodoItem>
        get() = _pickedItem

    fun removeItemById(id: String) {
        try {
            repository.removeItemById(id)
            _pickedItem.value = empty
        } catch (e: Exception) {
            _dataState.value = StateModel(error = true)
        }
    }

    fun saveItem() {
        try {
            _pickedItem.value.let {
                repository.saveItem(it)
            }
            _pickedItem.value = empty
        } catch (e: Exception) {
            _dataState.value = StateModel(error = true)
        }
    }

    fun edit(todoItem: TodoItem) {
        _pickedItem.value = todoItem
    }

    fun changeContent(content: String, deadline: String?, priority: String) {
        _pickedItem.value.let { todoItem ->
            val newContent = content.trim()
            if (newContent != todoItem.content) {
                _pickedItem.value = todoItem.copy(
                    content = newContent,
                    deadline = deadline,
                    importance = when (priority) {
                        "Низкий" -> Importance.LOW
                        "Высокий" -> Importance.URGENT
                        else -> Importance.REGULAR
                    }
                )
            }
        }
    }

    fun changeDone(item: TodoItem) {
        try {
            repository.saveItem(item)
        } catch (e: Exception) {
            _dataState.value = StateModel(error = true)
        }
    }

    fun filter() {
        viewModelScope.launch {
            filteredItems.value = todoItems.first().filter { !it.isDone }
        }
    }

    fun cancel() {
        _pickedItem.value = empty
    }
}

private val empty = TodoItem("0", content = "", importance = Importance.REGULAR, creationDate = "")