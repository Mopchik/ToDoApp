package com.mopchik.planner.fragments

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.mopchik.planner.*
import java.text.SimpleDateFormat
import java.util.*

class AddFragment: Fragment(), AdapterView.OnItemSelectedListener {

    private val formatter = SimpleDateFormat("dd MMMM yyyy")
    private lateinit var fragmentView: View
    private lateinit var closeBtn: Button
    private lateinit var saveBtn: Button
    private lateinit var editText: EditText
    private lateinit var spinner: Spinner
    private lateinit var spinnerTextView: TextView
    private lateinit var textOfDate: TextView
    private lateinit var chooseImportanceLayout: ConstraintLayout
    private lateinit var chooseDateLayout: ConstraintLayout
    private lateinit var switchDate: Switch
    private lateinit var deleteLayout: ConstraintLayout
    private lateinit var deleteIcon: ImageView
    private lateinit var deleteText: TextView
    private val chosenDate = Calendar.getInstance()
    private var isCreatingNew:Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View{
        isCreatingNew = requireArguments().getBoolean(CREATING_KEY)
        fragmentView = inflater.inflate(R.layout.add_fragment, container, false)
        findViews()
        initTextLayout()
        initDateLayout()
        initImportanceLayout()
        initDeleteLayout()

        closeBtn.setOnClickListener { goBack() }
        saveBtn.setOnClickListener { saveOptions() }

        return fragmentView
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        spinnerTextView.text = spinner.selectedItem.toString()
    }
    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    private fun findViews(){
        closeBtn = fragmentView.findViewById<Button>(R.id.button_close)
        saveBtn = fragmentView.findViewById<Button>(R.id.button_save)
        editText = fragmentView.findViewById<EditText>(R.id.editText)
        spinner = fragmentView.findViewById<Spinner>(R.id.spinner)
        spinnerTextView = fragmentView.findViewById<TextView>(R.id.spinnerTextView)
        textOfDate = fragmentView.findViewById<TextView>(R.id.textOfDate)
        chooseImportanceLayout = fragmentView.findViewById<ConstraintLayout>(R.id.chooseImportanceLayout)
        chooseDateLayout = fragmentView.findViewById<ConstraintLayout>(R.id.chooseDateLayout)
        switchDate = fragmentView.findViewById<Switch>(R.id.switch_date)
        deleteLayout = fragmentView.findViewById<ConstraintLayout>(R.id.deleteLayout)
        deleteIcon = fragmentView.findViewById<ImageView>(R.id.deleteIcon)
        deleteText = fragmentView.findViewById<TextView>(R.id.deleteText)
    }

    private fun initTextLayout(){
        val text = arguments?.getString(DESCRIPTION_KEY)
        editText.setText(text)
    }

    private fun initImportanceLayout(){
        val importanceId = requireArguments().getInt(IMPORTANCE_KEY, 0)
        spinner.setSelection(importanceId)
        spinnerTextView.text = spinner.selectedItem.toString()
        spinner.onItemSelectedListener = this
        chooseImportanceLayout.setOnClickListener{
            spinner.performClick()
        }
    }

    private fun initDateLayout(){
        val day = requireArguments().getInt(DAY_KEY, -1)
        val month = requireArguments().getInt(MONTH_KEY, -1)
        val year = requireArguments().getInt(YEAR_KEY, -1)

        if(day != -1 && month != - 1 && year != -1) {
            textOfDate.visibility = View.VISIBLE
            switchDate.isChecked = true
            chosenDate.set(year, month, day)
        } else {
            textOfDate.visibility = View.GONE
            switchDate.isChecked = false
        }
        textOfDate.text = formatter.format(chosenDate.time)

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
                    requireContext(), R.style.DialogTheme, onDateSetListener, chosenDate.get(Calendar.YEAR),
                    chosenDate.get(Calendar.MONTH), chosenDate.get(Calendar.DAY_OF_MONTH)
                )
                datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
                datePickerDialog.show()
            }
        }

        switchDate.setOnCheckedChangeListener{ compoundButton: CompoundButton, isChecked: Boolean ->
            textOfDate.visibility = if(isChecked) View.VISIBLE else View.GONE
        }
    }

    private fun initDeleteLayout(){
        if(isCreatingNew){
            deleteIcon.setColorFilter(Color.GRAY)
            deleteText.setTextColor(Color.GRAY)
        }
        deleteLayout.setOnClickListener {
            if(!isCreatingNew) {
                setFragmentResult(REQUEST_KEY, bundleOf(ACTION_KEY to Action.DELETE.ordinal))
                goBack()
            }
        }
    }

    private fun goBack(){
        requireActivity().onBackPressed()
    }

    private fun saveOptions(){
        val bundle = Bundle().apply {
            putInt(ACTION_KEY, if(isCreatingNew) Action.CREATE.ordinal else Action.CHANGE.ordinal)
            putString("description", editText.text.toString())
            putInt("importance", spinner.selectedItemPosition)
            if(switchDate.isChecked) {
                putInt("day", chosenDate.get(Calendar.DAY_OF_MONTH))
                putInt("month", chosenDate.get(Calendar.MONTH))
                putInt("year", chosenDate.get(Calendar.YEAR))
            }
        }
        setFragmentResult(REQUEST_KEY, bundle)
        goBack()
    }

    companion object{
        fun newInstance(description: String, importanceId:Int, isCreatingNew: Boolean,
                        day: Int?, month: Int?, year:Int?): AddFragment {
            val addFragment = AddFragment()
            addFragment.arguments = Bundle().apply {
                putString(DESCRIPTION_KEY, description)
                putInt(IMPORTANCE_KEY, importanceId)
                putBoolean(CREATING_KEY, isCreatingNew)
                putInt(DAY_KEY, day ?: -1)
                putInt(MONTH_KEY, month ?: -1)
                putInt(YEAR_KEY, year ?: -1)
            }
            return addFragment
        }
    }
}