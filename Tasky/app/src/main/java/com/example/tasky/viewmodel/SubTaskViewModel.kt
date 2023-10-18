package com.example.tasky.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tasky.database.SubTaskDatabase
import com.example.tasky.model.SubTask
import com.example.tasky.repository.SubTaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubTaskViewModel(application: Application): AndroidViewModel(application) {

    private val subTaskDao = SubTaskDatabase.getDatabase(application).subTaskDao()
    private val repository : SubTaskRepository


    init{
        repository = SubTaskRepository(subTaskDao)
    }

    fun insert(subTask: SubTask){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(subTask)
        }
    }

    fun update(subTask: SubTask){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(subTask)
        }
    }

    fun delete(idSubTask: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(idSubTask)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }
    fun getAllSubTaskFromPriorityTask(idTask: Int) : LiveData<List<SubTask>> = repository.getAllSubTasksFromPriorityTask(idTask)
}