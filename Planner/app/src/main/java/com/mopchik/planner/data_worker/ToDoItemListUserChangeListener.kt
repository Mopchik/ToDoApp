package com.mopchik.planner.data_worker

interface ToDoItemListUserChange<T>{
    fun onAddToDoItem(item: T)
    fun onDeleteToDoItem(item: T)
    fun onChangeToDoItem(oldItem:T, newItem: T)
}