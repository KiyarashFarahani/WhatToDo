package com.kiyarash.whattodo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kiyarash.whattodo.databinding.FragmentAddTaskBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddTaskFragment : BottomSheetDialogFragment() {
	private var dueDate: Long? = null
	private var dueTime: Long? = null
	private var _binding: FragmentAddTaskBinding? = null
	private val binding get() = _binding!!
	private lateinit var viewModel: TaskViewModel
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentAddTaskBinding.inflate(inflater, container, false)
		viewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.checkButton.setOnClickListener {
			viewModel.sharedData.value = Task(
				taskName = binding.textInput.text.toString(),
				dueDate = dueDate,
				dueTime = dueTime,
				isDone = false
			)
			requireDialog().dismiss()
		}

		viewModel.sharedData.observe(viewLifecycleOwner) { task ->
			if (task != null) {
				binding.textInput.setText(task.taskName)

				if(task.dueTime != null){
					binding.timeButton.text = SimpleDateFormat(
						"hh:mm a",
						Locale.getDefault()
					).format(Date(task.dueTime))
				}

				if(task.dueDate != null) {
					val dateFormat: String?
					val oneWeekInMillis = (7 * 24 * 60 * 60 * 1000L)
					val duration = task.dueDate - System.currentTimeMillis()
					if (duration in 0..oneWeekInMillis) dateFormat = "E"
					else dateFormat = "E, MMM dd"
					binding.dateButton.text = SimpleDateFormat(
						dateFormat,
						Locale.getDefault()
					).format(Date(task.dueDate))
				}
			}
		}
		binding.dateButton.setOnClickListener {
			openDatePicker()
		}
		binding.timeButton.setOnClickListener {
			openTimePicker()
		}
	}

	private fun openTimePicker() {
		val calendar = Calendar.getInstance()
		val hour = calendar.get(Calendar.HOUR_OF_DAY)
		val minute = calendar.get(Calendar.MINUTE)

		val timePickerDialog = TimePickerDialog(
			requireContext(),
			{ _, selectedHour, selectedMinute ->
				calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
				calendar.set(Calendar.MINUTE, selectedMinute)
				val selectedTime =
					String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
				binding.timeButton.text = selectedTime
				dueTime = calendar.timeInMillis
			},
			hour, minute, true
		)
		timePickerDialog.show()
	}

	private fun openDatePicker() {
		val calendar = Calendar.getInstance()
		val year = calendar.get(Calendar.YEAR)
		val month = calendar.get(Calendar.MONTH)
		val day = calendar.get(Calendar.DAY_OF_MONTH)

		val datePickerDialog = DatePickerDialog(
			requireContext(),
			{ _, selectedYear, selectedMonth, selectedDay ->
				val selectedDate = "${selectedDay}/${selectedMonth + 1}/$selectedYear"
				calendar.set(Calendar.YEAR, selectedYear)
				calendar.set(Calendar.MONTH, selectedMonth)
				calendar.set(Calendar.DAY_OF_MONTH, selectedDay)
				binding.dateButton.text = selectedDate
				dueDate = calendar.timeInMillis
			},
			year, month, day
		)
		datePickerDialog.show()
	}
	override fun onDestroyView() {
		super.onDestroyView()
		viewModel.sharedData.value = null
		_binding = null
	}
	companion object {
		const val TAG = "BottomSheet"
	}
}