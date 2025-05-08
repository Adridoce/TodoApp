package com.adridoce.todoapp.addtasks.data.repository

import com.adridoce.todoapp.addtasks.data.local.TaskDao
import com.adridoce.todoapp.addtasks.data.local.TaskEntity
import com.adridoce.todoapp.addtasks.presentation.model.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    val taskList: Flow<List<TaskModel>> = taskDao.getTaskList().map { items ->
        items.map { TaskModel(it.id, it.taskName, it.taskDescription, it.selected) }
    }

    suspend fun add(taskModel: TaskModel) {
        taskDao.addTask(taskModel.toData())
    }

    suspend fun update(taskModel: TaskModel) {
        taskDao.updateTask(taskModel.toData())
    }

    suspend fun delete(taskModel: TaskModel) {
        taskDao.deleteTask(taskModel.toData())
    }
}

fun TaskModel.toData(): TaskEntity {
    return TaskEntity(this.id, this.taskName, this.taskDescription, this.selected)
}