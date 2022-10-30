package com.mopchik.planner.to_do_item

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity(tableName = "to_do_items")
data class ToDoItem(
    @PrimaryKey
    val id:String,
    var text:String,
    var importance: Importance,
    var isDone:Boolean,
    var creationDate:Calendar,
    var deadline: Calendar? = null,
    var changeDate:Calendar? = null){

    constructor(id:String) : this(id, "", Importance.NO, false, Calendar.getInstance())
}