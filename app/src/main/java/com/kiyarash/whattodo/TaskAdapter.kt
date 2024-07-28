package com.kiyarash.whattodo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val data: List<Task>): RecyclerView.Adapter<TaskAdapter.ViewHolder>(){
	class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
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
		val	item = data[position]
		holder.taskName.text = item.taskName
		/*holder.due.text = buildString {
			append(item.dueDate.toString())
			append(" ")
			append(item.dueTime.toString())
		}*/
		holder.isDone.isChecked = item.isDone
	}
}