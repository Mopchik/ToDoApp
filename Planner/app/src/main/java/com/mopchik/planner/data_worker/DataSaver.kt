package com.mopchik.planner.data_worker

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mopchik.planner.to_do_item.ToDoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DataSaver(private val prefs: SharedPreferences,
                private val viewModelScope:CoroutineScope) {

    private val gson = Gson()

    fun saveToDoItemsList(list: List<ToDoItem>){
        viewModelScope.launch(Dispatchers.IO) {
            prefs.edit().apply {
                remove("toDoItemList")
                putString("toDoItemList", gson.toJson(list))
            }.apply()
        }
    }

    fun getToDoItemsList():List<ToDoItem>?{
        val listType = object : TypeToken<List<ToDoItem>>() {}.type
        val list: List<ToDoItem>? =
            gson.fromJson(prefs.getString("toDoItemList",""), listType)
        if(list != null){
            Log.i("SAVING_DATA", "List was read")
        } else {
            Log.i("SAVING_DATA", "Unfortunately, list was not read")
        }
        return list
    }
}