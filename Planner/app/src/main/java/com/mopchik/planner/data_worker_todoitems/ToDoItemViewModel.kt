package com.mopchik.planner.data_worker_todoitems

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ToDoItemViewModel(
    private val rep: ToDoItemsRepository
): ViewModel(), ToDoItemListUserChangeListener {
    val toDoItemsLiveData = rep.liveData

    override fun onAddToDoItem(item: ToDoItem) {
        viewModelScope.launch {
            rep.addNewItem(item)
        }
    }

    override fun onDeleteToDoItem(ind: Int) {
        viewModelScope.launch {
            rep.deleteItem(ind)
        }
    }

    override fun onChangeToDoItem(newItem: ToDoItem, pos: Int) {
        viewModelScope.launch {
            rep.changeItem(newItem, pos)
        }
    }

    fun updateToDoItems(){
        viewModelScope.launch {
            rep.updateToDoItems()
        }
    }
}