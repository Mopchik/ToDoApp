package com.mopchik.planner.fragments.list

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.mopchik.planner.*
import com.mopchik.planner.layers.App
import com.mopchik.planner.data_worker.*
import com.mopchik.planner.data_worker.ToDoItemStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ListFragment : Fragment() {

    private val applicationComponent
        get() = App.get(requireContext()).appComponent
    private val viewModel: ToDoItemViewModel by viewModels {
        applicationComponent.viewModelFactory }
    private lateinit var component: ListFragmentComponent
    private lateinit var adapterController: ListFragmentAdapterController
    private lateinit var communicator: ListAddChangeFragmentsCommunicator
    private lateinit var prefs: SharedPreferences
    // private lateinit var dataSaver: DataSaver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("KOSTIK", "Fragment created.")
        // Log.i("KOSTIK", "Created new database object.")
        // dataSaver = DataSaver(requireContext()
        //     .getSharedPreferences(APP_PREFERENCES,
        //                         Context.MODE_PRIVATE),
        //                         viewModel.viewModelScope)
        // val toDoItemsList = dataSaver.getToDoItemsList()
        // viewModel.viewModelScope.launch(Dispatchers.IO){
        //     val toDoItemsList = db.toDoItems().all()
        //     viewModel.setList(toDoItemsList)
        // }
        //if(toDoItemsList!=null)
        //    viewModel.setList(toDoItemsList)
        prefs = requireContext().getSharedPreferences(APP_PREFERENCES,
            Context.MODE_PRIVATE)
        val visibility = prefs.getBoolean("visibility", false)
        adapterController = ListFragmentAdapterController(
            startChangeFragment = {item -> communicator.startChangeFragment(item)},
            viewModel, this, visibility)
        // adapterController.adapter.showOnlyImportant = visibility
        communicator = ListAddChangeFragmentsCommunicator(viewModel,
                adapterController, parentFragmentManager/*, db.toDoItems()*/)
        setFragmentResultListener(REQUEST_KEY){ _, bundle ->
            communicator.getResultOnListFragmentAfterAddChangeFragment(bundle)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                          savedInstanceState: Bundle?): View {
        Log.i("KOSTIK", "Fragment view created.")
        val fragmentView = inflater.inflate(R.layout.list_fragment, container, false)
        component = ListFragmentComponent(fragmentView)
        adapterController.observeData(component)
        val listFragmentController = ListFragmentController(component, adapterController.adapter,
                    startAddFragment = {communicator.startAddFragment()}, requireContext())
        listFragmentController.setUpViews()
        return fragmentView
    }

    override fun onPause() {
        super.onPause()
        prefs.edit().putBoolean("visibility",
            adapterController.adapter.showOnlyImportant).apply()
        // dataSaver.saveToDoItemsList(adapterController.adapter.toDoItemsListInfo.toDoActualList)
        // Log.i("SAVING_DATA", "List was saved")
    }

}
