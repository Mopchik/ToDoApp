package com.mopchik.planner.fragments.list

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mopchik.planner.R
import com.mopchik.planner.data_worker.adapter.ToDoItemAdapter

class ListFragmentController(private val component: ListFragmentComponent,
                             private val adapter: ToDoItemAdapter,
                             private val startAddFragment: ()->Unit,
                             private val layoutManagerContext: Context) {
    fun setUpViews(){
        component.recycler.adapter = adapter
        component.recycler.layoutManager =
            LinearLayoutManager(layoutManagerContext, RecyclerView.VERTICAL, false)
        component.howManyDoneTextView.text =
            "Выполнено — " + adapter.toDoItemsListInfo.completedSize
        setVisibility()
        component.visibilityImageView.setOnClickListener{
            onVisibilityChanged()
        }
        component.fab.setOnClickListener { startAddFragment() }
    }

    private fun onVisibilityChanged(){
        adapter.showOnlyImportant = !adapter.showOnlyImportant
        setVisibility()
    }

    fun setVisibility(visibility: Boolean){
        adapter.showOnlyImportant = visibility
        setVisibility()
    }

    private fun setVisibility(){
        if(adapter.showOnlyImportant){
            component.visibilityImageView.setBackgroundResource(R.drawable.ic_baseline_visibility_off_24)
        } else{
            component.visibilityImageView.setBackgroundResource(R.drawable.ic_baseline_visibility_24)
        }
    }
}