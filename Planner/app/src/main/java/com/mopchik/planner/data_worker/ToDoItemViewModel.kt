package com.mopchik.planner.data_worker

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mopchik.planner.to_do_item.ToDoItem
import kotlinx.coroutines.launch

class ToDoItemViewModel(
    private val rep: ToDoItemsRepository
): ViewModel() {
    init{
        Log.i("KOSTIK", "ViewModel constructor started.")
    }
    // val toDoItemsLiveData = rep.liveData
    val toDoItemsLiveData = rep.dbLiveData

    fun onAddToDoItem(item: ToDoItem) {
        // viewModelScope.launch {
            rep.addNewItem(item)
        // }
    }

    fun onDeleteToDoItem(item: ToDoItem) {
        // viewModelScope.launch {
            rep.deleteItem(item)
        // }
    }

    fun onChangeToDoItem(oldItem: ToDoItem, newItem: ToDoItem) {
        // viewModelScope.launch {
            rep.changeItem(oldItem, newItem)
        // }
    }

    // fun updateToDoItems(){
    //     viewModelScope.launch {
    //         rep.updateToDoItems()
    //     }
    // }

    // fun setList(list: List<ToDoItem>){
    //     viewModelScope.launch {
    //         rep.setList(list)
    //     }
    // }

}