package com.example.tasky.repository

import com.example.tasky.Dao.SubTaskDao
import com.example.tasky.model.SubTask

class SubTaskRepository(private val subTaskDao: SubTaskDao) {

    suspend fun insert(subTask: SubTask) = subTaskDao.insert(subTask)

    suspend fun updateData(subTask: SubTask) = subTaskDao.update(subTask)

    suspend fun deleteItem(idSubTask:Int) = subTaskDao.delete(idSubTask)

    suspend fun deleteAll() = subTaskDao.deleteAll()

    fun getAllSubTasksFromPriorityTask(idTask:Int) = subTaskDao.getAllSubTasksFromPriorityTask(idTask)

}