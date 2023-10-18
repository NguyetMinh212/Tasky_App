package com.example.tasky.repository

import androidx.lifecycle.LiveData
import com.example.tasky.Dao.PriorityTaskDao
import com.example.tasky.model.PriorityTask

class PriorityTaskRepository(private val priorityTaskDao: PriorityTaskDao) {

    suspend fun insert(priorityTask:PriorityTask):Long = priorityTaskDao.insert(priorityTask)

    suspend fun updateData(priorityTask: PriorityTask) = priorityTaskDao.update(priorityTask)

    suspend fun deleteItem(idTask:Int) = priorityTaskDao.delete(idTask)

    suspend fun deleteAll() = priorityTaskDao.deleteAll()

    fun getAllPriorityTask():LiveData<List<PriorityTask>> = priorityTaskDao.getAllPriorityTasks()
}