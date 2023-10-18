package com.example.tasky.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "priorityTask_table")
data class PriorityTask (
    @PrimaryKey(autoGenerate = true)
    var idTask: Int=0,
    var title: String,
    var category: String,
    var description: String,
    var timeStart: String,
    var timeEnd: String,
    var dayCreated: String
):Serializable