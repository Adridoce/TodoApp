package com.adridoce.todoapp.addtasks.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskEntity::class] , version = 1, exportSchema = false)
abstract class TodoDatabase:RoomDatabase() {
    //DAO
    abstract fun taskDao():TaskDao
}