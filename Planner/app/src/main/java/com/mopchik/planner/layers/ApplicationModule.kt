package com.mopchik.planner.layers

import com.mopchik.planner.data_worker.data_base.DataBaseModule
import dagger.Module

@Module(includes = [DataBaseModule::class])
object ApplicationModule