package com.mopchik.planner.layers

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mopchik.planner.data_worker.ToDoItemViewModel
import com.mopchik.planner.data_worker.ToDoItemsRepository
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val rep: ToDoItemsRepository,
    private val prefs: SharedPreferences):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        ToDoItemViewModel::class.java ->
            ToDoItemViewModel(rep, prefs.getBoolean("isNight", false))
        else -> throw IllegalArgumentException("${modelClass.simpleName} cannot be provided.")
    } as T
}