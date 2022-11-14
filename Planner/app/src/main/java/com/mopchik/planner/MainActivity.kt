package com.mopchik.planner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.mopchik.planner.fragments.list.ListFragment
import com.mopchik.planner.layers.App

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
            val listFragment = ListFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, listFragment)
                .commit()
        }
    }
}
