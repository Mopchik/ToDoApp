package com.mopchik.planner.fragments.list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mopchik.planner.ListFragmentViewScope
import com.mopchik.planner.R
import javax.inject.Inject

@ListFragmentViewScope
class ListFragmentBinding @Inject constructor(fragmentView: View) {
    val howManyDoneTextView: TextView
    val visibilityImageView: ImageView
    val fab: FloatingActionButton
    val recycler: RecyclerView
    init {
        howManyDoneTextView = fragmentView.findViewById(R.id.completedTextView)
        visibilityImageView = fragmentView.findViewById(R.id.visibilityImageView)
        fab = fragmentView.findViewById(R.id.fab)
        recycler = fragmentView.findViewById(R.id.recycler)
    }
}