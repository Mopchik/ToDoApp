package com.mopchik.planner.data_worker_todoitems

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioGroup
import androidx.lifecycle.LifecycleOwner
import com.mopchik.planner.*


class ToDoItemAdapter(lifeCycleOwner:LifecycleOwner,
                      private val viewModel: ToDoItemViewModel,
                      private val onClick: OnItemClickedListener
):
    RecyclerView.Adapter<ToDoItemViewHolder>(),
    ToDoItemListUserChangeListener {
    private var arrOfToDoItems:List<ToDoItem> = ArrayList()
    init{
        viewModel.toDoItemsLiveData.observe(lifeCycleOwner){ newToDoItems ->
            arrOfToDoItems = newToDoItems
            notifyDataSetChanged()
        }
        viewModel.updateToDoItems()
    }

    override fun getItemCount(): Int {
        return arrOfToDoItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ToDoItemViewHolder(
            layoutInflater.inflate(R.layout.to_do_item, parent, false),
            onClick, viewModel
        )
    }

    override fun onBindViewHolder(holder: ToDoItemViewHolder, position: Int) {
        val item = arrOfToDoItems[position]
        holder.bind(item, position)
    }

    fun getItem(ind:Int):ToDoItem{
        return arrOfToDoItems[ind]
    }

    override fun onAddToDoItem(item: ToDoItem) {
        // arr.add(item)
        // notifyItemInserted(arrOfToDoItems.size)
        viewModel.onAddToDoItem(item)
    }

    override fun onDeleteToDoItem(ind: Int) {
        // arr.deleteItem(ind)
        // notifyItemRemoved(ind)
        // notifyItemRangeChanged(ind, itemCount)
        viewModel.onDeleteToDoItem(ind)
    }

    override fun onChangeToDoItem(newItem: ToDoItem, pos: Int) {
        // notifyItemChanged(pos)
        viewModel.onChangeToDoItem(newItem, pos)
    }

}