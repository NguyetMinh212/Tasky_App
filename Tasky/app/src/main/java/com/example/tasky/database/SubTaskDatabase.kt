package com.example.tasky.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tasky.Dao.SubTaskDao
import com.example.tasky.model.SubTask

@Database(entities = arrayOf(SubTask::class), version = 1, exportSchema = false)
abstract class SubTaskDatabase: RoomDatabase() {

    abstract fun subTaskDao(): SubTaskDao

    companion object{

        @Volatile
        private var INSTANCE: SubTaskDatabase?=null

        fun getDatabase(context: Context): SubTaskDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SubTaskDatabase::class.java,
                    "subTask_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return  instance
            }
        }
    }
}