package com.example.myapp.fragmants

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.Common.Utility
import com.example.myapp.R
import com.example.myapp.adapters.BhawanVargikaranAdapter
import com.example.myapp.adapters.VisitedListAdapter
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.databinding.FragmentVisitedDataBinding
import com.example.myapp.dbhelper.SurveyDataHelper
import com.example.myapp.model.SurveyDataModel

class VisitedDataFragment : Fragment ,View.OnClickListener {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var fragmentVisitedDataBinding : FragmentVisitedDataBinding
    private var mActivity: FragmentActivity? = null
    private lateinit var visitedListAdapter: VisitedListAdapter
    var surveyDataModel: ArrayList<SurveyDataModel>? = null


    constructor() {}
    constructor( activityMainBinding: ActivityMainBinding)
    {
        this.activityMainBinding=activityMainBinding
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentVisitedDataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_visited_data, container, false)
        return fragmentVisitedDataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        updateUI()

    }

    private fun updateUI()
    {
         surveyDataModel =SurveyDataHelper.getAll(1,0, mActivity)

        visitedListAdapter =
            VisitedListAdapter(surveyDataModel, mActivity!!)
        val layoutManager = LinearLayoutManager(mActivity)
        fragmentVisitedDataBinding.visitedListRec.layoutManager = layoutManager
        fragmentVisitedDataBinding.visitedListRec.itemAnimator = DefaultItemAnimator()
        fragmentVisitedDataBinding.visitedListRec.adapter = visitedListAdapter

        visitedListAdapter.setItemListener(propertyItemListener)
    }

    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        mActivity = context as FragmentActivity
    }


    override fun onClick(p0: View?) {

    }


    val propertyItemListener: VisitedListAdapter.PropertyItemListener =
        object : VisitedListAdapter.PropertyItemListener {
            override fun OnItemClick(position: Int) {
                val utils: SurveyDataModel? = surveyDataModel?.get(position)

                Utility.replaceFragment(VisitDetailsFragment(activityMainBinding ,
                    surveyDataModel?.get(position)!!.UNIQUE_ID.toString()
                ), mActivity!!.getSupportFragmentManager(), R.id.layout_fragment)

//                val intent = Intent()
//                intent.putExtra("UTILITY_NAME", utils.UTILITY_NAME)
//                intent.putExtra("UTILITY_CODE", utils.UTILITY_CODE)
//                setResult(10, intent)
//                finish()

            }

        }


}