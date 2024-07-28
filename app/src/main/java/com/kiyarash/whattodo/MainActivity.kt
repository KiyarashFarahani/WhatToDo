package com.kiyarash.whattodo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.kiyarash.whattodo.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
	private var _binding: ActivityMainBinding? = null
	private val binding get() = _binding!!
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		_binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		binding.fabButton.setOnClickListener {
			val modalBottomSheet = AddTaskFragment()
			modalBottomSheet.show(supportFragmentManager, AddTaskFragment.TAG)
		}

		lifecycleScope.launch(Dispatchers.IO) {
			val database = Room.databaseBuilder(
				applicationContext,
				AppDatabase::class.java,
				"tasks"
			).build()

			val data = database.taskDao().getAll()
			withContext(Dispatchers.Main) {
				binding.recyclerView.adapter = TaskAdapter(data)
			}
		}

	}
}