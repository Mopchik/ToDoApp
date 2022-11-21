package com.mopchik.planner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.mopchik.planner.fragments.list.ListFragment
import com.mopchik.planner.layers.App
import com.mopchik.planner.notifications_worker.NotificationSender
import java.util.*

class MainActivity : AppCompatActivity() {

    private val app by lazy{ App.get(this) }
    val component: MainActivityComponent by lazy {
        app.applicationComponent.mainActivityComponent()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null) {
            // val notificationSender = NotificationSender(this)
            // val nowPlusTen = Calendar.getInstance()
            // nowPlusTen.set(Calendar.SECOND, nowPlusTen.get(Calendar.SECOND) + 2)
            // notificationSender.send(-1, nowPlusTen, "It really works!")

            val listFragment = ListFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, listFragment)
                .commit()
        }
    }
}
