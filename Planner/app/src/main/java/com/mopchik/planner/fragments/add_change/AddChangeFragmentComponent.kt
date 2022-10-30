package com.mopchik.planner.fragments.add_change

import android.annotation.SuppressLint
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.mopchik.planner.R
import java.util.*

class AddChangeFragmentComponent(fragmentView: View,
                                 val isCreatingNew: Boolean) {
    val closeBtn: Button
    val saveBtn: Button
    val editText: EditText
    val spinner: Spinner
    val spinnerTextView: TextView
    val textOfDate: TextView
    val chooseImportanceLayout: ConstraintLayout
    val chooseDateLayout: ConstraintLayout
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    val switchDate: Switch
    val deleteLayout: ConstraintLayout
    val deleteIcon: ImageView
    val deleteText: TextView
    val chosenDate:Calendar = Calendar.getInstance()

    init{
        closeBtn = fragmentView.findViewById(R.id.button_close)
        saveBtn = fragmentView.findViewById(R.id.button_save)
        editText = fragmentView.findViewById(R.id.editText)
        spinner = fragmentView.findViewById(R.id.spinner)
        spinnerTextView = fragmentView.findViewById(R.id.spinnerTextView)
        textOfDate = fragmentView.findViewById(R.id.textOfDate)
        chooseImportanceLayout = fragmentView.findViewById(R.id.chooseImportanceLayout)
        chooseDateLayout = fragmentView.findViewById(R.id.chooseDateLayout)
        switchDate = fragmentView.findViewById(R.id.switch_date)
        deleteLayout = fragmentView.findViewById(R.id.deleteLayout)
        deleteIcon = fragmentView.findViewById(R.id.deleteIcon)
        deleteText = fragmentView.findViewById(R.id.deleteText)
    }
}