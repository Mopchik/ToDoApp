package com.mopchik.planner.fragments.list

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mopchik.planner.*
import com.mopchik.planner.data_worker.ToDoItemViewModel
import com.mopchik.planner.data_worker.adapter.ToDoItemAdapter
import javax.inject.Inject

@ListFragmentViewScope
class ListFragmentController @Inject constructor(private val binding: ListFragmentBinding,
                                                 private val adapter: ToDoItemAdapter,
                                                 @ContextClass(ContextOwner.LIST_FRAGMENT)
                                                 private val layoutManagerContext: Context,
                                                 @ActionParameterType(TypeOfAction.START_ADD_FRAGMENT)
                                                 private val startAddFragment: () -> Unit,
                                                 private val viewModel: ToDoItemViewModel) {

    fun setUpViews(){
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager =
            LinearLayoutManager(layoutManagerContext, RecyclerView.VERTICAL, false)
        binding.howManyDoneTextView.text =
            "Выполнено — " + adapter.toDoItemsListInfo.completedSize
        setVisibility()
        binding.visibilityImageView.setOnClickListener{
            onVisibilityChanged()
        }
        setNight()
        binding.nightImageView.setOnClickListener {
            onNightChange()
        }
        binding.fab.setOnClickListener { startAddFragment() }
    }

    private fun onVisibilityChanged(){
        adapter.showOnlyImportant = !adapter.showOnlyImportant
        setVisibility()
    }

    private fun setVisibility(){
        if(adapter.showOnlyImportant){
            binding.visibilityImageView.setBackgroundResource(R.drawable.ic_baseline_visibility_off_24)
        } else{
            binding.visibilityImageView.setBackgroundResource(R.drawable.ic_baseline_visibility_24)
        }
    }

    private fun setNight(){
        if(!viewModel.isNight){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.nightImageView.setBackgroundResource(R.drawable.ic_baseline_wb_sunny_24)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.nightImageView.setBackgroundResource(R.drawable.ic_baseline_nights_stay_24)
        }
    }
    private fun onNightChange(){
        viewModel.isNight = !viewModel.isNight
        setNight()
    }
}