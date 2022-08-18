package com.mopchik.planner.data_worker_todoitems

interface ToDoItemListUserChangeListener{
    fun onAddToDoItem(item: ToDoItem)
    fun onDeleteToDoItem(ind:Int)
    fun onChangeToDoItem(newItem: ToDoItem, pos:Int)
}