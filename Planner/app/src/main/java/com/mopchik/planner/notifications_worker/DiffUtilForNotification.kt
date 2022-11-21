package com.mopchik.planner.notifications_worker

import com.mopchik.planner.data_worker.Converters
import com.mopchik.planner.to_do_item.ToDoItem
import java.util.*

class DiffUtilForNotification(private val oldList: List<ToDoItem>,
                              private val newList: List<ToDoItem>) {
    fun getDeletedList(): List<ToDoItem>{
            return oldList.filter { !contains(it) }
        }

    fun getChangedDateList():List<ToDoItem>{
        return newList.filter { dateChanged(it) }
    }

    private fun dateChanged(item:ToDoItem):Boolean{
        return oldList.all{ it.id != item.id ||
                (it.id == item.id && !calendarEquals(it.deadline, item.deadline))}
    }

    private fun calendarEquals(f: Calendar?, s:Calendar?):Boolean{
        return f?.get(Calendar.YEAR) == s?.get(Calendar.YEAR) &&
                f?.get(Calendar.MONTH) == s?.get(Calendar.MONTH) &&
                f?.get(Calendar.DAY_OF_MONTH) == s?.get(Calendar.DAY_OF_MONTH)
    }
    private fun contains(item:ToDoItem):Boolean{
        return newList.any { it.id == item.id && !it.isDone}
    }
}