package com.adridoce.todoapp.addtasks.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM TaskEntity")
    fun getTaskList(): Flow<List<TaskEntity>>

    @Insert
    suspend fun addTask(item: TaskEntity)
}