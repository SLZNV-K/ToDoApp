package com.github.slznvk.todoapp.domain.dto

import java.time.LocalDate

data class TodoItem(
    val id: String,
    val content: String,
    val importance: Importance,
    val creationDate: String? = null,
    val deadline: String? = null,
    var isDone: Boolean = false,
    val changeDate: String? = LocalDate.now().toString()
)

enum class Importance(val value: String) {
    LOW("Низкий"),
    REGULAR("Нет"),
    URGENT("Высокий")
}
