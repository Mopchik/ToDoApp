package com.mopchik.planner.data_worker_todoitems

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import java.util.Calendar

class ToDoItemsRepository {
    private val listOfItems:ArrayList<ToDoItem> = ArrayList()
    val liveData = MutableLiveData<List<ToDoItem>>()

    suspend fun addNewItem(item: ToDoItem){
        withContext(Dispatchers.IO) {
            listOfItems.add(item)
            liveData.postValue(listOfItems)
        }
    }
    suspend fun deleteItem(ind:Int) {
        withContext(Dispatchers.IO) {
            listOfItems.removeAt(ind)
            liveData.postValue(listOfItems)
        }
    }
    suspend fun changeItem(newItem: ToDoItem, pos:Int){
        withContext(Dispatchers.IO){
            listOfItems[pos] = newItem
            liveData.postValue(listOfItems)
        }
    }
    suspend fun updateToDoItems(){
        withContext(Dispatchers.IO){
            // listOfItems = ...
        }
        liveData.postValue(listOfItems)
    }

    companion object {
        val INSTANCE by lazy {
            val rep = ToDoItemsRepository()
            val scope = CoroutineScope(Dispatchers.IO)
            scope.launch {
                init(rep)
            }
            rep
        }/*{
            if(_inst == null) {
                get()
            }
            return _inst ?: throw NullPointerException("Can't create custom repository.")
        }*/

        private suspend fun init(rep: ToDoItemsRepository): ToDoItemsRepository {
            val today = Calendar.getInstance()
            val deadline = Calendar.getInstance()
            deadline.set(2022, 9, 5)
            val changeDate = Calendar.getInstance()
            changeDate.set(2022, 7, 30)


            lateinit var it1: ToDoItem
            lateinit var it2: ToDoItem
            lateinit var it3: ToDoItem
            lateinit var it4: ToDoItem
            lateinit var it5: ToDoItem
            lateinit var it6: ToDoItem
            lateinit var it7: ToDoItem
            lateinit var it8: ToDoItem
            lateinit var it9: ToDoItem
            lateinit var it10: ToDoItem

            val scope = CoroutineScope(Dispatchers.IO)

            val job1 = scope.launch {
                delay(1000)
                it1 = ToDoItem(
                    "1", "Сделать дз", Importance.HIGH, false, today,
                    deadline = deadline, changeDate = changeDate
                )
            }
            val job2 = scope.launch {
                delay(1000)
                it2 = ToDoItem(
                    "2", "Сходить в магазин", Importance.LOW, false, today,
                    deadline = deadline
                )
            }
            val job3 = scope.launch {
                delay(1000)
                it3 = ToDoItem("3", "Watch lection", Importance.NO, false, today)
            }
            val job4 = scope.launch {
                delay(1000)
                it4 = ToDoItem(
                    "4", "Покурить", Importance.HIGH, false, today,
                    changeDate = changeDate
                )
            }
            val job5 = scope.launch {
                delay(1000)
                it5 = ToDoItem("5", "", Importance.LOW, false, today)
            }
            val job6 = scope.launch {
                delay(1000)
                it6 = ToDoItem(
                    "6", "Сыграть в доту за найтсталкера и победить минимум 5 раз из 7, " +
                            "либо сыграть за бистмастера и победить минимум 9 раз из 10",
                    Importance.HIGH, false, today
                )
            }
            val job7 = scope.launch {
                delay(1000)
                it7 = ToDoItem("7", "Покопаться в себе", Importance.HIGH, false, today)
            }
            val job8 = scope.launch {
                delay(1000)
                it8 = ToDoItem("8", "Подумать над будущим", Importance.LOW, false, today)
            }
            val job9 = scope.launch {
                delay(1000)
                it9 = ToDoItem("9", "Ещё покурить", Importance.HIGH, false, today)
            }
            val job10 = scope.launch {
                delay(1000)
                it10 = ToDoItem("10", "Ну и ещё покурить", Importance.HIGH, false, today)
            }
            job1.join()
            job2.join()
            job3.join()
            job4.join()
            job5.join()
            job6.join()
            job7.join()
            job8.join()
            job9.join()
            job10.join()
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