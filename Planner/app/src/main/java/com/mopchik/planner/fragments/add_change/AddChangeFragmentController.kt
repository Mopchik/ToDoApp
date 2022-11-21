package com.mopchik.planner.fragments.add_change

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.CompoundButton
import android.widget.DatePicker
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.mopchik.planner.*
import com.mopchik.planner.others.StyleWorker
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AddChangeFragmentViewScope
class AddChangeFragmentController @Inject constructor(
    private val arguments: Bundle,
    private val binding: AddChangeFragmentBinding,
    @ContextClass(ContextOwner.ADD_CHANGE_FRAGMENT)
    private val context: Context,
    private val saveOptions: (Bundle)->Unit,
    @ActionParameterType(TypeOfAction.GO_BACK)
    private val goBack:()->Unit,
    @FragmentManagerClass(FragmentManagerOwner.ADD_CHANGE_FRAGMENT)
    private val childFragmentManager: FragmentManager
) {

    @SuppressLint("SimpleDateFormat")
    private val formatter = SimpleDateFormat("dd MMMM yyyy")
    private val isCreatingNew = arguments.getBoolean(CREATING_KEY)

    fun setUpViews(){
        initTextLayout()
        initDateLayout()
        initImportanceLayout()
        initDeleteLayout()

        binding.closeBtn.setOnClickListener { goBack() }
        binding.saveBtn.setOnClickListener {
            saveOptions(getToDoItemBundle())
            goBack()
        }
    }

    private fun initTextLayout(){
        val text = arguments.getString(DESCRIPTION_KEY)
        binding.editText.setText(text)
    }

    private fun initImportanceLayout(){
        val importanceId = arguments.getInt(IMPORTANCE_KEY, 0)
        binding.spinner.setSelection(importanceId)
        binding.spinnerTextView.text =
            binding.spinner.selectedItem.toString()
        binding.spinner.onItemSelectedListener =
            AddChangeSpinnerSelectedListener(binding.spinnerTextView, binding.spinner)
        binding.chooseImportanceLayout.setOnClickListener{
            binding.spinner.performClick()
        }
    }

    private fun initDateLayout(){
        setUpDateView()

        val onDateSetListener = DatePickerDialog.OnDateSetListener {
                _: DatePicker, y: Int, m: Int, d: Int ->
            onDateChose(y, m, d)
        }
        binding.chooseDateLayout.setOnClickListener {
            startChoosingDate(onDateSetListener)
        }

        binding.switchDate.setOnCheckedChangeListener{
                _: CompoundButton, isChecked: Boolean ->
            binding.textOfDate.visibility = if(isChecked)
                View.VISIBLE else View.GONE
        }
    }

    private fun initDeleteLayout(){
        if(isCreatingNew){
            binding.deleteIcon.setColorFilter(
                StyleWorker.getColorByAttr(R.attr.colorGrayLight, context)
            )
            binding.deleteText.setTextColor(
                StyleWorker.getColorByAttr(R.attr.colorTertiaryLabel, context)
            )
        }
        binding.deleteLayout.setOnClickListener {
            if(!isCreatingNew) {
                saveOptions(bundleOf(ACTION_KEY to Action.DELETE.ordinal))
                goBack()
            }
        }
    }

    private fun setUpDateView(){
        val day = arguments.getInt(DAY_KEY, -1)
        val month = arguments.getInt(MONTH_KEY, -1)
        val year = arguments.getInt(YEAR_KEY, -1)

        if(day != -1 && month != - 1 && year != -1) {
            binding.textOfDate.visibility = View.VISIBLE
            binding.switchDate.isChecked = true
            binding.chosenDate.set(year, month, day)
        } else {
            binding.textOfDate.visibility = View.GONE
            binding.switchDate.isChecked = false
        }

        binding.textOfDate.text =
            formatter.format(binding.chosenDate.time)
    }

    private fun startChoosingDate(
        onDateSetListener: DatePickerDialog.OnDateSetListener){
        if(binding.switchDate.isChecked) {
            // val datePickerDialog = DatePickerDialog(
            //     context, R.style.DialogTheme, onDateSetListener,
            //     binding.chosenDate.get(
            //         Calendar.YEAR),
            //     binding.chosenDate.get(Calendar.MONTH),
            //     binding.chosenDate.get(Calendar.DAY_OF_MONTH)
            // )
            // datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            // datePickerDialog.show()
            val dialogFragment = SelectDateFragment(context,
                    binding.chosenDate
            ) { year: Int, month: Int, day: Int ->
                onDateChose(year, month - 1, day)
            }
            dialogFragment.show(childFragmentManager, "DatePicker")
        }
    }

    private fun onDateChose(year:Int, month:Int, day:Int){
        binding.chosenDate.set(year, month, day)
        val strOfDate = formatter.format(binding.chosenDate.time)
        binding.textOfDate.text = strOfDate
    }

    private fun getToDoItemBundle(): Bundle{
        return Bundle().apply {
            putInt(ACTION_KEY,
                if(isCreatingNew)
                    Action.CREATE.ordinal
                else
                    Action.CHANGE.ordinal)
            putString("description", binding.editText.text.toString())
            putInt("importance", binding.spinner.selectedItemPosition)
            if(binding.switchDate.isChecked) {
                putInt("day", binding.chosenDate.get(Calendar.DAY_OF_MONTH))
                putInt("month", binding.chosenDate.get(Calendar.MONTH))
                putInt("year", binding.chosenDate.get(Calendar.YEAR))
            }
        }
    }
}