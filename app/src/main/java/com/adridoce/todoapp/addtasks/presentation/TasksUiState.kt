package com.adridoce.todoapp.addtasks.presentation

import com.adridoce.todoapp.addtasks.presentation.model.TaskModel

sealed interface TasksUiState {
    data object Loading : TasksUiState
    data class Error(val throwable: Throwable) : TasksUiState
    data class Success(val taskList: List<TaskModel>) : TasksUiState
}