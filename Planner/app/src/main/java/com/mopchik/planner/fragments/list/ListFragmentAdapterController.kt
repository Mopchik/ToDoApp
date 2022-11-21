package com.mopchik.planner.fragments.list

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.mopchik.planner.ListFragmentScope
import com.mopchik.planner.to_do_item.ToDoItem
import com.mopchik.planner.data_worker.adapter.ToDoItemAdapter
import com.mopchik.planner.data_worker.ToDoItemViewModel
import com.mopchik.planner.layers.App
import com.mopchik.planner.notifications_worker.DiffUtilForNotification
import com.mopchik.planner.notifications_worker.NotificationSender
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@ListFragmentScope
class ListFragmentAdapterController @Inject constructor(
    val adapter: ToDoItemAdapter,
    val notificationSender: NotificationSender) {

    fun observeData(liveData: LiveData<List<ToDoItem>>,
                    binding: ListFragmentBinding,
                    lifecycleOwner: LifecycleOwner){
        liveData.observe(lifecycleOwner){ newToDoItems ->
            adapter.toDoItemsListInfo.toDoActualList = newToDoItems
            adapter.submitDueToImportanceVisibility()
            binding.howManyDoneTextView.text = "Выполнено — " +
                    adapter.toDoItemsListInfo.completedSize
        }
    }
}