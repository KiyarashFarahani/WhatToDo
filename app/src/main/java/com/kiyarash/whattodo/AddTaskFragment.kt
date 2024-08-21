package com.kiyarash.whattodo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kiyarash.whattodo.databinding.FragmentAddTaskBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTaskFragment : BottomSheetDialogFragment() {
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
					dueDate = null,
					dueTime = null,
					isDone = false
				)
				requireDialog().dismiss()
		}
	}

	companion object {
		const val TAG = "BottomSheet"
	}
}