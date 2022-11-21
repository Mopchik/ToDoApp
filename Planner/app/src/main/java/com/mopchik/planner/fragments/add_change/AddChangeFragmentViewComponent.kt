package com.mopchik.planner.fragments.add_change

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import com.mopchik.planner.*
import dagger.BindsInstance
import dagger.Module
import dagger.Subcomponent

@Subcomponent(modules = [])
@AddChangeFragmentViewScope
interface AddChangeFragmentViewComponent {
    val binding: AddChangeFragmentBinding
    val controller: AddChangeFragmentController
    @Subcomponent.Factory
    interface AddChangeFragmentViewComponentFactory{
        fun create(@BindsInstance fragmentView: View,
                   @BindsInstance @ContextClass(ContextOwner.ADD_CHANGE_FRAGMENT)
                   context: Context,
                   @BindsInstance arguments: Bundle,
                   @BindsInstance saveOptions: (Bundle)->Unit,
                   @BindsInstance @ActionParameterType(TypeOfAction.GO_BACK)
                   goBack:()->Unit,
                   @BindsInstance @FragmentManagerClass(FragmentManagerOwner.ADD_CHANGE_FRAGMENT)
                   childFragmentManager: FragmentManager
                   ):AddChangeFragmentViewComponent
    }
    fun inject(fragment: AddChangeFragment)
}

