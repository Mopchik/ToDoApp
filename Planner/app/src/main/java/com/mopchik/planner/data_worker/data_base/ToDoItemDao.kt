package com.mopchik.planner.data_worker.data_base

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mopchik.planner.to_do_item.ToDoItem

@Dao
interface ToDoItemDao {
    @Insert suspend fun addToDoItem(item: ToDoItem)
    @Update suspend fun editToDoItem(item: ToDoItem)
    @Delete suspend fun removeToDoItem(item: ToDoItem)

    @Query("select * from to_do_items")
    suspend fun all():List<ToDoItem>

    @Query("select * from to_do_items")
    fun getLiveData():LiveData<List<ToDoItem>>
}