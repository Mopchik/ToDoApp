package com.mopchik.planner.data_worker.data_base

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.mopchik.planner.APP_PREFERENCES
import com.mopchik.planner.AppScope
import com.mopchik.planner.ContextClass
import com.mopchik.planner.ContextOwner
import dagger.Module
import dagger.Provides

@Module
object DataBaseModule {
    @Provides
    @AppScope
    fun provideDataBase(@ContextClass(ContextOwner.APP)
                        applicationContext: Context): ToDoItemStorage {
        return Room.databaseBuilder(
            applicationContext,
            ToDoItemStorage::class.java,
            "to_do_items.db").build()
    }

    @Provides
    @AppScope
    fun provideToDoItemDao(db: ToDoItemStorage): ToDoItemDao {
        return db.toDoItems()
    }

    @Provides
    @AppScope
    fun provideSharedPreferences(@ContextClass(ContextOwner.APP)
                                 applicationContext: Context):SharedPreferences{
        return applicationContext.getSharedPreferences(
            APP_PREFERENCES,
            Context.MODE_PRIVATE)
    }
}