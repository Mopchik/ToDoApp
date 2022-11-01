package com.mopchik.planner.fragments.list

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.mopchik.planner.data_worker.ToDoItemDao
import com.mopchik.planner.to_do_item.ToDoItem
import com.mopchik.planner.data_worker.adapter.ToDoItemAdapter
import com.mopchik.planner.data_worker.ToDoItemViewModel
import kotlinx.coroutines.launch

class ListFragmentAdapterController(private val startChangeFragment:(item: ToDoItem)->Unit,
                                    private val viewModel: ToDoItemViewModel,
                                    private val lifecycleOwner: LifecycleOwner,
                                    showOnlyImportant: Boolean) {
    val adapter: ToDoItemAdapter
    lateinit var changingItem: ToDoItem

    init {
        adapter = ToDoItemAdapter(
            onClick = { item ->
                changingItem = item
                startChangeFragment(item) },
            onCheck = {
                    item: ToDoItem, isChecked:Boolean ->
                val newItem = item.copy(isDone = isChecked)
                viewModel.onChangeToDoItem(item, newItem)
                // viewModel.viewModelScope.launch { toDoItemDao.editToDoItem(newItem) }
            }
            , showOnlyImportant)
    }

    fun observeData(component: ListFragmentComponent){
        viewModel.toDoItemsLiveData.observe(lifecycleOwner){ newToDoItems ->
            adapter.toDoItemsListInfo.toDoActualList = newToDoItems
            adapter.submitDueToImportanceVisibility()
            component.howManyDoneTextView.text = "Выполнено — " +
                    adapter.toDoItemsListInfo.completedSize
        }
        //viewModel.updateToDoItems()
    }
}