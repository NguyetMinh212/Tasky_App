package com.example.tasky.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tasky.database.DailyTaskDatabase
import com.example.tasky.model.DailyTask
import com.example.tasky.repository.DailyTaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DailyTaskViewModel(application: Application): AndroidViewModel(application) {
    private val dailyTaskDao = DailyTaskDatabase.getDatabase(application).dailyTaskDao()
    private val repository: DailyTaskRepository

    val getAllDailyTasks: LiveData<List<DailyTask>>

    init {
        repository = DailyTaskRepository(dailyTaskDao)
        getAllDailyTasks = repository.getAllDailyTasks()
    }

    fun insert(dailyTask: DailyTask){
        viewModelScope.launch(Dispatchers.IO){
            repository.insert(dailyTask)
        }
    }

    fun delete(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(id)
        }
    }

    fun update(dailyTask: DailyTask){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(dailyTask)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

}