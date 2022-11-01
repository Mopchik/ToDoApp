package com.mopchik.planner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mopchik.planner.fragments.list.ListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
