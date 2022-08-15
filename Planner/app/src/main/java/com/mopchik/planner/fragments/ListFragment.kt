package com.mopchik.planner.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mopchik.planner.*
import com.mopchik.planner.application_layer.App
import com.mopchik.planner.data_worker_todoitems.*
import java.lang.NullPointerException

import java.util.*

class ListFragment : Fragment(), OnItemClickedListener {

    private val applicationComponent
        get() = App.get(requireContext()).applicationComponent
    private val viewModel: ToDoItemViewModel by viewModels {
        applicationComponent.viewModelFactory }
    private lateinit var adapter:ToDoItemAdapter
    private var indOfChangingItem: Int = -1

    private fun changeToDoItem(bundle: Bundle, oldItem: ToDoItem): ToDoItem{
        val item = oldItem.copy()
        item.text = bundle.getString("description") ?: throw NullPointerException()
        val importanceId = bundle.getInt("importance", -1)
        item.importance = Importance.values()[importanceId]
        val day = bundle.getInt("day", -1)
        val month = bundle.getInt("month", -1)
        val year = bundle.getInt("year", -1)
        if(day != -1 && month != -1 && year != -1){
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            item.deadline = calendar
        } else {
            item.deadline = null
        }
        item.changeDate = Calendar.getInstance()
        return item
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = ToDoItemAdapter(this, viewModel, this)
        setFragmentResultListener(REQUEST_KEY){ requestKey, bundle ->
            val isDeleted = bundle.getInt(ACTION_KEY)
            when(Action.values()[isDeleted]) {
                Action.CREATE -> {
                    val toDoItem = ToDoItem((adapter.itemCount + 1).toString())
                    adapter.onAddToDoItem(changeToDoItem(bundle, toDoItem))
                }
                Action.CHANGE -> {
                    val toDoItem = adapter.getItem(indOfChangingItem)
                    adapter.onChangeToDoItem(
                        changeToDoItem(bundle, toDoItem), indOfChangingItem)
                }
                Action.DELETE -> {
                    adapter.onDeleteToDoItem(indOfChangingItem)
                }
            }
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                          savedInstanceState: Bundle?): View {
        val fragmentView = inflater.inflate(R.layout.list_fragment, container, false)

        val recycler = fragmentView.findViewById<RecyclerView>(R.id.recycler)
        recycler.adapter = adapter
        recycler.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter.notifyDataSetChanged()

        val fab = fragmentView.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
            val addFragment = AddFragment().apply{
                arguments = Bundle().apply {
                    putBoolean(CREATING_KEY, true)
                }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, addFragment)
                .addToBackStack(null)
                .commit()
        }
        return fragmentView
    }

    override fun onItemClicked(itemInd:Int) {
        val item = adapter.getItem(itemInd)
        indOfChangingItem = itemInd
        val changeFragment = AddFragment.newInstance(
            item.text, item.importance.ordinal,
            false, item.deadline?.get(Calendar.DAY_OF_MONTH),
            item.deadline?.get(Calendar.MONTH), item.deadline?.get(Calendar.YEAR)
        )
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, changeFragment)
            .addToBackStack(null)
            .commit()
    }

}
