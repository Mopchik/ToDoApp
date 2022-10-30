package com.mopchik.planner.fragments.add_change

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.mopchik.planner.*



class AddChangeFragment: Fragment() {

    private lateinit var component: AddChangeFragmentComponent

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View{
        val isCreatingNew = requireArguments().getBoolean(CREATING_KEY)
        val fragmentView = inflater.inflate(R.layout.add_fragment, container, false)
        component = AddChangeFragmentComponent(fragmentView, isCreatingNew)
        val controller = AddChangeFragmentController(arguments, component, requireContext(),
                saveOptions={bundle->saveOptions(bundle)},
                goBack={goBack()})
        controller.setUpViews()
        return fragmentView
    }

    private fun goBack(){
        requireActivity().onBackPressed()
    }

    private fun saveOptions(bundle: Bundle){
        setFragmentResult(REQUEST_KEY, bundle)
    }

    companion object{
        fun newInstance(description: String, importanceId:Int, isCreatingNew: Boolean,
                        day: Int?, month: Int?, year:Int?): AddChangeFragment {
            val addFragment = AddChangeFragment()
            addFragment.arguments = Bundle().apply {
                putString(DESCRIPTION_KEY, description)
                putInt(IMPORTANCE_KEY, importanceId)
                putBoolean(CREATING_KEY, isCreatingNew)
                putInt(DAY_KEY, day ?: -1)
                putInt(MONTH_KEY, month ?: -1)
                putInt(YEAR_KEY, year ?: -1)
            }
            return addFragment
        }
    }
}