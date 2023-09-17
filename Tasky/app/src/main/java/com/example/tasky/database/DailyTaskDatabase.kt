package com.example.tasky.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tasky.Dao.DailyTaskDao
import com.example.tasky.model.DailyTask


@Database(entities = arrayOf(DailyTask::class), version = 1, exportSchema = false)
abstract class DailyTaskDatabase: RoomDatabase(){
    abstract fun dailyTaskDao(): DailyTaskDao

    companion object{
        @Volatile
        private var INSTANCE: DailyTaskDatabase?=null

        fun getDatabase(context: Context): DailyTaskDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DailyTaskDatabase::class.java,
                    "dailyTask_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return  instance
            }
        }

    }
}