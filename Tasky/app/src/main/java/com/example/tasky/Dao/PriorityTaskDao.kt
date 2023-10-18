package com.example.tasky.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tasky.model.PriorityTask

@Dao
interface PriorityTaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(priorityTask: PriorityTask)

    @Query("DELETE FROM priorityTask_table WHERE idTask=:idTask")
    suspend fun delete(idTask: Int)

    @Update
    suspend fun update(priorityTask: PriorityTask)

    @Query("DELETE From priorityTask_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM priorityTask_table")
    fun getAllPriorityTasks(): LiveData<List<PriorityTask>>
}