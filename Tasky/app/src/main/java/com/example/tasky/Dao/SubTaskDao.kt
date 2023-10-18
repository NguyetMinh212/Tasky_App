package com.example.tasky.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tasky.model.SubTask

@Dao
interface SubTaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(subTask: SubTask)

    @Query("DELETE FROM subTask_table WHERE idSubTask=:idSubTask")
    suspend fun delete(idSubTask: Int)

    @Update
    suspend fun update(subTask: SubTask)

    @Query("DELETE From subTask_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM subTask_table WHERE idTask LIKE :idTask")
    fun getAllSubTasksFromPriorityTask(idTask:Int): LiveData<List<SubTask>>
}