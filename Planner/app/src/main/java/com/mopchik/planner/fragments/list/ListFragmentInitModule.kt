package com.mopchik.planner.fragments.list

import android.content.Context
import android.content.SharedPreferences
import com.mopchik.planner.ContextClass
import com.mopchik.planner.ContextOwner
import com.mopchik.planner.ListFragmentScope
import com.mopchik.planner.data_worker.ToDoItemViewModel
import com.mopchik.planner.data_worker.adapter.ToDoItemAdapter
import com.mopchik.planner.to_do_item.ToDoItem
import dagger.Module
import dagger.Provides

@Module
object ListFragmentInitModule {
    @Provides
    @ListFragmentScope
    fun provideAdapter(prefs: SharedPreferences,
                       viewModel: ToDoItemViewModel,
                       communicator: ListAddChangeFragmentsCommunicator, ): ToDoItemAdapter {
        val showOnlyImportant = prefs.getBoolean("visibility", false)
        return ToDoItemAdapter(
            onClick = { item ->
                viewModel.changingItem = item
                communicator.startChangeFragment(item) },
            onCheck = {
                    item: ToDoItem, isChecked:Boolean ->
                val newItem = item.copy(isDone = isChecked)
                viewModel.onChangeToDoItem(newItem)
            },
            showOnlyImportant
        )
    }
}