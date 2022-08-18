package com.mopchik.planner

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import android.graphics.Paint
import android.widget.*


class ToDoItemAdapter(private val arr:ToDoItemsRepository, private val onClick:OnItemClickedListener):
    RecyclerView.Adapter<ToDoItemViewHolder>(){
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
                textView.setTextColor(Color.GRAY)
                textView.paintFlags = (textView.paintFlags
                        or Paint.STRIKE_THRU_TEXT_FLAG)
                checkBox.buttonTintList = context.resources
                    .getColorStateList(R.color.checkbox_done, null)
                checkBox.isChecked = true
            }
            else{
                textView.setTextColor(Color.BLACK)
                textView.setPaintFlags(textView.getPaintFlags()
                        and Paint.STRIKE_THRU_TEXT_FLAG.inv())
                checkBox.buttonTintList = context.resources
                    .getColorStateList(R.color.checkbox_border_gray, null)
                checkBox.isChecked = false
                if(item.importance == Importance.HIGH){
                    checkBox.buttonTintList = context.resources
                        .getColorStateList(R.color.checkbox_border_red, null)
                }
            }
            checkBox.setOnCheckedChangeListener{view, isChecked ->
                if(isChecked){
                    textView.setTextColor(Color.GRAY)
                    textView.paintFlags = (textView.paintFlags
                            or Paint.STRIKE_THRU_TEXT_FLAG)
                    item.isDone = true
                    checkBox.buttonTintList = context.resources
                        .getColorStateList(R.color.checkbox_done, null)
                } else{
                    textView.setTextColor(Color.BLACK)
                    textView.setPaintFlags(textView.getPaintFlags()
                            and Paint.STRIKE_THRU_TEXT_FLAG.inv())
                    checkBox.buttonTintList = context.resources
                        .getColorStateList(R.color.checkbox_border_gray, null)
                    item.isDone = false
                    if(item.importance == Importance.HIGH){
                        checkBox.buttonTintList = context.resources
                            .getColorStateList(R.color.checkbox_border_red, null)
                    }
                }
            }
            layout.setOnClickListener{view ->
                onClick.onItemClicked(position)
            }
        }
    }

    fun addNewItem(item:ToDoItem){
        arr.addNewItem(item)
    }

    fun getItem(ind:Int):ToDoItem{
        return arr.getItem(ind)
    }

    fun deleteItem(ind:Int){
        arr.deleteItem(ind)
    }
}