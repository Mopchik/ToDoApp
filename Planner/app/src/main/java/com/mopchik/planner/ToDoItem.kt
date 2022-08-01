package com.mopchik.planner

import java.util.Calendar

data class ToDoItem(val id:String, var text:String, var importance:Importance,
                    var isDone:Boolean, var creationDate:Calendar,
                    var deadline: Calendar? = null, var changeDate:Calendar? = null)