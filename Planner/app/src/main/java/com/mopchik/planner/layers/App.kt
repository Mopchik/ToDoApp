package com.mopchik.planner.layers

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.mopchik.planner.notifications_worker.NotificationSender
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Custom Application class allows to hold reference to [applicationComponent]
 * as long as application lives.
 */
class App : Application() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var prefs: SharedPreferences
    @Inject
    lateinit var notificationSender: NotificationSender
    val applicationDefaultScope = CoroutineScope(Dispatchers.Default)
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        Log.i("KOSTIK", "Application created")
        applicationComponent = DaggerApplicationComponent.factory().create(this)
        applicationComponent.inject(this)
    }

    companion object {
        /**
         * Shortcut to get [App] instance from any context, e.g. from Activity.
         */
        fun get(context: Context): App = context.applicationContext as App
    }
}