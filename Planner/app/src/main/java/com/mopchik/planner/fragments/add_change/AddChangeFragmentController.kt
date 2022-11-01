package com.mopchik.planner.fragments.add_change

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.DatePicker
import androidx.core.os.bundleOf
import com.mopchik.planner.*
import java.text.SimpleDateFormat
import java.util.*

class AddChangeFragmentController(
    private val arguments: Bundle?,
    private val component: AddChangeFragmentComponent,
    private val datePickerDialogContext: Context,
    private val saveOptions: (Bundle)->Unit,
    private val goBack:()->Unit
) {
    @SuppressLint("SimpleDateFormat")
    private val formatter = SimpleDateFormat("dd MMMM yyyy")

    fun setUpViews(){
        initTextLayout()
        initDateLayout()
        initImportanceLayout()
        initDeleteLayout()

        component.closeBtn.setOnClickListener { goBack() }
        component.saveBtn.setOnClickListener {
            saveOptions(getToDoItemBundle())
            goBack()
        }
    }

    private fun initTextLayout(){
        val text = arguments?.getString(DESCRIPTION_KEY)
        component.editText.setText(text)
    }

    private fun initImportanceLayout(){
        val importanceId = arguments!!.getInt(IMPORTANCE_KEY, 0)
        component.spinner.setSelection(importanceId)
        component.spinnerTextView.text =
            component.spinner.selectedItem.toString()
        component.spinner.onItemSelectedListener =
            AddChangeSpinnerSelectedListener(component.spinnerTextView, component.spinner)
        component.chooseImportanceLayout.setOnClickListener{
            component.spinner.performClick()
        }
    }

    private fun initDateLayout(){
        setUpDateView()

        val onDateSetListener = DatePickerDialog.OnDateSetListener {
                _: DatePicker, y: Int, m: Int, d: Int ->
            onDateChose(y, m, d)
        }
        component.chooseDateLayout.setOnClickListener {
            startChoosingDate(onDateSetListener)
        }

        component.switchDate.setOnCheckedChangeListener{
                _: CompoundButton, isChecked: Boolean ->
            component.textOfDate.visibility = if(isChecked)
                View.VISIBLE else View.GONE
        }
    }

    private fun initDeleteLayout(){
        if(component.isCreatingNew){
            component.deleteIcon.setColorFilter(Color.GRAY)
            component.deleteText.setTextColor(Color.GRAY)
        }
        component.deleteLayout.setOnClickListener {
            if(!component.isCreatingNew) {
                saveOptions(bundleOf(ACTION_KEY to Action.DELETE.ordinal))
                goBack()
            }
        }
    }

    private fun setUpDateView(){
        val day = arguments!!.getInt(DAY_KEY, -1)
        val month = arguments.getInt(MONTH_KEY, -1)
        val year = arguments.getInt(YEAR_KEY, -1)

        if(day != -1 && month != - 1 && year != -1) {
            component.textOfDate.visibility = View.VISIBLE
            component.switchDate.isChecked = true
            component.chosenDate.set(year, month, day)
        } else {
            component.textOfDate.visibility = View.GONE
            component.switchDate.isChecked = false
        }

        component.textOfDate.text =
            formatter.format(component.chosenDate.time)
    }

    private fun startChoosingDate(
        onDateSetListener: DatePickerDialog.OnDateSetListener){
        if(component.switchDate.isChecked) {
            val datePickerDialog = DatePickerDialog(
                datePickerDialogContext, R.style.DialogTheme, onDateSetListener,
                component.chosenDate.get(
                    Calendar.YEAR),
                component.chosenDate.get(Calendar.MONTH),
                component.chosenDate.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            datePickerDialog.show()
        }
    }

    private fun onDateChose(year:Int, month:Int, day:Int){
        run {
            component.chosenDate.set(year, month, day)
            val strOfDate = formatter.format(component.chosenDate.time)
            component.textOfDate.text = strOfDate
        }
    }

    private fun getToDoItemBundle(): Bundle{
        return Bundle().apply {
            putInt(ACTION_KEY,
                if(component.isCreatingNew)
                    Action.CREATE.ordinal
                else
                    Action.CHANGE.ordinal)
            putString("description", component.editText.text.toString())
            putInt("importance", component.spinner.selectedItemPosition)
            if(component.switchDate.isChecked) {
                putInt("day", component.chosenDate.get(Calendar.DAY_OF_MONTH))
                putInt("month", component.chosenDate.get(Calendar.MONTH))
                putInt("year", component.chosenDate.get(Calendar.YEAR))
            }
        }
    }
}