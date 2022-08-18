package com.mopchik.planner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mopchik.planner.data_worker_todoitems.ToDoItemViewModel
import com.mopchik.planner.data_worker_todoitems.ToDoItemsRepository

class ViewModelFactory(private val rep: ToDoItemsRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        ToDoItemViewModel::class.java -> ToDoItemViewModel(rep)
        else -> throw IllegalArgumentException("${modelClass.simpleName} cannot be provided.")
    } as T
}