package com.example.tasky.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tasky.model.DailyTask

@Dao
interface DailyTaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dailyTask: DailyTask)

    @Query("DELETE FROM dailyTask_table WHERE id=:id")
    suspend fun delete(id: Int)

    @Update
    suspend fun update(dailyTask: DailyTask)

    @Query("DELETE From dailyTask_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM dailyTask_table")
    fun getAllDailyTasks(): LiveData<List<DailyTask>>
}