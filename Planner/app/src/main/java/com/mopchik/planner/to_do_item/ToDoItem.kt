package com.mopchik.planner.to_do_item

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity(tableName = "to_do_items")
data class ToDoItem(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    var text:String,
    var importance: Importance,
    var isDone:Boolean,
    var creationDate:Calendar,
    var deadline: Calendar? = null,
    var changeDate:Calendar? = null){

    constructor() : this(0, "", Importance.NO, false, Calendar.getInstance())
}