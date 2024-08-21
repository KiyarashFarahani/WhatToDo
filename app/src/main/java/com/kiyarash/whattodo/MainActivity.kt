package com.kiyarash.whattodo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.kiyarash.whattodo.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
	private lateinit var taskViewModel: TaskViewModel
	private lateinit var database: AppDatabase

	private var _binding: ActivityMainBinding? = null
	private val binding get() = _binding!!
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		getDatabaseInstance()
		_binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]
		binding.fabButton.setOnClickListener {
			val modalBottomSheet = AddTaskFragment()
			modalBottomSheet.show(supportFragmentManager, AddTaskFragment.TAG)
		}
		updateRecyclerView()
		taskViewModel.sharedData.observe(this) { data ->
			lifecycleScope.launch(Dispatchers.IO) {
				database.taskDao().insertTask(data)
				withContext(Dispatchers.Main) {
					updateRecyclerView()
				}
			}
		}
	}

	private fun updateRecyclerView() {
		lifecycleScope.launch(Dispatchers.IO) {
			val data = database.taskDao().getAll()
			withContext(Dispatchers.Main) {
				binding.recyclerView.adapter = TaskAdapter(data)
			}
		}
	}

	private fun getDatabaseInstance() {
		lifecycleScope.launch(Dispatchers.IO) {
			database = Room.databaseBuilder(
				applicationContext,
				AppDatabase::class.java,
				"tasks"
			).build()
		}
	}
}