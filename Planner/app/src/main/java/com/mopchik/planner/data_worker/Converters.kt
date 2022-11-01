package com.mopchik.planner.data_worker

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun toCalendar(strArray: String?): Calendar? {
        if(strArray == null) return null
        val splitArray = strArray.split(' ')
        if(splitArray.size != 3)
            throw IllegalArgumentException("IntArray should contains day, month and year.")
        val intArray = IntArray(splitArray.size)
        for(i in splitArray.indices){
            intArray[i] = splitArray[i].toInt()
        }
        val day = intArray[0]
        val month = intArray[1]
        val year = intArray[2]
        return Calendar.getInstance().apply{set(year, month, day)}
    }

    @TypeConverter
    fun fromCalendar(calendar: Calendar?): String?{
        return if(calendar == null) null
            else calendar.get(Calendar.DAY_OF_MONTH).toString() + " " +
                calendar.get(Calendar.MONTH) + " " +
                calendar.get(Calendar.YEAR)

    }
}