package com.kiyarash.whattodo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.concurrent.Flow

@Dao
interface TasksDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertTask(task: Task)

	@Query("SELECT * FROM tasks")
	fun getAll(): MutableList<Task>

	@Query("DELETE FROM tasks WHERE id = :taskId")
	fun deleteTaskById(taskId: Int)
}