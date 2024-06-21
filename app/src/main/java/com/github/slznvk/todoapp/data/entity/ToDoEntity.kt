package com.github.slznvk.todoapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.slznvk.todoapp.domain.dto.Importance
import com.github.slznvk.todoapp.domain.dto.TodoItem

@Entity
data class ToDoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val content: String,
    val importance: Importance,
    val deadline: String? = null,
    val isDone: Boolean = false,
    val creationDate: String?,
    val changeDate: String? = null
) {
    fun toDto() = TodoItem(
        id = id.toString(),
        content = content,
        importance = importance,
        deadline = deadline,
        isDone = isDone,
        creationDate = creationDate,
        changeDate = changeDate
    )

    companion object {
        fun fromDto(dto: TodoItem) = ToDoEntity(
            id = dto.id.toLong(),
            content = dto.content,
            importance = dto.importance,
            deadline = dto.deadline,
            isDone = dto.isDone,
            creationDate = dto.creationDate,
            changeDate = dto.changeDate,
        )
    }
}

fun List<ToDoEntity>.toDto(): List<TodoItem> = map(ToDoEntity::toDto)