package com.mopchik.planner.others

import android.content.res.Resources
import android.graphics.Paint
import android.widget.CheckBox
import android.widget.TextView
import com.mopchik.planner.R

class StyleWorker {
    companion object {
        fun crossOutTextView(textView: TextView) {
            textView.paintFlags = (textView.paintFlags
                    or Paint.STRIKE_THRU_TEXT_FLAG)
        }

        fun noCrossOutTextView(textView: TextView) {
            textView.paintFlags =
                textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        fun setCheckBoxDone(checkBox: CheckBox, resources: Resources){
            checkBox.buttonTintList = resources.getColorStateList(R.color.checkbox_done, null)
            checkBox.isChecked = true
        }

        fun setCheckBoxUndoneNotImportant(checkBox: CheckBox, resources: Resources){
            checkBox.buttonTintList = resources
                .getColorStateList(R.color.checkbox_border_gray, null)
            checkBox.isChecked = false
        }

        fun setCheckBoxUndoneImportant(checkBox: CheckBox, resources: Resources){
            checkBox.buttonTintList = resources
                .getColorStateList(R.color.checkbox_border_red, null)
            checkBox.isChecked = false
        }
    }
}