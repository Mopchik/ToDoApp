package com.mopchik.planner.data_worker

import com.mopchik.planner.data_worker.data_base.ToDoItemDao
import com.mopchik.planner.to_do_item.ToDoItem
import kotlinx.coroutines.*
import javax.inject.Inject

class ToDoItemsRepository @Inject constructor(private val toDoItemDao: ToDoItemDao) {
    val liveData = toDoItemDao.getLiveData()
    private val scope = CoroutineScope(Dispatchers.IO)

    fun addNewItem(item: ToDoItem){
        scope.launch {
            toDoItemDao.addToDoItem(item)
        }
    }

    fun deleteItem(item: ToDoItem) {
        scope.launch {
            toDoItemDao.removeToDoItem(item)
        }
    }

    fun changeItem(newItem: ToDoItem){
        scope.launch{
            toDoItemDao.editToDoItem(newItem)
        }
    }

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
    //                 "1", "?????????????? ????", Importance.HIGH, false, today,
    //                 deadline = deadline, changeDate = changeDate
    //             )
    //         }
    //         val job2 = scope.launch {
    //             it2 = ToDoItem(
    //                 "2", "?????????????? ?? ??????????????", Importance.LOW, false, today,
    //                 deadline = deadline
    //             )
    //         }
    //         val job3 = scope.launch {
    //             it3 = ToDoItem("3", "Watch lection", Importance.NO, false, today)
    //         }
    //         val job4 = scope.launch {
    //             it4 = ToDoItem(
    //                 "4", "????????????????", Importance.HIGH, false, today,
    //                 changeDate = changeDate
    //             )
    //         }
    //         val job5 = scope.launch {
    //             it5 = ToDoItem("5", "", Importance.LOW, false, today)
    //         }
    //         val job6 = scope.launch {
    //             it6 = ToDoItem(
    //                 "6", "?????????????? ?? ???????? ???? ???????????????????????? ?? ???????????????? ?????????????? 5 ?????? ???? 7, " +
    //                         "???????? ?????????????? ???? ?????????????????????? ?? ???????????????? ?????????????? 9 ?????? ???? 10",
    //                 Importance.HIGH, false, today
    //             )
    //         }
    //         val job7 = scope.launch {
    //             it7 = ToDoItem("7", "???????????????????? ?? ????????", Importance.HIGH, false, today)
    //         }
    //         val job8 = scope.launch {
    //             it8 = ToDoItem("8", "???????????????? ?????? ??????????????", Importance.LOW, false, today)
    //         }
    //         val job9 = scope.launch {
    //             it9 = ToDoItem("9", "?????? ????????????????", Importance.HIGH, false, today)
    //         }
    //         val job10 = scope.launch {
    //             it10 = ToDoItem("10", "???? ?? ?????? ????????????????", Importance.HIGH, false, today)
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