package com.mopchik.planner.layers

import android.content.Context
import android.content.SharedPreferences
import com.mopchik.planner.AppScope
import com.mopchik.planner.ContextClass
import com.mopchik.planner.ContextOwner
import com.mopchik.planner.MainActivityComponent
import com.mopchik.planner.notifications_worker.NotificationSender
import dagger.BindsInstance
import dagger.Component


@Component(modules = [ApplicationModule::class])
@AppScope
interface ApplicationComponent {
    val viewModelFactory: ViewModelFactory
    val prefs: SharedPreferences
    val notificationSender: NotificationSender
    @Component.Factory
    interface ApplicationComponentFactory{
        fun create(@BindsInstance
                   @ContextClass(ContextOwner.APP)
                   applicationContext: Context): ApplicationComponent
    }
    fun inject(app: App)
    fun mainActivityComponent(): MainActivityComponent
}