package com.mopchik.planner.fragments.list

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
import javax.inject.Inject


class ListFragment : Fragment() {

    private val app by lazy{ App.get(requireContext()) }
    private val viewModel: ToDoItemViewModel by viewModels {
        app.viewModelFactory }
    private val component by lazy{
        (requireActivity() as MainActivity)
            .component
            .listFragmentComponentFactory()
            .create(viewModel, this, parentFragmentManager, requireContext())
    }
    private lateinit var viewComponent: ListFragmentViewComponent
    @Inject
    lateinit var adapterController: ListFragmentAdapterController
    @Inject
    lateinit var communicator: ListAddChangeFragmentsCommunicator
    @Inject
    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        setFragmentResultListener(REQUEST_KEY){ _, bundle ->
            communicator.getResultOnListFragmentAfterAddChangeFragment(bundle)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                          savedInstanceState: Bundle?): View {
        val fragmentView = inflater.inflate(R.layout.list_fragment, container, false)
        viewComponent = component
            .viewComponentFactory()
            .create(fragmentView, startAddFragment = {communicator.startAddFragment()})
        adapterController.observeData(viewModel.toDoItemsLiveData,
            viewComponent.binding, this)
        viewComponent.controller.setUpViews()
        return fragmentView
    }

    override fun onPause() {
        super.onPause()
        prefs.edit()
             .putBoolean("visibility", adapterController.adapter.showOnlyImportant)
             .putBoolean("isNight", viewModel.isNight)
             .apply()
    }

}
