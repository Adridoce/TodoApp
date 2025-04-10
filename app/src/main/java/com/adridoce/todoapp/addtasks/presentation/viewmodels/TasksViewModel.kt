package com.adridoce.todoapp.addtasks.presentation.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adridoce.todoapp.addtasks.presentation.model.TaskModel
import javax.inject.Inject

class TasksViewModel @Inject constructor() : ViewModel() {

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: MutableLiveData<Boolean> = _showDialog

    private val _tasksList = mutableStateListOf<TaskModel>()
    var tasksList: List<TaskModel> = _tasksList

    fun showDialog() {
        _showDialog.value = true
    }

    fun closeDialog() {
        _showDialog.value = false
    }

    fun onTaskAdded(taskName: String, taskDescription: String) {
        closeDialog()
        _tasksList.add(TaskModel(taskName = taskName, taskDescription = taskDescription))
    }

    fun onCheckedChange(task: TaskModel) {
        val index = _tasksList.indexOf(task)
        _tasksList[index] = _tasksList[index].let {
            it.copy(selected = !it.selected)
        }
    }
}