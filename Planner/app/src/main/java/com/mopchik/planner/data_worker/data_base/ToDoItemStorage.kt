package com.mopchik.planner.data_worker.data_base

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mopchik.planner.data_worker.Converters
import com.mopchik.planner.to_do_item.ToDoItem

@Database(
    version = 2,
    entities = [ToDoItem::class]
)
@TypeConverters(Converters::class)
abstract class ToDoItemStorage: RoomDatabase() {
    abstract fun toDoItems(): ToDoItemDao
}