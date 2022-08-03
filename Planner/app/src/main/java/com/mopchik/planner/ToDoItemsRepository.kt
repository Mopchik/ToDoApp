package com.mopchik.planner

import java.util.Calendar

class ToDoItemsRepository {
    private val listOfItems:ArrayList<ToDoItem> = ArrayList()
    fun addNewItem(item:ToDoItem){
        listOfItems.add(item)
    }
    fun getItem(ind:Int):ToDoItem{
        return listOfItems[ind]
    }
    fun getSize():Int{
        return listOfItems.size
    }
    fun deleteItem(ind:Int){
        listOfItems.removeAt(ind)
    }
    companion object {
        val INSTANCE by lazy { init() }
        private fun init():ToDoItemsRepository {
            val rep = ToDoItemsRepository()
            val today = Calendar.getInstance()
            val deadline = Calendar.getInstance()
            deadline.set(2022, 9, 5)
            val changeDate = Calendar.getInstance()
            changeDate.set(2022, 7, 30)

            val it1 = ToDoItem(
                "1", "Сделать дз", Importance.HIGH, false, today,
                deadline = deadline, changeDate = changeDate
            )
            val it2 = ToDoItem(
                "2", "Сходить в магазин", Importance.LOW, false, today,
                deadline = deadline
            )
            val it3 = ToDoItem("3", "Watch lection", Importance.NO, false, today)
            val it4 = ToDoItem(
                "4", "Покурить", Importance.HIGH, false, today,
                changeDate = changeDate
            )
            val it5 = ToDoItem("5", "", Importance.LOW, false, today)
            val it6 = ToDoItem(
                "6", "Сыграть в доту за найтсталкера и победить минимум 5 раз из 7, " +
                        "либо сыграть за бистмастера и победить минимум 9 раз из 10",
                Importance.HIGH, false, today
            )
            val it7 = ToDoItem("7", "Покопаться в себе", Importance.HIGH, false, today)
            val it8 = ToDoItem("8", "Подумать над будущим", Importance.LOW, false, today)
            val it9 = ToDoItem("9", "Ещё покурить", Importance.HIGH, false, today)
            val it10 = ToDoItem("10", "Ну и ещё покурить", Importance.HIGH, false, today)

            rep.addNewItem(it1)
            rep.addNewItem(it2)
            rep.addNewItem(it3)
            rep.addNewItem(it4)
            rep.addNewItem(it5)
            rep.addNewItem(it6)
            rep.addNewItem(it7)
            rep.addNewItem(it8)
            rep.addNewItem(it9)
            rep.addNewItem(it10)

            return rep
        }
    }
}