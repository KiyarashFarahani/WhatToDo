package com.kiyarash.whattodo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kiyarash.whattodo.databinding.ActivityMainBinding
import java.sql.Time
import java.util.Date

class MainActivity : AppCompatActivity() {
	private var _binding: ActivityMainBinding? = null
	private val binding get() = _binding!!
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		_binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		binding.recyclerView.adapter = TaskAdapter(
			listOf(
				Task(0, "Title", Date(1234), Time(123), false)
			)
		)


		binding.fabButton.setOnClickListener {
			val modalBottomSheet = AddTaskFragment()
			modalBottomSheet.show(supportFragmentManager, AddTaskFragment.TAG)
		}
	}
}