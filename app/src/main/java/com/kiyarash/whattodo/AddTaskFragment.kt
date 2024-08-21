package com.kiyarash.whattodo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kiyarash.whattodo.databinding.FragmentAddTaskBinding
import java.util.Calendar
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
		binding.dateButton.setOnClickListener {
			openDatePicker()
		}
		binding.timeButton.setOnClickListener {
			openTimePicker()
		}
	}

	private fun openTimePicker() {
		// Get current time
		val calendar = Calendar.getInstance()
		val hour = calendar.get(Calendar.HOUR_OF_DAY)
		val minute = calendar.get(Calendar.MINUTE)

		// Create TimePickerDialog
		val timePickerDialog = TimePickerDialog(
			requireContext(),
			{ _, selectedHour, selectedMinute ->
				// Handle time selection
				binding.timeButton.text =
					String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
				dueTime = calendar.timeInMillis
			},
			hour, minute, true
		)

		// Show TimePickerDialog
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
				/*Toast.makeText(
					requireContext(), "Selected Date: $selectedDate", Toast.LENGTH_SHORT
				).show()*/
				dueDate = calendar.timeInMillis
				binding.dateButton.text = selectedDate
			},
			year, month, day
		)
		datePickerDialog.show()
	}

	companion object {
		const val TAG = "BottomSheet"
	}
}