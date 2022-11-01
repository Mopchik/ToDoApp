package com.mopchik.planner.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mopchik.planner.ACTION_KEY
import com.mopchik.planner.Action
import com.mopchik.planner.CREATING_KEY
import com.mopchik.planner.R
import com.mopchik.planner.to_do_item.ToDoItem
import com.mopchik.planner.data_worker.ToDoItemViewModel
import com.mopchik.planner.to_do_item.ToDoItemWorker
import com.mopchik.planner.fragments.add_change.AddChangeFragment
import java.util.*

class ListAddChangeFragmentsCommunicator(
    private val viewModel: ToDoItemViewModel,
    private val adapterController: ListFragmentAdapterController,
    private val parentFragmentManager: FragmentManager
) {

    fun getResultOnListFragmentAfterAddChangeFragment(bundle: Bundle){
        val isDeleted = bundle.getInt(ACTION_KEY)
        when(Action.values()[isDeleted]) {
            Action.CREATE -> {
                val toDoItem = ToDoItem()
                val newToDoItem = ToDoItemWorker.changeToDoItemFromBundle(bundle, toDoItem)
                viewModel.onAddToDoItem(newToDoItem)
            }
            Action.CHANGE -> {
                val changedItem =
                    ToDoItemWorker.changeToDoItemFromBundle(bundle, adapterController.changingItem)
                viewModel.onChangeToDoItem(changedItem)
            }
            Action.DELETE -> {
                viewModel.onDeleteToDoItem(adapterController.changingItem)
            }
        }
    }

    fun startAddFragment(){
        val addFragment = AddChangeFragment().apply {
            arguments = Bundle().apply {
                putBoolean(CREATING_KEY, true)
            }
        }
        startFragmentFromListFragment(addFragment)
    }

    fun startChangeFragment(item: ToDoItem){
        val changeFragment = AddChangeFragment.newInstance(
            item.text,
            item.importance.ordinal, false,
            item.changeDate?.get(Calendar.DAY_OF_MONTH),
            item.changeDate?.get(Calendar.MONTH),
            item.changeDate?.get(Calendar.YEAR),
        )
        startFragmentFromListFragment(changeFragment)
    }

    private fun startFragmentFromListFragment(fragment: Fragment){
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}