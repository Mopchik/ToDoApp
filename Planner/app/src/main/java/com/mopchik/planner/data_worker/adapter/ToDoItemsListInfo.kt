package com.mopchik.planner.data_worker.adapter

import com.mopchik.planner.to_do_item.ToDoItem

class ToDoItemsListInfo {
    var toDoActualList:List<ToDoItem> = ArrayList()
    set(value){
        field = value
        completedSize = calculateCompletedSize()
        importantListIndexes = calculateNotDoneListIndexes()
        notDoneList = calculateNotDoneList()
    }

    var completedSize:Int = 0
    private set

    private fun calculateCompletedSize():Int{
        var completedSize = 0
        for(item in toDoActualList){
            if(item.isDone){
                completedSize++
            }
        }
        return completedSize
    }

    fun getItem(ind:Int): ToDoItem {
        return toDoActualList[ind]
    }

    var importantListIndexes:List<Int> = ArrayList()
    private set
    var notDoneList:List<ToDoItem> = ArrayList()
    private set

    private fun calculateNotDoneListIndexes():List<Int> {
        // val importantList = ArrayList<Int>()
        // for (i in toDoActualList.indices) {
        //     if (!toDoActualList[i].isDone) {
        //         importantList.add(i)
        //     }
        // }
        return calculateNotDoneList().indices.toList()
    }
    private fun calculateNotDoneList():List<ToDoItem> {
        // val importantList = ArrayList<ToDoItem>()
        // for (i in toDoActualList) {
        //     if (!i.isDone) {
        //         importantList.add(i)
        //     }
        // }
        return toDoActualList.filterNot { it.isDone }
    }

}