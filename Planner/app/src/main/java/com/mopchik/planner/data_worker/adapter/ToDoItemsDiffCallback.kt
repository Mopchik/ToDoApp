package com.mopchik.planner.data_worker.adapter

import androidx.recyclerview.widget.DiffUtil
import com.mopchik.planner.to_do_item.ToDoItem

class ToDoItemsDiffCallback: DiffUtil.ItemCallback<ToDoItem>(){
    override fun areItemsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean {
        return oldItem == newItem
    }

}