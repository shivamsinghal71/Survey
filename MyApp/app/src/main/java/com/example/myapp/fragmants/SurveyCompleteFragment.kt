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
import com.example.myapp.Common.Utility
import com.example.myapp.activities.MainActivity
import com.example.myapp.R
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.databinding.FragmentSurveyCompleteBinding
import com.example.myapp.model.UserInfoModel
import com.inventia.ugo_mici.dbhelper.UserDataHelper
import java.util.*

class SurveyCompleteFragment : Fragment ,View.OnClickListener{



    private var activityMainBinding: ActivityMainBinding?=null
    private lateinit var fragmentSurveyCompleteBinding: FragmentSurveyCompleteBinding
    private var mActivity: FragmentActivity? = null

    constructor(activityMainBinding: ActivityMainBinding)
    {
        this.activityMainBinding=activityMainBinding
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSurveyCompleteBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_survey_complete, container, false)
        return fragmentSurveyCompleteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        fragmentSurveyCompleteBinding.btnDone.setOnClickListener(this)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as FragmentActivity
    }


    override fun onClick(p0: View?) {
        when (p0?.id)
        {
            R.id.btnDone ->
            {

                val model: UserInfoModel? = UserDataHelper.getLogin(mActivity)
                if (model != null) {


                    var dateTime = Utility.dateTimeToString(Date())
                    var dateTimeArray = dateTime!!.split("-")

                    val random1 =
                        model.MOBILE_NO + "_" + dateTimeArray[0] + "_" + dateTimeArray[1] + "_" + dateTimeArray[2] + "_" + dateTimeArray[3] + "_" + dateTimeArray[4] + "_" + dateTimeArray[5]

                    Utility.clearAllFragment(mActivity!!)
                    val bundle = Bundle()
                    bundle.putString("UNIQUE_ID", random1.toString())
                    Utility.replaceFragmentWithBundle(
                        MainFragment(activityMainBinding!!),
                        mActivity!!.supportFragmentManager,
                        R.id.layout_fragment,
                        bundle
                    )
                }

            }
        }
    }

}