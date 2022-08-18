package com.mopchik.planner.application_layer

import com.mopchik.planner.ViewModelFactory
import com.mopchik.planner.data_worker_todoitems.ToDoItemsRepository

class ApplicationComponent {
    private val toDoItemsRepository: ToDoItemsRepository = ToDoItemsRepository.INSTANCE
    val viewModelFactory = ViewModelFactory(toDoItemsRepository)
}