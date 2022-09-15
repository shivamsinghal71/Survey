package com.example.myapp.fragmants

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.myapp.Common.Constants
import com.example.myapp.Common.Utility
import com.example.myapp.R
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.databinding.FragmentTabOneBinding
import com.example.myapp.dbhelper.PropertyDetailsHelper
import com.example.myapp.dbhelper.SurveyDataHelper
import com.example.myapp.listner.OnFragmentChangeListener
import com.example.myapp.model.SurveyDataModel
import com.example.myapp.model.UserInfoModel
import com.inventia.ugo_mici.dbhelper.UserDataHelper
import java.util.*


class TabOneFragment : Fragment, View.OnClickListener {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var fragmentTabOneBinding: FragmentTabOneBinding
    private var onFragmentChange: OnFragmentChangeListener? = null
    private var mActivity: FragmentActivity? = null
    private var surveyDataModel: SurveyDataModel? = null
    private var bundle: Bundle? = null
    var unique_id: String? = null
    var parinam_id: Int = 0
    var maap_aadhar_id: Int = 0


    constructor() {}
    constructor(
        onFragmentChange: OnFragmentChangeListener,
        activityMainBinding: ActivityMainBinding
    ) {
        this.onFragmentChange = onFragmentChange
        this.activityMainBinding = activityMainBinding
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentTabOneBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_tab_one, container, false)
        return fragmentTabOneBinding.root

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentTabOneBinding.btnSaveInfo.setOnClickListener(this)
        fragmentTabOneBinding.nextDate.setOnClickListener(this)
        fragmentTabOneBinding.img1.setOnClickListener(this)
        fragmentTabOneBinding.img2.setOnClickListener(this)

        updateUI()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateUI() {
        bundle = arguments
        unique_id = bundle?.getString("UNIQUE_ID")!!
        surveyDataModel = SurveyDataHelper.getByUniqueId(unique_id!!, mActivity)
        if (surveyDataModel == null) {
            surveyDataModel = SurveyDataModel()
        }


        fragmentTabOneBinding.txtUniqueId.setOnFocusChangeListener(OnFocusChangeListener { view, b ->

            if (!Utility.isNullOrEmpty(fragmentTabOneBinding.txtUniqueId.text.toString())) {
                val model = PropertyDetailsHelper.getByUniqueId(
                    fragmentTabOneBinding.txtUniqueId.text.toString(),
                    mActivity
                )
                if (model!!.size > 0) {
                    fragmentTabOneBinding.txtSampattiPincode.setText(
                        Utility.getStringPreference(
                            Constants.PINCODE,
                            mActivity
                        )
                    )
                    fragmentTabOneBinding.txtSampattiSankhya.setText(model.get(0).PM_PROP_NO)
                    fragmentTabOneBinding.txtULBId.setText(model.get(0).PM_PROP_OLDPROPNO)
                    fragmentTabOneBinding.txtKhasraNo.setText(model.get(0).Kharsra_No)
                    fragmentTabOneBinding.txtSampattiAdress.setText(
                        model.get(0).Property_Location_Address + ", " + model.get(
                            0
                        ).Location
                    )
                    fragmentTabOneBinding.txtSampattiLandmark.setText(model.get(0).LANDMARK)
                    fragmentTabOneBinding.txtMobile.setText(model.get(0).Mobile_Number)
                }
            }
        })


//        val tomorrow = LocalDate.now().plus(1, ChronoUnit.DAYS)
//        val formattedTomorrow = tomorrow.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
//        fragmentTabOneBinding.txtNextDate.setText(formattedTomorrow)
        bindPrainamCode()
        bindMaapAdhar()
    }

    private fun bindMaapAdhar() {
        val maapAadharList = ArrayList<String>()
        maapAadharList.add("select")
        maapAadharList.add("वास्तविक माप")
        maapAadharList.add("अनुमानित माप")

        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, maapAadharList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentTabOneBinding.spinnerMaapAadhar.adapter = adapter

        fragmentTabOneBinding.spinnerMaapAadhar.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    maap_aadhar_id = position
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

    }


