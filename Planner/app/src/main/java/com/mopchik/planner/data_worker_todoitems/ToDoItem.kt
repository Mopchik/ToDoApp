package com.mopchik.planner.data_worker_todoitems

import java.util.Calendar

data class ToDoItem(val id:String, var text:String, var importance: Importance,
                    var isDone:Boolean, var creationDate:Calendar,
                    var deadline: Calendar? = null, var changeDate:Calendar? = null){
    constructor(id:String) : this(id, "", Importance.NO, false, Calendar.getInstance())
}