package com.mopchik.planner

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner

class AddActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        val closeBtn = findViewById<Button>(R.id.button_close)
        val saveBtn = findViewById<Button>(R.id.button_save)
        val editText = findViewById<EditText>(R.id.editText)
        val spinner = findViewById<Spinner>(R.id.spinner)
        closeBtn.setOnClickListener {
            // val intent = Intent(this, MainActivity::class.java)
            setResult(RESULT_CANCELED)
            finish()
        }
        saveBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("description", editText.text.toString())
            intent.putExtra("importance", spinner.selectedItemPosition)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}