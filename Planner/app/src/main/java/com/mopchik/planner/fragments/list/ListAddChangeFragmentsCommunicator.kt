package com.mopchik.planner.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mopchik.planner.*
import com.mopchik.planner.to_do_item.ToDoItem
import com.mopchik.planner.data_worker.ToDoItemViewModel
import com.mopchik.planner.to_do_item.ToDoItemWorker
import com.mopchik.planner.fragments.add_change.AddChangeFragment
import java.util.*
import javax.inject.Inject

@ListFragmentScope
class ListAddChangeFragmentsCommunicator @Inject constructor(
    private val viewModel: ToDoItemViewModel,
    private val parentFragmentManager: FragmentManager
) {

    fun getResultOnListFragmentAfterAddChangeFragment(bundle: Bundle){
        val isDeleted = bundle.getInt(ACTION_KEY)
        when(Action.values()[isDeleted]) {
            Action.CREATE -> {
                val toDoItem = ToDoItem()
                val newToDoItem =
                    ToDoItemWorker.changeToDoItemFromBundle(
                        true, bundle, toDoItem)
                viewModel.onAddToDoItem(newToDoItem)
            }
            Action.CHANGE -> {
                val changedItem =
                    ToDoItemWorker.changeToDoItemFromBundle(
                        false, bundle, viewModel.changingItem)
                viewModel.onChangeToDoItem(changedItem)
            }
            Action.DELETE -> {
                viewModel.onDeleteToDoItem(viewModel.changingItem)
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
            item.deadline?.get(Calendar.DAY_OF_MONTH),
            item.deadline?.get(Calendar.MONTH),
            item.deadline?.get(Calendar.YEAR),
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