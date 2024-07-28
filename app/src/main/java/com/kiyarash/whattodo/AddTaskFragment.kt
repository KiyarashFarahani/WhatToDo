package com.kiyarash.whattodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kiyarash.whattodo.databinding.FragmentAddTaskBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddTaskFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.checkButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val database = Room.databaseBuilder(
                    requireContext(),
                    AppDatabase::class.java,
                    "tasks"
                ).build()

                database.taskDao().insertTask(
                    Task(
                        taskName = binding.textInput.text.toString(),
                        dueDate = null,
                        dueTime = null,
                        isDone = false
                    )
                )
                requireDialog().dismiss()
            }
        }
    }

    companion object {
        const val TAG = "BottomSheet"
    }
}