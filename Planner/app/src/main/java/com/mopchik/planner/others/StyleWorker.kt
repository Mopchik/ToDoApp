package com.mopchik.planner.others

import android.content.Context
import android.content.res.Resources
import android.graphics.Paint
import android.util.TypedValue
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
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

        fun setCheckBoxDone(checkBox: CheckBox, resources: Resources, context: Context){
            checkBox.buttonTintList = resources.getColorStateList(
                getColorIdByAttr(R.attr.colorGreen, context), null)
            checkBox.isChecked = true
        }

        fun setCheckBoxUndoneNotImportant(checkBox: CheckBox, resources: Resources, context: Context){
            checkBox.buttonTintList = resources
                .getColorStateList(getColorIdByAttr(R.attr.colorGrayLight, context), null)
            // checkBox.buttonDrawable = resources.getDrawable(
            //     R.drawable.checkbox);
            checkBox.isChecked = false

        }

        fun setCheckBoxUndoneImportant(checkBox: CheckBox, resources: Resources, context: Context){
            checkBox.buttonTintList = resources
                .getColorStateList(getColorIdByAttr(R.attr.colorRed, context), null)
            checkBox.isChecked = false
        }

        fun getColorByAttr(attr: Int, context: Context): Int{
            val typedValue = TypedValue()
            context.theme.resolveAttribute(attr, typedValue, true)
            return ContextCompat.getColor(context, typedValue.resourceId)
        }

        private fun getColorIdByAttr(attr: Int, context:Context): Int{
            val typedValue = TypedValue()
            context.theme.resolveAttribute(attr, typedValue, true)
            return typedValue.resourceId
        }
    }
}