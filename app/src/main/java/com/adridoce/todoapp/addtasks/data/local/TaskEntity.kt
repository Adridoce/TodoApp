package com.adridoce.todoapp.addtasks.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
    @PrimaryKey
    val id: Int,
    val taskName: String,
    val taskDescription: String,
    var selected: Boolean = false
)