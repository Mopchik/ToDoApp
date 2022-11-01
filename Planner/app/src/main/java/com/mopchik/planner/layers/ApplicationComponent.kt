package com.mopchik.planner.layers

import android.content.Context
import com.mopchik.planner.modules.AppModule
import dagger.BindsInstance
import dagger.Component

@Component(modules=[AppModule::class])
interface ApplicationComponent {
    val viewModelFactory: ViewModelFactory
    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}