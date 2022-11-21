package com.mopchik.planner.fragments.add_change

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.mopchik.planner.ContextClass
import com.mopchik.planner.ContextOwner
import com.mopchik.planner.R
import java.util.*
import javax.inject.Inject


class SelectDateFragment constructor(
    private val contextOwner: Context,
    private val calendar: Calendar,
    private val onDateChose: (year:Int, month:Int, day:Int)->Unit)
                                            : DialogFragment(), OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val yy: Int = calendar.get(Calendar.YEAR)
        val mm: Int = calendar.get(Calendar.MONTH)
        val dd: Int = calendar.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(contextOwner, R.style.DialogTheme, this, yy, mm, dd)
    }

    override fun onDateSet(view: DatePicker, yy: Int, mm: Int, dd: Int) {
        onDateChose(yy, mm + 1, dd)
    }

}