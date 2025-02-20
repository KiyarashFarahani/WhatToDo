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
	private val data: MutableList<Task>, private val buttonClickListener: OnButtonClickListener
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
	private val oneWeekInMillis = (7 * 24 * 60 * 60 * 1000L)

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
			var text = ""

			var dateFormat: String? = null
			if (item.dueDate != null) {
				val duration = item.dueDate - (System.currentTimeMillis())
				if (duration in 0..oneWeekInMillis)
					dateFormat = "E"
				else
					dateFormat = "E, MMM dd"
				text += SimpleDateFormat(
					dateFormat,
					Locale.getDefault()
				).format(Date(item.dueDate))
			}

			if (item.dueTime != null) {
				if (text != "")
					text += " "
				text += SimpleDateFormat(
					"hh:mm a",
					Locale.getDefault()
				).format(Date(item.dueTime))
			}
			holder.due.text = buildString {
				append(text)
			}
		} else {
			holder.due.visibility = View.GONE
		}
		holder.isDone.isChecked = item.isDone
		holder.isDone.setOnClickListener {
			buttonClickListener.onButtonClick(holder, position)
			notifyItemRemoved(position)
		}
	}

	fun getTask(position: Int): Task = data[position]

	fun removeTask(position: Int) {
		data.removeAt(position)
		notifyItemRemoved(position)
	}

}