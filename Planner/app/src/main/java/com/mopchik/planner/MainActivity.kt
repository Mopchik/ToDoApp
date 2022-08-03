package com.mopchik.planner

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.NullPointerException

import java.util.*

class MainActivity : AppCompatActivity(), OnItemClickedListener {

    private val adapter = ToDoItemAdapter(ToDoItemsRepository.INSTANCE, this)
    private var indOfChangingItem: Int = -1

    private var saveResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK){
            val toDoItem = ToDoItem((adapter.itemCount + 1).toString())
            setToDoItem(result.data!!, toDoItem)
            adapter.addNewItem(toDoItem)
            adapter.notifyItemInserted(adapter.itemCount - 1)
        }
    }

    private val changeResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK){
            val isDeleted = result.data!!.getBooleanExtra("deleted", false)
            if(!isDeleted) {
                val item = adapter.getItem(indOfChangingItem)
                setToDoItem(result.data!!, item)
                adapter.notifyItemChanged(indOfChangingItem)
            } else {
                adapter.deleteItem(indOfChangingItem)
                adapter.notifyItemRemoved(indOfChangingItem)
                adapter.notifyItemRangeChanged(indOfChangingItem, adapter.itemCount)
            }
        }
    }

    private fun setToDoItem(intent: Intent, item: ToDoItem){
        item.text = intent.getStringExtra("description") ?: throw NullPointerException()
        val importanceId = intent.getIntExtra("importance", -1)
        item.importance = Importance.values()[importanceId]
        val day = intent.getIntExtra("day", -1)
        val month = intent.getIntExtra("month", -1)
        val year = intent.getIntExtra("year", -1)
        if(day != -1 && month != -1 && year != -1){
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            item.deadline = calendar
        } else {
            item.deadline = null
        }
        item.changeDate = Calendar.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter.notifyDataSetChanged()

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
            val intent = Intent(this, AddActivity::class.java)
            saveResultLauncher.launch(intent)
        }

    }

    override fun onItemClicked(itemInd:Int) {
        val intent = Intent(this, AddActivity::class.java)
        val item = adapter.getItem(itemInd)
        intent.putExtra("description", item.text)
        intent.putExtra("importance", item.importance.ordinal)
        intent.putExtra("day", item.deadline?.get(Calendar.DAY_OF_MONTH))
        intent.putExtra("month", item.deadline?.get(Calendar.MONTH))
        intent.putExtra("year", item.deadline?.get(Calendar.YEAR))
        indOfChangingItem = itemInd
        changeResultLauncher.launch(intent)
    }

}
