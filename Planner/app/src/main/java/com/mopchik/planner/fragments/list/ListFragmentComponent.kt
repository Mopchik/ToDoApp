package com.mopchik.planner.fragments.list

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.mopchik.planner.ContextClass
import com.mopchik.planner.ContextOwner
import com.mopchik.planner.ListFragmentScope
import com.mopchik.planner.data_worker.ToDoItemViewModel
import dagger.*


@Subcomponent(modules = [ListFragmentInitModule::class])
@ListFragmentScope
interface ListFragmentComponent{
    val adapterController: ListFragmentAdapterController
    val communicator: ListAddChangeFragmentsCommunicator
    @Subcomponent.Factory
    interface ListFragmentComponentFactory{
        fun create(@BindsInstance viewModel: ToDoItemViewModel,
                @BindsInstance lifecycleOwner: LifecycleOwner,
                @BindsInstance parentFragmentManager: FragmentManager,
                @BindsInstance @ContextClass(ContextOwner.LIST_FRAGMENT)
                   context: Context):ListFragmentComponent
    }
    fun inject(fragment: ListFragment)
    fun viewComponentFactory():
            ListFragmentViewComponent.ListFragmentViewComponentFactory
}




