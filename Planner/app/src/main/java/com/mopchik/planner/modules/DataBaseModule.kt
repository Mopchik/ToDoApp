package com.mopchik.planner.modules

import android.content.Context
import androidx.room.Room
import com.mopchik.planner.data_worker.ToDoItemDao
import com.mopchik.planner.data_worker.ToDoItemStorage
import dagger.Module
import dagger.Provides

@Module
class DataBaseModule{
    @Provides
    fun provideToDoItemDao(db: ToDoItemStorage): ToDoItemDao {
        return db.toDoItems()
    }
    @Provides
    fun provideDatabase(applicationContext: Context): ToDoItemStorage {
        return Room.databaseBuilder(
            applicationContext,
            ToDoItemStorage::class.java,
            "to_do_items.db").build()
    }
}