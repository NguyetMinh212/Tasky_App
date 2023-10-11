package com.example.tasky.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "dailyTask_table")
data class DailyTask(
    @PrimaryKey(autoGenerate = true)
    var id: Int?=null,
    var title: String,
    var category: String,
    var note: String,
    var timeStart: String,
    var timeEnd: String,
    var ischecked: Boolean,
    var date: String,

): Serializable

