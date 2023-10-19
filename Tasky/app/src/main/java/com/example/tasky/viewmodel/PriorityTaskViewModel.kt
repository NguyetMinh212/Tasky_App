package com.example.tasky.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tasky.database.PriorityTaskDatabase
import com.example.tasky.database.SubTaskDatabase
import com.example.tasky.model.PriorityTask
import com.example.tasky.model.SubTask
import com.example.tasky.repository.PriorityTaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PriorityTaskViewModel(application: Application): AndroidViewModel(application) {
    private val priorityTaskDao = PriorityTaskDatabase.getDatabase(application).priorityTaskDao()
    private val repository: PriorityTaskRepository
    private val subTaskDao = SubTaskDatabase.getDatabase(application).subTaskDao()

    val getAllPriorityTasks : LiveData<List<PriorityTask>>

    init {
        repository = PriorityTaskRepository(priorityTaskDao)
        getAllPriorityTasks = repository.getAllPriorityTask()
    }

    fun insert(priorityTask: PriorityTask){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(priorityTask)
        }

    }

    fun savePriorityTask(priorityTask: PriorityTask, listSubTask: List<SubTask>){
        viewModelScope.launch(Dispatchers.IO) {
            val idTask = priorityTaskDao.insert(priorityTask)
            for (subTask in listSubTask){
                subTask.idTask = idTask.toInt()
                subTaskDao.insert(subTask)
            }
        }
    }

    fun delete(idTask: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(idTask)
        }
    }

    fun update(priorityTask: PriorityTask){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(priorityTask)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }


}