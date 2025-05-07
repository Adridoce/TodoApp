package com.adridoce.todoapp.addtasks.domain.usecase

import com.adridoce.todoapp.addtasks.data.repository.TaskRepository
import com.adridoce.todoapp.addtasks.presentation.model.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTaskListUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    operator fun invoke(): Flow<List<TaskModel>> = taskRepository.taskList
}