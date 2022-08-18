package com.mopchik.planner

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_add.*
import java.text.SimpleDateFormat
import java.util.*

class AddActivity: Activity(), AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val closeBtn = findViewById<Button>(R.id.button_close)
        val saveBtn = findViewById<Button>(R.id.button_save)
        val editText = findViewById<EditText>(R.id.editText)
        val spinner = findViewById<Spinner>(R.id.spinner)
        val spinnerTextView = findViewById<TextView>(R.id.spinnerTextView)
        val textOfDate = findViewById<TextView>(R.id.textOfDate)
        val chooseImportanceLayout = findViewById<ConstraintLayout>(R.id.chooseImportanceLayout)
        val chooseDateLayout = findViewById<ConstraintLayout>(R.id.chooseDateLayout)
        val switchDate = findViewById<Switch>(R.id.switch_date)
        val deleteLayout = findViewById<ConstraintLayout>(R.id.deleteLayout)
        val deleteIcon = findViewById<ImageView>(R.id.deleteIcon)
        val deleteText = findViewById<TextView>(R.id.deleteText)

        val chosenDate = Calendar.getInstance()

        val text = intent.getStringExtra("description")
        val importanceId = intent.getIntExtra("importance", -1)
        val day = intent.getIntExtra("day", -1)
        val month = intent.getIntExtra("month", -1)
        val year = intent.getIntExtra("year", -1)
        val formatter = SimpleDateFormat("dd MMMM yyyy")

        val isCreatingNew = importanceId == -1

        if(day != -1 && month != - 1 && year != -1) {
            textOfDate.visibility = View.VISIBLE
            switchDate.isChecked = true
            chosenDate.set(year, month, day)
        } else {
            textOfDate.visibility = View.GONE
            switchDate.isChecked = false
        }
        textOfDate.text = formatter.format(chosenDate.time)

        editText.setText(text)

        if(isCreatingNew){
            deleteIcon.setColorFilter(Color.GRAY)
            deleteText.setTextColor(Color.GRAY)
        }

        spinner.setSelection(if(importanceId != -1) importanceId else 0)
        spinnerTextView.text = spinner.selectedItem.toString()
        spinner.onItemSelectedListener = this
        chooseImportanceLayout.setOnClickListener{
            spinner.performClick()
        }
        closeBtn.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
        saveBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("deleted", false)
            intent.putExtra("description", editText.text.toString())
            intent.putExtra("importance", spinner.selectedItemPosition)
            if(switchDate.isChecked) {
                intent.putExtra("day", chosenDate.get(Calendar.DAY_OF_MONTH))
                intent.putExtra("month", chosenDate.get(Calendar.MONTH))
                intent.putExtra("year", chosenDate.get(Calendar.YEAR))
            }
            setResult(RESULT_OK, intent)
            finish()
        }

        val onDateSetListener = DatePickerDialog.OnDateSetListener(){
                datePicker: DatePicker, year: Int, month: Int, day: Int ->
            run {
                chosenDate.set(year, month, day)
                val strOfDate = formatter.format(chosenDate.time)
                textOfDate.text = strOfDate
            }
        }
        chooseDateLayout.setOnClickListener {
            if(switchDate.isChecked) {
                val datePickerDialog = DatePickerDialog(
                    this, R.style.DialogTheme, onDateSetListener, chosenDate.get(Calendar.YEAR),
                    chosenDate.get(Calendar.MONTH), chosenDate.get(Calendar.DAY_OF_MONTH)
                )
                datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
                datePickerDialog.show()
            }
        }

        switchDate.setOnCheckedChangeListener{ compoundButton: CompoundButton, isChecked: Boolean ->
            textOfDate.visibility = if(isChecked) View.VISIBLE else View.GONE
        }

        deleteLayout.setOnClickListener {
            if(!isCreatingNew) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("deleted", true)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        spinnerTextView.text = spinner.selectedItem.toString()
    }
    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}