package com.mopchik.planner

import android.app.Activity
import android.content.Intent
import android.os.Bundle
<<<<<<< Updated upstream
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

import java.util.*

class MainActivity : AppCompatActivity() {

    val adapter = ToDoItemAdapter(ToDoItemsRepository.init())
=======
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mopchik.planner.fragments.list.ListFragment

class MainActivity : AppCompatActivity() {
    init{
        Log.i("KOSTIK", "Activity constructor started.")
    }
>>>>>>> Stashed changes
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter.notifyDataSetChanged()

        fab.setOnClickListener { view ->
            val intent = Intent(this, AddActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

<<<<<<< Updated upstream
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            val text = data!!.getStringExtra("description")
            val importanceId = data!!.getIntExtra("importance", -1)
            val importance = Importance.values().get(importanceId)
            adapter.addNewItem(
                ToDoItem(
                    "1", text, importance, false,
                    Calendar.getInstance()
                )
            )
            adapter.notifyDataSetChanged()
        }
    }

=======
>>>>>>> Stashed changes
}
