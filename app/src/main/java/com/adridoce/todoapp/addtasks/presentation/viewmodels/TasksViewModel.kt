package com.adridoce.todoapp.addtasks.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adridoce.todoapp.addtasks.domain.usecase.AddTaskUseCase
import com.adridoce.todoapp.addtasks.domain.usecase.DeleteTaskUseCase
import com.adridoce.todoapp.addtasks.domain.usecase.GetTaskListUseCase
import com.adridoce.todoapp.addtasks.domain.usecase.UpdateTaskUseCase
import com.adridoce.todoapp.addtasks.presentation.TasksUiState
import com.adridoce.todoapp.addtasks.presentation.TasksUiState.Success
import com.adridoce.todoapp.addtasks.presentation.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    getTaskListUseCase: GetTaskListUseCase
) : ViewModel() {

    val uiState: StateFlow<TasksUiState> = getTaskListUseCase().map(::Success)
        .catch { TasksUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TasksUiState.Loading)

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: MutableLiveData<Boolean> = _showDialog

    fun showDialog() {
        _showDialog.value = true
    }

    fun closeDialog() {
        _showDialog.value = false
    }

    fun onTaskAdded(taskName: String, taskDescription: String) {
        closeDialog()
        val task = TaskModel(taskName = taskName, taskDescription = taskDescription)
        viewModelScope.launch {
            addTaskUseCase(task)
        }
    }

    fun onCheckedChange(task: TaskModel) {
        viewModelScope.launch {
            updateTaskUseCase(task.copy(selected = !task.selected))
        }
    }

    fun onTaskDeleted(taskModel: TaskModel) {
        viewModelScope.launch {
            deleteTaskUseCase(taskModel)
        }
    }
}