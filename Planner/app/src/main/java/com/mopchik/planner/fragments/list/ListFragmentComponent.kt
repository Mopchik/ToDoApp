package com.mopchik.planner.fragments.list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mopchik.planner.R

class ListFragmentComponent(fragmentView: View) {
    val howManyDoneTextView:TextView = fragmentView.findViewById(R.id.completedTextView)
    val visibilityImageView:ImageView = fragmentView.findViewById(R.id.visibilityImageView)
    val fab:FloatingActionButton = fragmentView.findViewById(R.id.fab)
    val recycler:RecyclerView = fragmentView.findViewById(R.id.recycler)
}