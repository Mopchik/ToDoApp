package com.mopchik.planner

import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import android.graphics.Paint
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.widget.*


class ToDoItemAdapter(val arr:ToDoItemsRepository): RecyclerView.Adapter<ToDoItemViewHolder>(){
    override fun getItemCount(): Int {
        return arr.getSize()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ToDoItemViewHolder(
            layoutInflater.inflate(R.layout.to_do_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ToDoItemViewHolder, position: Int) {
        val item = arr.getItem(position)
        holder.itemView.apply{
            val textView = findViewById<TextView>(R.id.toDoItem_text)
            val checkBox = findViewById<CheckBox>(R.id.toDoItem_checkBox)
            val layout = findViewById<LinearLayout>(R.id.layout_item)
            textView.text = item.text
            if(item.isDone){
                textView.paintFlags = (textView.paintFlags
                        or Paint.STRIKE_THRU_TEXT_FLAG)
                checkBox.setBackgroundColor(Color.WHITE)
                checkBox.isChecked = true
                checkBox.isClickable = false
            }
            else if(item.importance == Importance.HIGH){
                checkBox.setBackgroundColor(Color.RED)
            }
            checkBox.setOnCheckedChangeListener{view, isChecked ->
                if(isChecked){
                    textView.paintFlags = (textView.paintFlags
                            or Paint.STRIKE_THRU_TEXT_FLAG)
                    item.isDone = true
                    checkBox.setBackgroundColor(Color.WHITE)
                    checkBox.isClickable = false
                } else{
                    //  textView.setPaintFlags(textView.getPaintFlags()
                    //          and Paint.STRIKE_THRU_TEXT_FLAG.inv())

                }
            }
            // layout.setOnClickListener{view ->
            //     val intent = Intent(view.context, AddActivity::class.java)
            //     startActivityForResult(intent, 1)
            // }
        }
    }

    fun addNewItem(item:ToDoItem){
        arr.addNewItem(item)
    }
}