    private fun bindPrainamCode() {
        val prainamCodeList = ArrayList<String>()
        prainamCodeList.add("select")
        prainamCodeList.add("पूर्ण")
        prainamCodeList.add("स्थगित")
        prainamCodeList.add("अपूर्ण")
        prainamCodeList.add("घर पर उपलब्ध नही/लॉक")
        prainamCodeList.add("मना किया")
        prainamCodeList.add("खाली किया")
        prainamCodeList.add("परमानेंट लॉक")
        prainamCodeList.add("अन्य")

        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, prainamCodeList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentTabOneBinding.spinnerParinamCode.adapter = adapter

        fragmentTabOneBinding.spinnerParinamCode.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    parinam_id = position
                    if (parent.selectedItem.toString()
                            .equals("पूर्ण") || parent.selectedItem.toString().equals("select")
                    ) {
                        fragmentTabOneBinding.lytNextDate.visibility = View.GONE
                    } else {
                        fragmentTabOneBinding.lytNextDate.visibility = View.VISIBLE
                    }

                    if (parent.selectedItem.toString().equals("अन्य")) {
                        fragmentTabOneBinding.lytParinamCodeOther.visibility = View.VISIBLE
                    } else {
                        fragmentTabOneBinding.lytParinamCodeOther.visibility = View.GONE
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as FragmentActivity
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnSaveInfo -> {

                if (validateTabOne())
                    saveSurveyInfo()
            }
            R.id.nextDate -> {
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)


                val dpd = DatePickerDialog(
                    mActivity!!,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                        val dateString =
                            String.format("%02d-%02d-%d", dayOfMonth, monthOfYear + 1, year)
                        fragmentTabOneBinding.txtNextDate.setText(dateString)

                    },
                    year,
                    month,
                    day
                )

                dpd.show()
            }
            R.id.img1 -> {
                fragmentTabOneBinding.spinnerParinamCode.performClick()
            }
            R.id.img2 -> {
                fragmentTabOneBinding.spinnerMaapAadhar.performClick()
            }


        }
    }

    private fun validateTabOne(): Boolean {

        if (Utility.isNullOrEmpty(fragmentTabOneBinding.txtGridNo.text.toString())) {

            Utility.snackBar(
                fragmentTabOneBinding.layoutRoot,
                requireActivity().resources.getString(R.string.enter_grid_no),
                1200,
                requireActivity().resources.getColor(R.color.warning)
            )
            fragmentTabOneBinding.txtGridNo.requestFocus()
            return false
        } else if (Utility.isNullOrEmpty(fragmentTabOneBinding.txtMapId.text.toString())) {

            Utility.snackBar(
                fragmentTabOneBinding.layoutRoot,
                requireActivity().resources.getString(R.string.enter_map_id),
                1200,
                requireActivity().resources.getColor(R.color.warning)
            )
            fragmentTabOneBinding.txtMapId.requestFocus()
            return false
        } else if (Utility.isNullOrEmpty(fragmentTabOneBinding.txtMobile.text.toString())) {

            fragmentTabOneBinding.txtMobile.requestFocus()
            Utility.snackBar(
                fragmentTabOneBinding.layoutRoot,
                requireActivity().resources.getString(R.string.enter_mobile_no),
                1200,
                requireActivity().resources.getColor(R.color.warning)
            )
            return false

        } else if (!Utility.isValidMobile(
                fragmentTabOneBinding.layoutRoot,
                fragmentTabOneBinding.txtMobile.text.toString()
            )
        ) {
            fragmentTabOneBinding.txtMobile.requestFocus()
            Utility.snackBar(
                fragmentTabOneBinding.layoutRoot,
                requireActivity().resources.getString(R.string.correct_mobile_no),
                1200,
                requireActivity().resources.getColor(R.color.warning)
            )
            return false
        } else if (!Utility.isNullOrEmpty(fragmentTabOneBinding.txtSampattiPincode.text.toString())) {
            if (!Utility.isValidPinCode(
                    fragmentTabOneBinding.layoutRoot,
                    fragmentTabOneBinding.txtSampattiPincode.text.toString()
                )
            ) {
                Utility.snackBar(
                    fragmentTabOneBinding.layoutRoot,
                    requireActivity().resources.getString(R.string.enter_correct_pincode),
                    1200,
                    requireActivity().resources.getColor(R.color.warning)
                )
                fragmentTabOneBinding.txtSampattiPincode.requestFocus()
                return false
            }
        } else if (Utility.isNullOrEmpty(fragmentTabOneBinding.txtParcelName.text.toString())) {

            Utility.snackBar(
                fragmentTabOneBinding.layoutRoot,
                requireActivity().resources.getString(R.string.enter_parcel_no),
                1200,
                requireActivity().resources.getColor(R.color.warning)
            )
            fragmentTabOneBinding.txtParcelName.requestFocus()
            return false
        } else if (Utility.isNullOrEmpty(fragmentTabOneBinding.txtSampattiSankhya.text.toString())) {

            Utility.snackBar(
                fragmentTabOneBinding.layoutRoot,
                requireActivity().resources.getString(R.string.enter_property_no),
                1200,
                requireActivity().resources.getColor(R.color.warning)
            )
            fragmentTabOneBinding.txtSampattiSankhya.requestFocus()
            return false
        }

        return true
    }

    private fun saveSurveyInfo() {
        surveyDataModel?.MOBILE_NO = fragmentTabOneBinding.txtMobile.text.toString()
        surveyDataModel?.PARINAM_NAME =
            fragmentTabOneBinding.spinnerParinamCode.selectedItem.toString()
        surveyDataModel?.PARINAM_ID = parinam_id.toString()
        surveyDataModel?.MAAP_AADHAR_NAME =
            fragmentTabOneBinding.spinnerMaapAadhar.selectedItem.toString()
        surveyDataModel?.MAAP_AADHAR_ID = maap_aadhar_id.toString()
        surveyDataModel?.PARINAM_NAME_OTHER =
            fragmentTabOneBinding.txtParinamCodeOther.text.toString()
        surveyDataModel?.NEXT_DATE = fragmentTabOneBinding.txtNextDate.text.toString()
        surveyDataModel?.PARCEL_NAME = fragmentTabOneBinding.txtParcelName.text.toString()
        surveyDataModel?.PARCEL_NOT_IN_MAP =
            fragmentTabOneBinding.txtParcelNameIfNotMap.text.toString()
        surveyDataModel?.PROPERTY_NO = fragmentTabOneBinding.txtSampattiSankhya.text.toString()
        surveyDataModel?.PROPERTY_ADDRESS = fragmentTabOneBinding.txtSampattiAdress.text.toString()
        surveyDataModel?.ROAD_STREET_NO =
            fragmentTabOneBinding.txtSampattiRoadKramank.text.toString()
        surveyDataModel?.ROAD_STREET_NAME =
            fragmentTabOneBinding.txtSampattiRoadName.text.toString()
        surveyDataModel?.COLONY_NAME = fragmentTabOneBinding.txtSampattiMohlla.text.toString()
        surveyDataModel?.STATION_NAME = fragmentTabOneBinding.txtSampattiThana.text.toString()
        surveyDataModel?.LANDMARK_NAME = fragmentTabOneBinding.txtSampattiLandmark.text.toString()
        surveyDataModel?.PINCODE = fragmentTabOneBinding.txtSampattiPincode.text.toString()
        surveyDataModel?.LAST_PAYMENT_YEAR_SURVEYOR =
            fragmentTabOneBinding.txtPayYearSurveyor.text.toString()
        surveyDataModel?.LAST_PAYMENT_YEAR_CORP =
            fragmentTabOneBinding.txtLastYearPayCorp.text.toString()
        surveyDataModel?.LAST_PAYMENT_DATE_SURVEYOR =
            fragmentTabOneBinding.txtPayDateSurveyor.text.toString()
        surveyDataModel?.LAST_PAYMENT_DATE_CORP =
            fragmentTabOneBinding.txtPayDateCorp.text.toString()
        surveyDataModel?.RECIEPT_NO_SURVEYOR =
            fragmentTabOneBinding.txtRecieptNoSurveyor.text.toString()
        surveyDataModel?.RECIEPT_NO_CORP = fragmentTabOneBinding.txtRecieptNoCorp.text.toString()
        surveyDataModel?.PAID_TAX_AMOUNT = fragmentTabOneBinding.txtPaidTextAmount.text.toString()
        surveyDataModel?.GRID_NO = fragmentTabOneBinding.txtGridNo.text.toString()
        surveyDataModel?.MAP_ID = fragmentTabOneBinding.txtMapId.text.toString()
        surveyDataModel?.UNIQUE_ID_TEXT = fragmentTabOneBinding.txtUniqueId.text.toString()
        surveyDataModel?.ULB_P_ID = fragmentTabOneBinding.txtULBId.text.toString()
        surveyDataModel?.KHASRA_NO = fragmentTabOneBinding.txtKhasraNo.text.toString()
        surveyDataModel?.ISVISITED = false
        surveyDataModel?.UNIQUE_ID = unique_id
        val isSave = SurveyDataHelper.saveSuvreyData(surveyDataModel, mActivity)

        if (isSave)
            this.onFragmentChange?.onFragmentChange(1)
    }

}
