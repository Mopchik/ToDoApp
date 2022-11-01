package com.mopchik.planner.layers

import android.content.Context
import dagger.BindsInstance
import dagger.Component

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    val viewModelFactory: ViewModelFactory
    @Component.Factory
    interface ApplicationComponentFactory{
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}