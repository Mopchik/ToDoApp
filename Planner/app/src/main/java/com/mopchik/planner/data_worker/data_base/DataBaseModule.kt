package com.mopchik.planner.data_worker.data_base

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides

@Module
class DataBaseModule {
    @Provides
    fun provideDataBase(applicationContext: Context): ToDoItemStorage {
        return Room.databaseBuilder(
            applicationContext.applicationContext,
            ToDoItemStorage::class.java,
            "to_do_items.db").build()
    }

    @Provides
    fun provideToDoItemDao(db: ToDoItemStorage): ToDoItemDao {
        return db.toDoItems()
    }
}