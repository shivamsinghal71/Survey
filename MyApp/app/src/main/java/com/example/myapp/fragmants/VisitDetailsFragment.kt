package com.example.myapp.fragmants

import android.content.Context
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
import com.example.myapp.adapters.VisitedListAdapter
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.databinding.FragmentTabFourBinding
import com.example.myapp.databinding.FragmentVisitDetailsBinding
import com.example.myapp.dbhelper.SurveyDataHelper
import com.example.myapp.listner.OnFragmentChangeListener
import com.example.myapp.model.SurveyDataModel

class VisitDetailsFragment : Fragment ,View.OnClickListener {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var fragmentVisitDetailsBinding: FragmentVisitDetailsBinding
    private var onFragmentChange: OnFragmentChangeListener? = null
    private var mActivity: FragmentActivity? = null
    private var surveyDataModel: SurveyDataModel? = null
    var unique_id =""

    constructor()
    {
    }

    constructor( activityMainBinding: ActivityMainBinding, unique_id: String)
    {
        this.activityMainBinding=activityMainBinding
        this.unique_id=unique_id
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentVisitDetailsBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_visit_details, container, false)

        return fragmentVisitDetailsBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        updateUI()

    }

    private fun updateUI()
    {
        surveyDataModel =SurveyDataHelper.getByCreatedUniqueNumber(unique_id, mActivity)


        if(surveyDataModel != null &&  surveyDataModel?.PROPERTY_IMAGE != null)
        {
            fragmentVisitDetailsBinding.propertyimageoriginal1.visibility = View.VISIBLE
            fragmentVisitDetailsBinding.propertyimageoriginal1.setImageBitmap(
                Utility.getBitmapByStringImage(
                    surveyDataModel?.PROPERTY_IMAGE
                )
            )
        }

        if (surveyDataModel != null &&  surveyDataModel?.SIGNATURE_IMAGE != null){
            fragmentVisitDetailsBinding.signatureimageoriginal2.visibility = View.VISIBLE
            fragmentVisitDetailsBinding.signatureimageoriginal2.setImageBitmap(
                Utility.getBitmapByStringImage(
                    surveyDataModel?.SIGNATURE_IMAGE
                )
            )
        }

       fragmentVisitDetailsBinding.txtGridNoPrev.setText(surveyDataModel?.GRID_NO)
       fragmentVisitDetailsBinding.txtMapIdPrev.setText(surveyDataModel?.MAP_ID)
       fragmentVisitDetailsBinding.txtUniqueIdPrev.setText(surveyDataModel?.UNIQUE_ID_TEXT)
       fragmentVisitDetailsBinding.txtULBPIdPrev.setText(surveyDataModel?.ULB_P_ID)
       fragmentVisitDetailsBinding.txtParinamCodePrev.setText(surveyDataModel?.PARINAM_NAME)
       fragmentVisitDetailsBinding.txtMaapAadharPrev.setText(surveyDataModel?.MAAP_AADHAR_NAME)
       fragmentVisitDetailsBinding.txtParcelPrev.setText(surveyDataModel?.PARCEL_NAME)
       fragmentVisitDetailsBinding.txtPropertyNoPrev.setText(surveyDataModel?.PROPERTY_NO)
       fragmentVisitDetailsBinding.txtKhasraNoPrev.setText(surveyDataModel?.KHASRA_NO)
       fragmentVisitDetailsBinding.txtPropertyAddPrev.setText(surveyDataModel?.PROPERTY_ADDRESS)
       fragmentVisitDetailsBinding.txtRespondentNamePrev.setText(surveyDataModel?.RESPONDENT_NAME)
       fragmentVisitDetailsBinding.txtMobileNoPrev.setText(surveyDataModel?.MOBILE_NO)
       fragmentVisitDetailsBinding.txtOwnerClasificationPrev.setText(surveyDataModel?.PROPERTY_OWNERSHIP_CLASSIFICATION)
       fragmentVisitDetailsBinding.txtBhumiPrakarPrev.setText(surveyDataModel?.LAND_TYPE)
       fragmentVisitDetailsBinding.txtEmailPrev.setText(surveyDataModel?.EMAIL)
       fragmentVisitDetailsBinding.txtPropertyUsesPrev.setText(surveyDataModel?.PROPERTY_USES)
       fragmentVisitDetailsBinding.txtBhawanNirmanYearPrev.setText(surveyDataModel?.CONSTRUCTION_YEAR)
       fragmentVisitDetailsBinding.txtBhumiAreaPrev.setText(surveyDataModel?.LAND_AREA)
       fragmentVisitDetailsBinding.txtWaterSourcePrev.setText(surveyDataModel?.WATER_SOURCE)
       fragmentVisitDetailsBinding.txtPipeThicknessPrev.setText(surveyDataModel?.PIPE_THICKNESS)
       fragmentVisitDetailsBinding.txtWaterSupplyHoursPrev.setText(surveyDataModel?.WATER_SUPPLY_HOURS)
       fragmentVisitDetailsBinding.txtPipePressurePrev.setText(surveyDataModel?.PIPE_PRESSURE



       )
    }

    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        mActivity = context as FragmentActivity
    }


    override fun onClick(p0: View?) {

    }


}