package com.example.tasky.repository

import androidx.lifecycle.LiveData
import com.example.tasky.Dao.DailyTaskDao
import com.example.tasky.model.DailyTask

class DailyTaskRepository(private val dailyTaskDao: DailyTaskDao) {
    suspend fun insert(dailyTask: DailyTask) = dailyTaskDao.insert(dailyTask)

    suspend fun updateData(dailyTask: DailyTask)= dailyTaskDao.update(dailyTask)

    suspend fun deleteItem(id: Int) = dailyTaskDao.delete(id)

    suspend fun deleteAll(){
        dailyTaskDao.deleteAll()
    }

    fun getAllDailyTasks():LiveData<List<DailyTask>> = dailyTaskDao.getAllDailyTasks()

    fun calendarSearch(searchQuery: String) : LiveData<List<DailyTask>> = dailyTaskDao.calendarSearch(searchQuery)
}