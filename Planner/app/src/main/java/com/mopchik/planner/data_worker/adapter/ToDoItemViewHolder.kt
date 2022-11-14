package com.mopchik.planner.data_worker.adapter

import android.content.Context
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mopchik.planner.R
import com.mopchik.planner.others.StyleWorker
import com.mopchik.planner.to_do_item.Importance
import com.mopchik.planner.to_do_item.ToDoItem


class ToDoItemViewHolder(
    itemView: View,
    private val onClick: (item: ToDoItem) -> Unit,
    private val onCheck: (item: ToDoItem, isChecked:Boolean) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    private val context: Context = itemView.context
    private val textView = itemView.findViewById<TextView>(R.id.toDoItem_text)
    private val checkBox = itemView.findViewById<CheckBox>(R.id.toDoItem_checkBox)
    private val layout = itemView.findViewById<LinearLayout>(R.id.layout_item)
    private val priorityHigh = itemView.findViewById<ImageView>(R.id.priority_high)

    fun bind(item: ToDoItem) {
        textView.text = item.text
        if (item.isDone) {
            setDoneStyle()
        } else {
            setUndoneStyle(item.importance)
        }
        checkBox.setOnClickListener {
            onCheck(item, checkBox.isChecked)
        }
        layout.setOnClickListener {
            onClick(item)
        }
        if(item.importance == Importance.HIGH && !item.isDone) {
            priorityHigh.visibility = View.VISIBLE
        } else{
            priorityHigh.visibility = View.GONE
        }
    }

    private fun setDoneStyle(){
        textView.setTextColor(StyleWorker
            .getColorByAttr(R.attr.colorTertiaryLabel, context))
        StyleWorker.crossOutTextView(textView)
        StyleWorker.setCheckBoxDone(checkBox, itemView.resources, context)
    }

    private fun setUndoneStyle(importance: Importance){
        textView.setTextColor(StyleWorker
            .getColorByAttr(R.attr.colorPrimaryLabel, context))
        StyleWorker.noCrossOutTextView(textView)
        if (importance == Importance.HIGH) {
            StyleWorker.setCheckBoxUndoneImportant(checkBox, itemView.resources, context)
        } else {
            StyleWorker.setCheckBoxUndoneNotImportant(checkBox, itemView.resources, context)
        }
    }
}
