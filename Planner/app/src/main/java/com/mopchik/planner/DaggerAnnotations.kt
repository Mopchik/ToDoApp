package com.mopchik.planner

import javax.inject.Qualifier
import javax.inject.Scope

@Scope
annotation class AppScope

@Scope
annotation class AddChangeFragmentViewScope

@Scope
annotation class ListFragmentScope

@Scope
annotation class ListFragmentViewScope

enum class ContextOwner{
    APP, LIST_FRAGMENT, ADD_CHANGE_FRAGMENT
}
@Qualifier
annotation class ContextClass(val cl: ContextOwner)

enum class TypeOfAction{
    GO_BACK, START_ADD_FRAGMENT
}
@Qualifier
annotation class ActionParameterType(val tOfA:TypeOfAction)

enum class FragmentManagerOwner{
    ADD_CHANGE_FRAGMENT
}
@Qualifier
annotation class FragmentManagerClass(val fm: FragmentManagerOwner)