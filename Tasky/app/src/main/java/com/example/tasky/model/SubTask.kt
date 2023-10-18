package com.example.tasky.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "subTask_table")
data class SubTask(
    @PrimaryKey(autoGenerate = true)
    var idSubTask: Int?=null,
    var title: String,
    var isDone: Boolean,
    var idTask: Int
): Serializable