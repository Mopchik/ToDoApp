package com.mopchik.planner.data_worker

import com.mopchik.planner.to_do_item.ToDoItem
import kotlinx.coroutines.*
import javax.inject.Inject

class ToDoItemsRepository @Inject constructor(
    private val toDoItemDao: ToDoItemDao) {
    // private var listOfItems:List<ToDoItem> = ArrayList()
    // val liveData = MutableLiveData<List<ToDoItem>>()
    val dbLiveData = toDoItemDao.observeAll()
    private val scope = CoroutineScope(Dispatchers.IO)


    fun addNewItem(item: ToDoItem){
        scope.launch {
            // listOfItems.add(item)
            // liveData.postValue(listOfItems)
            toDoItemDao.addToDoItem(item)
        }
    }

    fun deleteItem(item: ToDoItem) {
        // scope.launch {
        //     listOfItems.remove(item)
        // }.join()
        // liveData.value = listOfItems
        scope.launch {
            toDoItemDao.removeToDoItem(item)
        }
    }

    // suspend fun setList(list: List<ToDoItem>){
    //     scope.launch {
    //         listOfItems = ArrayList(list)
    //     }.join()
    //     liveData.value = listOfItems
    // }

    fun changeItem(oldItem: ToDoItem, newItem: ToDoItem){
        // scope.launch{
        //     try {
        //         listOfItems[listOfItems.indexOf(oldItem)] = newItem
        //         liveData.postValue(listOfItems)
        //     } catch(e: ArrayIndexOutOfBoundsException){
        //         Log.e("Kostik", "Clicking too fast " + e.message)
        //     }
        // }
        scope.launch {
            toDoItemDao.editToDoItem(newItem)
        }
    }
    // suspend fun updateToDoItems(){
    //     scope.launch{
    //         // listOfItems = ...
    //         liveData.postValue(listOfItems)
    //     }
    // }

    // companion object {
    //     val INSTANCE by lazy {
    //         val rep = ToDoItemsRepository()
    //         // val scope = CoroutineScope(Dispatchers.IO)
    //         // scope.launch {
    //         //     init(rep)
    //         // }
    //         rep
    //     }/*{
    //         if(_inst == null) {
    //             get()
    //         }
    //         return _inst ?: throw NullPointerException("Can't create custom repository.")
    //     }*/
//
    //     private suspend fun init(rep: ToDoItemsRepository): ToDoItemsRepository {
    //         val today = Calendar.getInstance()
    //         val deadline = Calendar.getInstance()
    //         deadline.set(2022, 9, 5)
    //         val changeDate = Calendar.getInstance()
    //         changeDate.set(2022, 7, 30)
//
//
    //         lateinit var it1: ToDoItem
    //         lateinit var it2: ToDoItem
    //         lateinit var it3: ToDoItem
    //         lateinit var it4: ToDoItem
    //         lateinit var it5: ToDoItem
    //         lateinit var it6: ToDoItem
    //         lateinit var it7: ToDoItem
    //         lateinit var it8: ToDoItem
    //         lateinit var it9: ToDoItem
    //         lateinit var it10: ToDoItem
//
    //         val scope = CoroutineScope(Dispatchers.IO)
//
    //         val job1 = scope.launch {
    //             it1 = ToDoItem(
    //                 "1", "Сделать дз", Importance.HIGH, false, today,
    //                 deadline = deadline, changeDate = changeDate
    //             )
    //         }
    //         val job2 = scope.launch {
    //             it2 = ToDoItem(
    //                 "2", "Сходить в магазин", Importance.LOW, false, today,
    //                 deadline = deadline
    //             )
    //         }
    //         val job3 = scope.launch {
    //             it3 = ToDoItem("3", "Watch lection", Importance.NO, false, today)
    //         }
    //         val job4 = scope.launch {
    //             it4 = ToDoItem(
    //                 "4", "Покурить", Importance.HIGH, false, today,
    //                 changeDate = changeDate
    //             )
    //         }
    //         val job5 = scope.launch {
    //             it5 = ToDoItem("5", "", Importance.LOW, false, today)
    //         }
    //         val job6 = scope.launch {
    //             it6 = ToDoItem(
    //                 "6", "Сыграть в доту за найтсталкера и победить минимум 5 раз из 7, " +
    //                         "либо сыграть за бистмастера и победить минимум 9 раз из 10",
    //                 Importance.HIGH, false, today
    //             )
    //         }
    //         val job7 = scope.launch {
    //             it7 = ToDoItem("7", "Покопаться в себе", Importance.HIGH, false, today)
    //         }
    //         val job8 = scope.launch {
    //             it8 = ToDoItem("8", "Подумать над будущим", Importance.LOW, false, today)
    //         }
    //         val job9 = scope.launch {
    //             it9 = ToDoItem("9", "Ещё покурить", Importance.HIGH, false, today)
    //         }
    //         val job10 = scope.launch {
    //             it10 = ToDoItem("10", "Ну и ещё покурить", Importance.HIGH, false, today)
    //         }
    //         job1.join()
    //         job2.join()
    //         job3.join()
    //         job4.join()
    //         job5.join()
    //         job6.join()
    //         job7.join()
    //         job8.join()
    //         job9.join()
    //         job10.join()
    //         rep.addNewItem(it1)
    //         rep.addNewItem(it2)
    //         rep.addNewItem(it3)
    //         rep.addNewItem(it4)
    //         rep.addNewItem(it5)
    //         rep.addNewItem(it6)
    //         rep.addNewItem(it7)
    //         rep.addNewItem(it8)
    //         rep.addNewItem(it9)
    //         rep.addNewItem(it10)
//
    //         return rep
    //     }
    // }
}