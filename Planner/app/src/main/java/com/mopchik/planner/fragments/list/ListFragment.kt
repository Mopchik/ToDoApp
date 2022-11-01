package com.mopchik.planner.fragments.list

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.mopchik.planner.*
import com.mopchik.planner.layers.App
import com.mopchik.planner.data_worker.*


class ListFragment : Fragment() {

    private val applicationComponent
        get() = App.get(requireContext()).applicationComponent
    private val viewModel: ToDoItemViewModel by viewModels {
        applicationComponent.viewModelFactory }
    private lateinit var component: ListFragmentComponent
    private lateinit var adapterController: ListFragmentAdapterController
    private lateinit var communicator: ListAddChangeFragmentsCommunicator
    private lateinit var prefs: SharedPreferences
    // private lateinit var dataSaver: DataSaver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        val showOnlyImportant = prefs.getBoolean("visibility", false)
        adapterController = ListFragmentAdapterController(
            startChangeFragment = {item -> communicator.startChangeFragment(item)},
            viewModel, this, showOnlyImportant)
        communicator = ListAddChangeFragmentsCommunicator(viewModel,
                adapterController, parentFragmentManager)
        setFragmentResultListener(REQUEST_KEY){ _, bundle ->
            communicator.getResultOnListFragmentAfterAddChangeFragment(bundle)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                          savedInstanceState: Bundle?): View {
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
        prefs.edit()
             .putBoolean("visibility", adapterController.adapter.showOnlyImportant)
             .apply()
    }

}
