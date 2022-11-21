package com.mopchik.planner.layers

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mopchik.planner.AppScope
import com.mopchik.planner.data_worker.ToDoItemViewModel
import com.mopchik.planner.data_worker.ToDoItemsRepository
import com.mopchik.planner.notifications_worker.NotificationSender
import javax.inject.Inject

@AppScope
class ViewModelFactory @Inject constructor(
    private val rep: ToDoItemsRepository,
    private val prefs: SharedPreferences,
    private val notificationSender: NotificationSender):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        ToDoItemViewModel::class.java ->
            ToDoItemViewModel(rep, notificationSender, prefs.getBoolean("isNight", false))
        else -> throw IllegalArgumentException("${modelClass.simpleName} cannot be provided.")
    } as T
}