package com.mopchik.planner.data_worker

import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.mopchik.planner.notifications_worker.DiffUtilForNotification
import com.mopchik.planner.notifications_worker.NotificationSender
import com.mopchik.planner.to_do_item.ToDoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class ToDoItemViewModel(
    private val rep: ToDoItemsRepository,
    private val notificationSender: NotificationSender,
    isNightNow: Boolean
): ViewModel() {
    var isNight: Boolean = isNightNow
    val toDoItemsLiveData = rep.liveData
    private var toDoItemsList:List<ToDoItem> = ArrayList()
    lateinit var changingItem: ToDoItem
    init{
        rep.liveData.observeForever(getNotificationObserver())
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

    private fun getNotificationObserver():Observer<List<ToDoItem>>{
        return Observer<List<ToDoItem>> { newToDoItems ->
            viewModelScope.launch(Dispatchers.Default) {
                val oldList = toDoItemsList
                val diff = DiffUtilForNotification(oldList = oldList, newList = newToDoItems)
                diff.getDeletedList().forEach {
                    notificationSender.cancelNotification(it.id)
                }
                diff.getChangedDateList().forEach {
                    if(it.deadline != null && !it.isDone){
                        val c = it.deadline!!.clone() as Calendar
                        c.add(Calendar.DAY_OF_MONTH, -1)
                        if (c.timeInMillis >= Calendar.getInstance().timeInMillis) {
                            notificationSender.send(it.id, c, it.text)
                        }
                    }
                }
                toDoItemsList = newToDoItems
            }
        }
    }

}