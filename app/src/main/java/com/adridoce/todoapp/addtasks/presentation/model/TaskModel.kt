package com.adridoce.todoapp.addtasks.presentation.model

data class TaskModel(
    val id: Int = System.currentTimeMillis().hashCode(),
    val taskName: String,
    val taskDescription: String,
    var selected: Boolean = false
)
