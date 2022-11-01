package com.mopchik.planner.layers

import android.app.Application
import android.content.Context

/**
 * Custom Application class allows to hold reference to [applicationComponent]
 * as long as application lives.
 */
class App : Application() {
    lateinit var appComponent: ApplicationComponent
    // val applicationComponent by lazy { ApplicationComponent(applicationContext) }
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.factory().create(this)
    }
    companion object {
        /**
         * Shortcut to get [App] instance from any context, e.g. from Activity.
         */
        fun get(context: Context): App = context.applicationContext as App
    }
}
