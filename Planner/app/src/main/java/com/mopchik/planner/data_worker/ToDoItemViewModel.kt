package com.mopchik.planner.data_worker

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mopchik.planner.to_do_item.ToDoItem
import kotlinx.coroutines.launch

class ToDoItemViewModel(
    private val rep: ToDoItemsRepository,
    isNightNow: Boolean
): ViewModel() {
    var isNight: Boolean = isNightNow
    val toDoItemsLiveData = rep.liveData
    lateinit var changingItem: ToDoItem
    init{
        Log.i("KOSTIK", "ViewModel created")
    }
    fun onAddToDoItem(item: ToDoItem) {
        rep.addNewItem(item)
    }

    fun onDeleteToDoItem(item: ToDoItem) {
        rep.deleteItem(item)
    }

    fun onChangeToDoItem(newItem: ToDoItem) {
        rep.changeItem(newItem)
    }


}