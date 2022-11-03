package com.mopchik.planner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mopchik.planner.fragments.list.ListFragment
import com.mopchik.planner.layers.App

class MainActivity : AppCompatActivity() {

    lateinit var component: MainActivityComponent
    private val app
        get() = App.get(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = app.applicationComponent.mainActivityComponent()
        setContentView(R.layout.activity_main)
        if(supportFragmentManager.fragments.isEmpty()) {
            val listFragment = ListFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, listFragment)
                .commit()
        }
    }
}
