package com.kiyarash.whattodo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel() {
	val sharedData: MutableLiveData<Task> = MutableLiveData()
}