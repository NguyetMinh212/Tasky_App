package com.example.tasky.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tasky.Dao.PriorityTaskDao
import com.example.tasky.model.PriorityTask

@Database(entities = arrayOf(PriorityTask::class), version = 1, exportSchema = false)
abstract class PriorityTaskDatabase: RoomDatabase() {
    abstract fun priorityTaskDao(): PriorityTaskDao

    companion object{
        @Volatile
        private var INSTANCE: PriorityTaskDatabase?=null

        fun getDatabase(context: Context): PriorityTaskDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PriorityTaskDatabase::class.java,
                    "priorityTask_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return  instance
            }
        }
    }
}