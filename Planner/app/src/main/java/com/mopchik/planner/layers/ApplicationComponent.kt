package com.mopchik.planner.layers

import com.mopchik.planner.data_worker.ToDoItemsRepository

class ApplicationComponent {
    private val toDoItemsRepository: ToDoItemsRepository = ToDoItemsRepository.INSTANCE
    val viewModelFactory = ViewModelFactory(toDoItemsRepository)
}