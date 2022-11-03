package com.mopchik.planner

import com.mopchik.planner.fragments.add_change.AddChangeFragmentViewComponent
import com.mopchik.planner.fragments.list.ListFragmentComponent
import dagger.Subcomponent

@Subcomponent
interface MainActivityComponent{
    fun listFragmentComponentFactory(): ListFragmentComponent.ListFragmentComponentFactory
    fun addChangeFragmentViewComponentFactory():
            AddChangeFragmentViewComponent.AddChangeFragmentViewComponentFactory
}