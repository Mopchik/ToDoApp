package com.mopchik.planner.data_worker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.mopchik.planner.*
import com.mopchik.planner.to_do_item.ToDoItem


class ToDoItemAdapter(
    private val onClick: (item: ToDoItem) -> Unit,
    private val onCheck: (item: ToDoItem, isChecked:Boolean) -> Unit,
):
    ListAdapter<ToDoItem, ToDoItemViewHolder>(ToDoItemsDiffCallback()) {

    var showOnlyImportant:Boolean = false
    set(value){
        field = value
        submitDueToImportanceVisibility()
    }

    fun submitDueToImportanceVisibility(){
        if(showOnlyImportant){
            submitList(toDoItemsListInfo.notDoneList.toMutableList())
        } else{
            submitList(toDoItemsListInfo.toDoActualList.toMutableList())
        }
    }

    val toDoItemsListInfo = ToDoItemsListInfo()

    override fun getItemCount(): Int {
        return if(showOnlyImportant)
            toDoItemsListInfo.importantListIndexes.size
        else
            currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ToDoItemViewHolder(
            layoutInflater.inflate(R.layout.to_do_item, parent, false),
            onClick, onCheck
        )
    }

    override fun onBindViewHolder(holder: ToDoItemViewHolder, position: Int) {
        // var realPos = position
        val item = if(showOnlyImportant)
            toDoItemsListInfo.notDoneList[position]
        else currentList[position]
        holder.bind(item)
    }

}