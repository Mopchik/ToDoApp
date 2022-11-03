package com.mopchik.planner.fragments.list

import android.view.View
import com.mopchik.planner.ActionParameterType
import com.mopchik.planner.ListFragmentViewScope
import com.mopchik.planner.TypeOfAction
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [])
@ListFragmentViewScope
interface ListFragmentViewComponent {
    val controller: ListFragmentController
    val binding: ListFragmentBinding
    @Subcomponent.Factory
    interface ListFragmentViewComponentFactory{
        fun create(@BindsInstance fragmentView: View,
                    @BindsInstance
                    @ActionParameterType(TypeOfAction.START_ADD_FRAGMENT)
                    startAddFragment: () -> Unit):ListFragmentViewComponent
    }
    fun inject(fragment: ListFragment)
}
