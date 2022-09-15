package com.example.myapp.fragmants

import android.content.Context
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.myapp.Common.Constants
import com.example.myapp.Common.Utility
import com.example.myapp.R
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.dbhelper.SurveyDataHelper
import com.example.myapp.listner.OnFragmentChangeListener
import com.example.myapp.model.SurveyDataModel
import com.google.android.material.tabs.TabLayout
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.nio.channels.FileChannel
import java.util.ArrayList

class MainFragment : Fragment {

    private lateinit var activityMainBinding: ActivityMainBinding
    var viewPager: ViewPager? = null
    var toolbar: ActionBar? = null
    var fragments: ArrayList<Fragment>? = null
    private var mActivity: FragmentActivity? = null
    private var bundle: Bundle? = null
    var unique_id: String? = null
    private var surveyDataModel: SurveyDataModel? = null

    constructor(
        activityMainBinding: ActivityMainBinding
    ) {
        this.activityMainBinding = activityMainBinding
    }


    constructor() {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_main, container, false)
        val tabLayout = view.findViewById<View>(R.id.tabs) as TabLayout


        exportDB()

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        viewPager = view.findViewById<View>(R.id.viewpager) as ViewPager
        tabLayout.addTab(tabLayout.newTab().setText(" A"))
        tabLayout.addTab(tabLayout.newTab().setText(" B "))
        tabLayout.addTab(tabLayout.newTab().setText(" C "))
        tabLayout.addTab(tabLayout.newTab().setText(" D "))
        bundle = arguments

        unique_id = bundle?.getString("UNIQUE_ID")!!
        surveyDataModel = SurveyDataHelper.getByUniqueId(unique_id!!, mActivity)

        if (surveyDataModel == null) {
            surveyDataModel = SurveyDataModel()
        }

        surveyDataModel!!.LOGIN_ID= Utility.getStringPreference(Constants.LOGIN_ID,mActivity)
        surveyDataModel!!.STATE_NAME= Utility.getStringPreference(Constants.STATE_NAME,mActivity)
        surveyDataModel!!.ZONE_NAME= Utility.getStringPreference(Constants.ZONE_NAME,mActivity)
        surveyDataModel!!.ZONE_ID= Utility.getIntPreference(Constants.ZONE_CODE,mActivity).toString()
        surveyDataModel!!.ULB_NAME= Utility.getStringPreference(Constants.ULB_NAME,mActivity)
        surveyDataModel!!.ULB_ID= Utility.getIntPreference(Constants.ULB_CODE,mActivity).toString()
        surveyDataModel!!.DIST_NAME= Utility.getStringPreference(Constants.DIST_NAME,mActivity)
        surveyDataModel!!.DIST_ID= Utility.getIntPreference(Constants.DIST_CODE,mActivity).toString()
        surveyDataModel!!.WARD_NAME= Utility.getStringPreference(Constants.WARD_NAME,mActivity)
        surveyDataModel!!.WARD_ID= Utility.getIntPreference(Constants.WARD_CODE,mActivity).toString()

        activityMainBinding.appBarHome.toolbarText.setText("Nagar Nigam - " + surveyDataModel?.ULB_NAME)
        // change here of utility type like Electricity, Gas, Water
        fragments = ArrayList()
        val fragment1: Fragment = TabOneFragment(onFragmentChange, activityMainBinding!!)
        fragment1.arguments = bundle
        val fragment2: Fragment = TabTwoFragment(onFragmentChange, activityMainBinding!!)
        fragment2.arguments = bundle
        val fragment3: Fragment = TabThreeFragment(onFragmentChange, activityMainBinding!!)
        fragment3.arguments = bundle
        val fragment4: Fragment = TabFourFragment(onFragmentChange, activityMainBinding!!)
        fragment4.arguments = bundle
        fragments!!.add(fragment1)
        fragments!!.add(fragment2)
        fragments!!.add(fragment3)
        fragments!!.add(fragment4)
        viewPager!!.offscreenPageLimit = 4

        viewPager?.currentItem = 1
        val pagerAdapter = PagerAdapter(
            childFragmentManager, tabLayout.tabCount
        )
        viewPager!!.adapter = pagerAdapter
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        if (bundle != null) {
            try {
            } catch (e: Exception) {
                e.message
            }
            val pageId = bundle!!.getInt("pageId")
            viewPager!!.currentItem = pageId
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        pagerAdapter.notifyDataSetChanged()


        return view
    }

    private fun exportDB() {
        val sd = Environment.getExternalStorageDirectory()
        val data = Environment.getDataDirectory()
        var source: FileChannel? = null
        var destination: FileChannel? = null
        val currentDBPath = "/data/com.example.myapp/databases/survey.db"
        val backupDBPath = "survey.db"
        val currentDB = File(data, currentDBPath)
        val backupDB = File(sd, backupDBPath)
        try {
            source = FileInputStream(currentDB).channel
            destination = FileOutputStream(backupDB).channel
            destination.transferFrom(source, 0, source.size())
            source.close()
            destination.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        mActivity = context as FragmentActivity
    }

    private val onFragmentChange =
        OnFragmentChangeListener { position -> viewPager!!.currentItem = position }

    inner class PagerAdapter(fm: FragmentManager?, var mNumOfTabs: Int) : FragmentStatePagerAdapter(
        fm!!
    ) {
        override fun getItem(position: Int): Fragment {
            return fragments!![position]
        }

        override fun getCount(): Int {
            return mNumOfTabs
        }
    }



}