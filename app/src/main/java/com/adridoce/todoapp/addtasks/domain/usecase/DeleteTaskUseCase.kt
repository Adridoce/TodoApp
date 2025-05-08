package com.adridoce.todoapp.addtasks.domain.usecase

import com.adridoce.todoapp.addtasks.data.repository.TaskRepository
import com.adridoce.todoapp.addtasks.presentation.model.TaskModel
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(taskModel: TaskModel) {
        taskRepository.delete(taskModel)
    }
}