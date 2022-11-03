package com.mopchik.planner.to_do_item

import android.os.Bundle
import java.lang.NullPointerException
import java.util.*

class ToDoItemWorker {
    companion object {
        fun changeToDoItemFromBundle(isCreating: Boolean,
                                     bundle: Bundle,
                                     oldItem: ToDoItem): ToDoItem {
            val item = oldItem.copy()
            item.text = bundle.getString("description") ?: throw NullPointerException()
            val importanceId = bundle.getInt("importance", -1)
            item.importance = Importance.values()[importanceId]
            val day = bundle.getInt("day", -1)
            val month = bundle.getInt("month", -1)
            val year = bundle.getInt("year", -1)
            if (day != -1 && month != -1 && year != -1) {
                val calendar = Calendar.getInstance()
                calendar.set(year, month, day)
                item.deadline = calendar
            } else {
                item.deadline = null
            }
            if(!isCreating)
                item.changeDate = Calendar.getInstance()
            return item
        }
    }
}