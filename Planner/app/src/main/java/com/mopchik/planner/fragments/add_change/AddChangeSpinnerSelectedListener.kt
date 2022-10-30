package com.mopchik.planner.fragments.add_change

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView

class AddChangeSpinnerSelectedListener(
                private val spinnerTextView: TextView,
                private val spinner: Spinner): AdapterView.OnItemSelectedListener {

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        spinnerTextView.text = spinner.selectedItem.toString()
    }
    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}