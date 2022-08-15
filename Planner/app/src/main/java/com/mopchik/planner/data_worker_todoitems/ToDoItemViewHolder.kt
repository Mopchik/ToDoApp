package com.mopchik.planner.data_worker_todoitems

import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mopchik.planner.OnItemClickedListener
import com.mopchik.planner.R


class ToDoItemViewHolder(
    itemView: View,
    private val onClick: OnItemClickedListener,
    private val viewModel: ToDoItemViewModel
) : RecyclerView.ViewHolder(itemView) {

    private val textView = itemView.findViewById<TextView>(R.id.toDoItem_text)
    private val checkBox = itemView.findViewById<CheckBox>(R.id.toDoItem_checkBox)
    private val layout = itemView.findViewById<LinearLayout>(R.id.layout_item)

    fun bind(item: ToDoItem, pos:Int) {
        textView.text = item.text
        if (item.isDone) {
            setStyleItemIsDone()
            checkBox.isChecked = true
        } else {
            setStyleItemIsNotDone(item.importance)
            checkBox.isChecked = false
        }
        checkBox.setOnClickListener {
            val newItem = item.copy(isDone = checkBox.isChecked)
            viewModel.onChangeToDoItem(newItem, pos)
            /* if(isChecked){
                setStyleItemIsDone()
                item.isDone = true
            } else{
                setStyleItemIsNotDone(item.importance)
                item.isDone = false
            }*/
        }
        layout.setOnClickListener {
            onClick.onItemClicked(layoutPosition)
        }
    }

    private fun setStyleItemIsDone() {
        textView.setTextColor(Color.GRAY)
        textView.paintFlags = (textView.paintFlags
                or Paint.STRIKE_THRU_TEXT_FLAG)
        checkBox.buttonTintList = itemView.context.resources
            .getColorStateList(R.color.checkbox_done, null)
    }

    private fun setStyleItemIsNotDone(importance: Importance) {
        textView.setTextColor(Color.BLACK)
        textView.setPaintFlags(
            textView.getPaintFlags()
                    and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        )
        checkBox.buttonTintList = itemView.context.resources
            .getColorStateList(R.color.checkbox_border_gray, null)

        if (importance == Importance.HIGH) {
            checkBox.buttonTintList = itemView.context.resources
                .getColorStateList(R.color.checkbox_border_red, null)
        }
    }
}
