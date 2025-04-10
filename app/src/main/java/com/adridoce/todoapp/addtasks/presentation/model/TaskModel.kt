package com.adridoce.todoapp.addtasks.presentation.model

data class TaskModel(
    val id: Long = System.currentTimeMillis(),
    val taskName: String,
    val taskDescription: String,
    var selected: Boolean = false
)
