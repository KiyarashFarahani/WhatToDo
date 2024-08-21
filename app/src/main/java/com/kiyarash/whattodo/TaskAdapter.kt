package com.kiyarash.whattodo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TaskAdapter(
	private val data: List<Task>, private val buttonClickListener: OnButtonClickListener
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
	interface OnButtonClickListener {
		fun onButtonClick(holder: ViewHolder, position: Int)
	}

	class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
		val taskName: TextView = view.findViewById(R.id.taskNameTextView)
		val due: TextView = view.findViewById(R.id.dueTextView)
		val isDone: CheckBox = view.findViewById(R.id.isDoneCheckBox)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val layout = LayoutInflater
			.from(parent.context)
			.inflate(R.layout.item_view, parent, false)

		return ViewHolder(layout)
	}

	override fun getItemCount(): Int {
		return data.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = data[position]
		holder.taskName.text = item.taskName

		if (item.dueDate != null || item.dueTime != null) {
			holder.due.text = buildString {
				append("Until ")
				//append(Date(item.dueDate!!))
				append(
					SimpleDateFormat(
						"E, MMM dd",
						Locale.getDefault()
					).format(Date(item.dueDate!!))
				)
				append(" ")
				//append(Time(item.dueTime!!))
				append(
					SimpleDateFormat(
						"hh:mm",
						Locale.getDefault()
					).format(Date(item.dueTime!!))
				)
			}
		}
		holder.isDone.isChecked = item.isDone
		holder.isDone.setOnClickListener {
			buttonClickListener.onButtonClick(holder, position)
		}
	}
}