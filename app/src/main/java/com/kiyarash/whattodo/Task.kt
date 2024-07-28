package com.kiyarash.whattodo

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.util.Date

@Entity(tableName = "tasksTable")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "task_name") val taskName: String?,
    //@ColumnInfo(name = "due_date") val dueDate: Date?,
    //@ColumnInfo(name = "due_time") val dueTime: Time?,
    @ColumnInfo(name = "is_done") val isDone: Boolean
)