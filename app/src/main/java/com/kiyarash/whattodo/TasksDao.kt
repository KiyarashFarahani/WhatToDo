package com.kiyarash.whattodo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.util.concurrent.Flow

@Dao
interface TasksDao {
    @Insert
    fun insertTask(task: Task)

	@Query("SELECT * FROM tasks")
	fun getAll(): List<Task>
}