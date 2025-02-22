package com.kiyarash.whattodo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.kiyarash.whattodo.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), TaskAdapter.OnButtonClickListener {
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var database: AppDatabase

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDatabaseInstance()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "What To Do"
        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        binding.fabButton.setOnClickListener {
            val modalBottomSheet = AddTaskFragment()
            modalBottomSheet.show(supportFragmentManager, AddTaskFragment.TAG)
        }
        updateRecyclerView()
        taskViewModel.sharedData.observe(this) { data ->
            if (data != null)
                lifecycleScope.launch(Dispatchers.IO) {
                    database.taskDao().insertTask(data)
                    withContext(Dispatchers.Main) {
                        taskViewModel.sharedData.value = null
                        updateRecyclerView()
                    }
                }
        }

    }

    private fun updateRecyclerView() {
        lifecycleScope.launch(Dispatchers.IO) {
            val data = database.taskDao().getAll()
            withContext(Dispatchers.Main) {
                binding.recyclerView.adapter = TaskAdapter(data, this@MainActivity)

                val itemTouchHelper = ItemTouchHelper(object :
                    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ) = false

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        //val position = viewHolder.adapterPosition
                        val adapter = binding.recyclerView.adapter as TaskAdapter
                        val position = viewHolder.adapterPosition
                        val task = adapter.getTask(position)

                        lifecycleScope.launch(Dispatchers.IO) {
                            database.taskDao().deleteTaskById(task.id)
                        }
                        adapter.removeTask(position)
                    }
                })
                itemTouchHelper.attachToRecyclerView(binding.recyclerView)
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

    override fun onButtonClick(holder: TaskAdapter.ViewHolder, position: Int) {
        if (holder.isDone.isChecked) {
            lifecycleScope.launch(Dispatchers.IO) {
                database.taskDao().deleteTaskById(database.taskDao().getAll()[position].id)
                updateRecyclerView()
            }
        }
    }
}