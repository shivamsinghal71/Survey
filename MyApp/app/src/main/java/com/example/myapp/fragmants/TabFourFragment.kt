package com.example.myapp.fragmants

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.example.myapp.Common.Constants
import com.example.myapp.Common.Utility
import com.example.myapp.R
import com.example.myapp.activities.CameraActivity
import com.example.myapp.activities.SignatureActivity
import com.example.myapp.async.ImageAsync
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.databinding.FragmentTabFourBinding
import com.example.myapp.dbhelper.SurveyDataHelper
import com.example.myapp.listner.ImageCallbackListener
import com.example.myapp.listner.OnFragmentChangeListener
import com.example.myapp.listner.PhotoCompressedListener
import com.example.myapp.listner.SignatureCallbackListener
import com.example.myapp.model.SurveyDataModel
import java.io.File

class TabFourFragment : Fragment, View.OnClickListener  , ImageCallbackListener ,SignatureCallbackListener{
    private var alertDialog: AlertDialog?=null
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var fragmentTabFourBinding: FragmentTabFourBinding
    private var onFragmentChange: OnFragmentChangeListener? = null
    private var mActivity: FragmentActivity? = null
    private var surveyDataModel: SurveyDataModel? = null
    var dhara136Value = ""
    var atihasikDharoharValue = ""
    var jalPradayeValue = ""
    var rainWaterHarwestingValue = ""
    var malNikasiConnectionValue = ""
    var paniTankiValue = ""
    var bhawanAnugyaValue = ""
    private var bundle: Bundle? = null
    var unique_id: String? = null
    val REQUEST_CODE_CAMERA = 200
    val REQUEST_CODE_GALLERY = 201
    val REQUEST_PERMISSION_CAMERA = 203
    var water_source_id=0
    var discount_type_id=0
    var sewage_network_if_not_id=0
    var sewage_type_id=0
    var pipe_thickness_id=0
    var pipe_pressure_id=0
    var rain_water_uses_id=0
    var water_meter_condition_id=0
    var water_meter_id=0
    var image_file_path : File? = null
    var signature_file_path : File? = null


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
        fragmentTabFourBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_tab_four, container, false)
        return fragmentTabFourBinding.root

    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            bundle = arguments
            unique_id = bundle?.getString("UNIQUE_ID")!!
            surveyDataModel = SurveyDataHelper.getByUniqueId(unique_id!!, mActivity)
            CameraFragment.getImageCallback(this)

            SignatureFragment.getSignatureCallback(this)
            val ss=""
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentTabFourBinding.btnSaveInfo.setOnClickListener(this)
        fragmentTabFourBinding.firstpropertyimage.setOnClickListener(this)
        fragmentTabFourBinding.firstSignatureImage.setOnClickListener(this)
        fragmentTabFourBinding.img11.setOnClickListener(this)
        fragmentTabFourBinding.img12.setOnClickListener(this)
        fragmentTabFourBinding.img13.setOnClickListener(this)
        fragmentTabFourBinding.img14.setOnClickListener(this)
        fragmentTabFourBinding.img15.setOnClickListener(this)
        fragmentTabFourBinding.img16.setOnClickListener(this)
        fragmentTabFourBinding.img17.setOnClickListener(this)
        fragmentTabFourBinding.img18.setOnClickListener(this)
        fragmentTabFourBinding.img19.setOnClickListener(this)
        fragmentTabFourBinding.img20.setOnClickListener(this)
        fragmentTabFourBinding.img21.setOnClickListener(this)
        fragmentTabFourBinding.img22.setOnClickListener(this)
        updateUI(view)
    }

    private fun updateUI(view: View) {

        bundle = arguments
        unique_id = bundle?.getString("UNIQUE_ID")!!
        surveyDataModel = SurveyDataHelper.getByUniqueId(unique_id!!, mActivity)


        fragmentTabFourBinding.rgDiscount.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> // checkedId is the RadioButton selected
            val radio: RadioButton = view.findViewById(checkedId)

            if (radio.text.equals("है")) {
                fragmentTabFourBinding.lytDiscountType.visibility = View.VISIBLE
            } else {
                fragmentTabFourBinding.lytDiscountType.visibility = View.GONE
            }
        })

        fragmentTabFourBinding.rgAtihasikDharohar.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> // checkedId is the RadioButton selected
            val radio: RadioButton = view.findViewById(checkedId)
            atihasikDharoharValue = radio.text.toString()
        })

        fragmentTabFourBinding.rgJalPradaye.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> // checkedId is the RadioButton selected
            val radio: RadioButton = view.findViewById(checkedId)
            jalPradayeValue = radio.text.toString()
        })

        fragmentTabFourBinding.rgRainWaterHarwesting.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> // checkedId is the RadioButton selected
            val radio: RadioButton = view.findViewById(checkedId)
            rainWaterHarwestingValue = radio.text.toString()
        })

        fragmentTabFourBinding.rgMalNikasiConnection.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> // checkedId is the RadioButton selected
            val radio: RadioButton = view.findViewById(checkedId)
            malNikasiConnectionValue = radio.text.toString()
        })

        fragmentTabFourBinding.rgPaniTanki.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> // checkedId is the RadioButton selected
            val radio: RadioButton = view.findViewById(checkedId)
            paniTankiValue = radio.text.toString()
        })

        fragmentTabFourBinding.rgBhawanAnugya.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> // checkedId is the RadioButton selected
            val radio: RadioButton = view.findViewById(checkedId)
            bhawanAnugyaValue = radio.text.toString()
        })



        bindAvdhi()
        bindTwoWheeler()
        bindFourWheeler()
        bindChootPrakar()
        bindJalStrotra()
        bindPipeMotai()
        bindPressureCondition()
        bindWaterMeter()
        bindWaterMeterCondition()
        bindRainWaterUses()
        bindSewageManage()
        bindMalNikasiType()
    }


    private fun bindJalStrotra() {
        val jalStrotraList = ArrayList<String>()
        jalStrotraList.add("select")
        jalStrotraList.add("कुआँ")
        jalStrotraList.add("हेंडपम्प/बोर")
        jalStrotraList.add("नगर निगम जल")
        jalStrotraList.add("टैंकर")
        jalStrotraList.add("हाउसिंग बोर्ड")
        jalStrotraList.add("अन्य")

        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, jalStrotraList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentTabFourBinding.spinnerJalSrotra.adapter = adapter

        fragmentTabFourBinding.spinnerJalSrotra.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    water_source_id=position

                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }

            }
    }

    private fun bindSewageManage() {
        val sewageManageList = ArrayList<String>()
        sewageManageList.add("select")
        sewageManageList.add("खुले स्थान मे")
        sewageManageList.add("शोख्ता गड्ढा")
        sewageManageList.add("सत ही नाली मे प्रवाहित")

        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, sewageManageList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentTabFourBinding.spinnerSewageManage.adapter = adapter

        fragmentTabFourBinding.spinnerSewageManage.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    sewage_network_if_not_id=position

                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }

            }
    }

    private fun bindMalNikasiType() {
        val malNikasiTypeList = ArrayList<String>()
        malNikasiTypeList.add("select")
        malNikasiTypeList.add("सेपटिक टैंक")
        malNikasiTypeList.add("भूमिगत से जुड़ा है")
        malNikasiTypeList.add("सीधे यूजीडी से जुड़ा है")
        malNikasiTypeList.add("शोख्ता गड्ढा")
        malNikasiTypeList.add("खुली नाली")

        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, malNikasiTypeList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentTabFourBinding.spinnerMalNikasiType.adapter = adapter

        fragmentTabFourBinding.spinnerMalNikasiType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    sewage_type_id=position

                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }

            }
    }

    private fun bindPipeMotai() {
        val pipeMotaiList = ArrayList<String>()
        pipeMotaiList.add("select")
        pipeMotaiList.add(" 1/2'' ")
        pipeMotaiList.add(" 3/4'' ")
        pipeMotaiList.add(" 1'' ")
        pipeMotaiList.add(" 1.5'' ")
        pipeMotaiList.add(" 2'' ")

        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, pipeMotaiList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentTabFourBinding.spinnerPipeMotai.adapter = adapter

        fragmentTabFourBinding.spinnerPipeMotai.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    pipe_thickness_id=position

                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }

            }
    }

    private fun bindPressureCondition() {
        val pressureConditionList = ArrayList<String>()
        pressureConditionList.add("select")
        pressureConditionList.add("कम")
        pressureConditionList.add("सामान्य")
        pressureConditionList.add("अधिक")

        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, pressureConditionList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentTabFourBinding.spinnerPressureStithi.adapter = adapter

        fragmentTabFourBinding.spinnerPressureStithi.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    pipe_pressure_id=position

                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }

            }
    }


    private fun bindRainWaterUses() {
        val rainWaterUsesList = ArrayList<String>()
        rainWaterUsesList.add("select")
        rainWaterUsesList.add("रीचार्ज")
        rainWaterUsesList.add("रियूज")
        rainWaterUsesList.add("लेफ्ट टू ड्रेन")
        rainWaterUsesList.add("अन्य")

        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, rainWaterUsesList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentTabFourBinding.spinnerRainWaterUses.adapter = adapter

        fragmentTabFourBinding.spinnerRainWaterUses.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    rain_water_uses_id=position

                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }

            }
    }

    private fun bindWaterMeter() {
        val waterMeterList = ArrayList<String>()
        waterMeterList.add("select")
        waterMeterList.add("है")
        waterMeterList.add("नहीं है")

        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, waterMeterList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentTabFourBinding.spinnerWaterMeter.adapter = adapter

        fragmentTabFourBinding.spinnerWaterMeter.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    water_meter_id=position

                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }

            }
    }


    private fun bindWaterMeterCondition() {
        val bindWaterMeterCondition = ArrayList<String>()
        bindWaterMeterCondition.add("select")
        bindWaterMeterCondition.add("चालू है")
        bindWaterMeterCondition.add("चालू नहीं है")

        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, bindWaterMeterCondition
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentTabFourBinding.spinnerWaterMeterCondition.adapter = adapter

        fragmentTabFourBinding.spinnerWaterMeterCondition.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    water_meter_condition_id=position
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }

            }
    }


    private fun bindChootPrakar() {
        val chootPrakarList = ArrayList<String>()
        chootPrakarList.add("select")
        chootPrakarList.add("स्वतंत्रा सेनानी")
        chootPrakarList.add("भारतीय सेना से रिटायर्ड")
        chootPrakarList.add("मानसिक रूप से विकलांग")
        chootPrakarList.add("परितक्ता महिला")
        chootPrakarList.add("विधवा")
        chootPrakarList.add("अन्य")

        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, chootPrakarList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentTabFourBinding.spinnerchootPrakar.adapter = adapter

        fragmentTabFourBinding.spinnerchootPrakar.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    discount_type_id=position
                    if (parent.selectedItem.toString().equals("अन्य")) {
                        fragmentTabFourBinding.lytOtherChootPrakarName.visibility = View.VISIBLE
                    } else {
                        fragmentTabFourBinding.lytOtherChootPrakarName.visibility = View.GONE
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }

            }
    }


    private fun bindAvdhi() {
        val avdhiList = ArrayList<String>()
        avdhiList.add("0")
        avdhiList.add("1")
        avdhiList.add("2")
        avdhiList.add("3")
        avdhiList.add("4")
        avdhiList.add("5")
        avdhiList.add("6")
        avdhiList.add("7")
        avdhiList.add("8")
        avdhiList.add("9")
        avdhiList.add("10")
        avdhiList.add("11")
        avdhiList.add("12")
        avdhiList.add("13")
        avdhiList.add("14")
        avdhiList.add("15")
        avdhiList.add("16")
        avdhiList.add("17")
        avdhiList.add("18")
        avdhiList.add("19")
        avdhiList.add("20")
        avdhiList.add("21")
        avdhiList.add("22")
        avdhiList.add("23")
        avdhiList.add("24")

        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, avdhiList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentTabFourBinding.spinnerAvdhi.adapter = adapter

        fragmentTabFourBinding.spinnerAvdhi.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
    }


    private fun bindTwoWheeler() {
        val chootPrakarList = ArrayList<String>()
        chootPrakarList.add("0")
        chootPrakarList.add("1")
        chootPrakarList.add("2")
        chootPrakarList.add("3")
        chootPrakarList.add("4")
        chootPrakarList.add("5")

        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, chootPrakarList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentTabFourBinding.spinnerTwoWheeler.adapter = adapter

        fragmentTabFourBinding.spinnerTwoWheeler.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                }

                override fun onNothingSelected(parent: AdapterView<*>) {


            }
            }
    }

    private fun bindFourWheeler() {
        val chootPrakarList = ArrayList<String>()
        chootPrakarList.add("0")
        chootPrakarList.add("1")
        chootPrakarList.add("2")
        chootPrakarList.add("3")
        chootPrakarList.add("4")
        chootPrakarList.add("5")

        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, chootPrakarList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentTabFourBinding.spinnerFourWheeler.adapter = adapter

        fragmentTabFourBinding.spinnerFourWheeler.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

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

                val builder = AlertDialog.Builder(mActivity!!)

                //set message for alert dialog
                builder.setMessage("क्या आप डेटा सेव करना चाहते हैं ?")
                builder.setIcon(android.R.drawable.ic_dialog_alert)

                //performing positive action
                builder.setPositiveButton("हाँ"){dialogInterface, which ->
                    if (validateTabFour()) {
                        saveSurveyInfo()
                    }
                }
                builder.setNegativeButton("नहींं"){dialogInterface, which ->
                    alertDialog!!.dismiss()
                }
                // Create the AlertDialog
                alertDialog = builder.create()
                // Set other dialog properties
                alertDialog!!.setCancelable(false)
                alertDialog!!.show()

            }

            R.id.firstpropertyimage -> {

                checkCameraPermission()

            }

            R.id.firstSignatureImage -> {

                val intent = Intent (mActivity, SignatureActivity()::class.java)
                intent.putExtra("unique_id" , unique_id)
                mActivity!!.startActivity(intent)

//                Utility.replaceFragment(
//                    SignatureFragment(activityMainBinding, unique_id),
//                    mActivity!!.supportFragmentManager,
//                    R.id.layout_fragment
//                )

            }


            R.id.img11 -> {
                fragmentTabFourBinding.spinnerchootPrakar.performClick()
            }

            R.id.img12 -> {
                fragmentTabFourBinding.spinnerJalSrotra.performClick()
            }

            R.id.img13 -> {
                fragmentTabFourBinding.spinnerPipeMotai.performClick()
            }
            R.id.img14 -> {
                fragmentTabFourBinding.spinnerAvdhi.performClick()
            }
            R.id.img15 -> {
                fragmentTabFourBinding.spinnerPressureStithi.performClick()
            }
            R.id.img16 -> {
                fragmentTabFourBinding.spinnerWaterMeter.performClick()
            }
            R.id.img17 -> {
                fragmentTabFourBinding.spinnerWaterMeterCondition.performClick()
            }
            R.id.img18 -> {
                fragmentTabFourBinding.spinnerRainWaterUses.performClick()
            }
            R.id.img19 -> {
                fragmentTabFourBinding.spinnerSewageManage.performClick()
            }
            R.id.img20 -> {
                fragmentTabFourBinding.spinnerMalNikasiType.performClick()
            }
            R.id.img21 -> {
                fragmentTabFourBinding.spinnerTwoWheeler.performClick()
            }
            R.id.img22 -> {
                fragmentTabFourBinding.spinnerFourWheeler.performClick()
            }
        }
    }

    private fun validateTabFour(): Boolean {

        if (Utility.isNullOrEmpty(surveyDataModel?.GRID_NO)) {

            Utility.snackBar(
                fragmentTabFourBinding.layoutRoot,
                requireActivity().resources.getString(R.string.enter_grid_no),
                1200,
                requireActivity().resources.getColor(R.color.warning)
            )
            return false
        }
        if (Utility.isNullOrEmpty(surveyDataModel?.MAP_ID)) {

            Utility.snackBar(
                fragmentTabFourBinding.layoutRoot,
                requireActivity().resources.getString(R.string.enter_map_id),
                1200,
                requireActivity().resources.getColor(R.color.warning)
            )
            return false
        }
        if (Utility.isNullOrEmpty(surveyDataModel?.MOBILE_NO)) {

            Utility.snackBar(
                fragmentTabFourBinding.layoutRoot,
                requireActivity().resources.getString(R.string.enter_mobile_no),
                1200,
                requireActivity().resources.getColor(R.color.warning)
            )
            return false

        }
        if (!Utility.isValidMobile(
                fragmentTabFourBinding.layoutRoot,
                surveyDataModel?.MOBILE_NO.toString()
            )
        ) {
            Utility.snackBar(
                fragmentTabFourBinding.layoutRoot,
                requireActivity().resources.getString(R.string.correct_mobile_no),
                1200,
                requireActivity().resources.getColor(R.color.warning)
            )
            return false
        }
        if (!Utility.isNullOrEmpty(surveyDataModel?.PINCODE)) {
            if (!Utility.isValidPinCode(
                    fragmentTabFourBinding.layoutRoot,surveyDataModel?.PINCODE.toString()
                )
            ) {
                Utility.snackBar(
                    fragmentTabFourBinding.layoutRoot,
                    requireActivity().resources.getString(R.string.enter_correct_pincode),
                    1200,
                    requireActivity().resources.getColor(R.color.warning)
                )
                return false
            }
        }
        if (Utility.isNullOrEmpty(surveyDataModel?.PARCEL_NAME)) {

            Utility.snackBar(
                fragmentTabFourBinding.layoutRoot,
                requireActivity().resources.getString(R.string.enter_parcel_no),
                1200,
                requireActivity().resources.getColor(R.color.warning)
            )
            return false
        }
        if (Utility.isNullOrEmpty(surveyDataModel?.PROPERTY_NO)) {

            Utility.snackBar(
                fragmentTabFourBinding.layoutRoot,
                requireActivity().resources.getString(R.string.enter_property_no),
                1200,
                requireActivity().resources.getColor(R.color.warning)
            )
            return false
        }

        if (!Utility.isNullOrEmpty(surveyDataModel?.PINCODE2)) {
            if (!Utility.isValidPinCode(
                    fragmentTabFourBinding.layoutRoot,surveyDataModel?.PINCODE2.toString()
                )
            ) {
                Utility.snackBar( fragmentTabFourBinding.layoutRoot, requireActivity().resources.getString(R.string.enter_correct_pincode),1200, requireActivity().resources.getColor(R.color.warning))
                return false
            }
        }
        if (!Utility.isNullOrEmpty(surveyDataModel?.EMAIL)) {
            if (!Utility.isValidMail(
                    fragmentTabFourBinding.layoutRoot,
                    surveyDataModel?.EMAIL.toString()
                )
            ) {
                Utility.snackBar( fragmentTabFourBinding.layoutRoot, requireActivity().resources.getString(R.string.enter_correct_email),1200, requireActivity().resources.getColor(R.color.warning))
                return false
            }
        }

        if (Utility.isNullOrEmpty(surveyDataModel?.PROPERTY_IMAGE))
        {
            Utility.snackBar(fragmentTabFourBinding.layoutRoot, "पहले इमेज क्लिक करें",1200, requireActivity().resources.getColor(R.color.warning))
            return false
        }

        if (Utility.isNullOrEmpty(surveyDataModel?.SIGNATURE_IMAGE))
        {
            Utility.snackBar(fragmentTabFourBinding.layoutRoot, "पहले हस्ताक्षर करें",1200, requireActivity().resources.getColor(R.color.warning))
            return false
        }
        return true
    }


    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                mActivity!!,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED

        ) {
            // Open Camera Intent
            CameraFragment.bothlensFacing = true
            val intent = Intent(activity, CameraActivity::class.java)
            intent.putExtra("pageAction", true)
            startActivity(intent)

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity!!,
                    android.Manifest.permission.CAMERA
                )
            ) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.CAMERA),
                    REQUEST_PERMISSION_CAMERA
                )
            } else {
                requestPermissions(
                    arrayOf(android.Manifest.permission.CAMERA),
                    REQUEST_PERMISSION_CAMERA
                )
            }
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_CAMERA) {
            if (data != null && data.extras != null) {
                //showImageData(resultCode, data)
            }
        }

        if (requestCode == REQUEST_CODE_GALLERY) {
            if (data != null && data.data != null) {
              //  showImageData(resultCode, data)
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_PERMISSION_CAMERA -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkCameraPermission()
            }


        }
    }


    private fun saveSurveyInfo() {
        surveyDataModel?.DISCOUNT = dhara136Value
        surveyDataModel?.HISTORIC_PROPERTY = atihasikDharoharValue
        surveyDataModel?.WATER_SUPPLY_TYPE = jalPradayeValue
        surveyDataModel?.RAIN_WATER_HARVESTING = rainWaterHarwestingValue
        surveyDataModel?.SEWAGE_CONNECTION = malNikasiConnectionValue
        surveyDataModel?.WATER_TANK = paniTankiValue
        surveyDataModel?.BUILDING_PERMIT = bhawanAnugyaValue
        surveyDataModel?.DISCOUNT_TYPE_OTHER_NAME =
            fragmentTabFourBinding.txtOtherChootPrakarName.text.toString()
        surveyDataModel?.TOILET_COUNT = fragmentTabFourBinding.txtToiletCount.text.toString()
        surveyDataModel?.TREE_COUNT = fragmentTabFourBinding.txtTreeCount.text.toString()
        surveyDataModel?.DISCOUNT_TYPE =
            fragmentTabFourBinding.spinnerchootPrakar.selectedItem.toString()
        surveyDataModel?.DISCOUNT_TYPE_CODE=discount_type_id.toString()
        surveyDataModel?.WATER_SOURCE =
            fragmentTabFourBinding.spinnerJalSrotra.selectedItem.toString()
        surveyDataModel?.WATER_SOURCE_ID=water_source_id
        surveyDataModel?.PIPE_THICKNESS =
            fragmentTabFourBinding.spinnerPipeMotai.selectedItem.toString()
        surveyDataModel?.PIPE_THICKNESS_ID=pipe_thickness_id
        surveyDataModel?.PIPE_PRESSURE =
            fragmentTabFourBinding.spinnerPressureStithi.selectedItem.toString()
        surveyDataModel?.PIPE_PRESSURE_ID=pipe_pressure_id
        surveyDataModel?.WATER_METER =
            fragmentTabFourBinding.spinnerWaterMeter.selectedItem.toString()
        surveyDataModel?.WATER_METER_ID=water_meter_id
        surveyDataModel?.WATER_METER_CONDITION =
            fragmentTabFourBinding.spinnerWaterMeterCondition.selectedItem.toString()
        surveyDataModel?.WATER_METER_CONDITION_ID=water_meter_condition_id
        surveyDataModel?.RAIN_WATER_HARVESTING_USES =
            fragmentTabFourBinding.spinnerRainWaterUses.selectedItem.toString()
        surveyDataModel?.RAIN_WATER_HARVESTING_USES_ID=rain_water_uses_id
        surveyDataModel?.SEWAGE_NETWORK_IF_NOT =
            fragmentTabFourBinding.spinnerSewageManage.selectedItem.toString()
        surveyDataModel?.SEWAGE_NETWORK_IF_NOT_ID=sewage_network_if_not_id
        surveyDataModel?.SEWAGE_TYPE =
            fragmentTabFourBinding.spinnerMalNikasiType.selectedItem.toString()
        surveyDataModel?.SEWAGE_TYPE_ID=sewage_type_id
        surveyDataModel?.TWO_WHEELER =
            fragmentTabFourBinding.spinnerTwoWheeler.selectedItem.toString()
        surveyDataModel?.FOUR_WHEELER =
            fragmentTabFourBinding.spinnerFourWheeler.selectedItem.toString()
        surveyDataModel?.WATER_SUPPLY_HOURS =
            fragmentTabFourBinding.spinnerAvdhi.selectedItem.toString()
        surveyDataModel?.UNIQUE_ID = unique_id
        surveyDataModel?.ISVISITED=true
        surveyDataModel?.ISUPLOADED=false
        if (!Utility.isNullOrEmpty(signature_file_path.toString()))
        {
            surveyDataModel?.SIGNATURE_IMAGE_NAME=signature_file_path!!.name
        }
        if (!Utility.isNullOrEmpty(image_file_path.toString()))
        {
            surveyDataModel?.PROPERTY_IMAGE_NAME=image_file_path!!.name
        }


        surveyDataModel!!.LOGIN_ID=Utility.getStringPreference(Constants.LOGIN_ID,mActivity)
        surveyDataModel!!.STATE_NAME=Utility.getStringPreference(Constants.STATE_NAME,mActivity)
        surveyDataModel!!.ZONE_NAME=Utility.getStringPreference(Constants.ZONE_NAME,mActivity)
        surveyDataModel!!.ZONE_ID= Utility.getIntPreference(Constants.ZONE_CODE,mActivity).toString()
        surveyDataModel!!.ULB_NAME=Utility.getStringPreference(Constants.ULB_NAME,mActivity)
        surveyDataModel!!.ULB_ID= Utility.getIntPreference(Constants.ULB_CODE,mActivity).toString()
        surveyDataModel!!.DIST_NAME=Utility.getStringPreference(Constants.DIST_NAME,mActivity)
        surveyDataModel!!.DIST_ID= Utility.getIntPreference(Constants.DIST_CODE,mActivity).toString()
        surveyDataModel!!.WARD_NAME=Utility.getStringPreference(Constants.WARD_NAME,mActivity)
        surveyDataModel!!.WARD_ID= Utility.getIntPreference(Constants.WARD_CODE,mActivity).toString()

        val isSave = SurveyDataHelper.saveSuvreyData(surveyDataModel, mActivity)


        if (isSave)
            Utility.clearAllFragment(mActivity!!)
            Utility.replaceFragment(SurveyCompleteFragment(activityMainBinding), mActivity!!.getSupportFragmentManager(), R.id.layout_fragment);

//        Utility.replaceFragment(
//            SignatureFragment(activityMainBinding, unique_id),
//            mActivity!!.supportFragmentManager,
//            R.id.layout_fragment
//        )

    }

    override fun imageCallback(file: File) {
        try {
            //  Utility.playBeep(mActivity, null);
            if (file.exists() && surveyDataModel != null) {
                image_file_path=file
                ImageAsync(mActivity!!,
                    file,true,
                    surveyDataModel!!,
                    false,
                    object : PhotoCompressedListener {
                        override fun compressedPhoto(path: String?) {
                            if (surveyDataModel != null) {
                                if (true && surveyDataModel?.PROPERTY_IMAGE != null) {

                                    fragmentTabFourBinding.propertyimageoriginal1.visibility =
                                        View.VISIBLE
                                    fragmentTabFourBinding.propertyimageoriginal1.setImageBitmap(
                                        Utility.getBitmapByStringImage(
                                            surveyDataModel?.PROPERTY_IMAGE
                                        )
                                    )
                                }
                                //img_capture.setImageBitmap(Utility.getBitmapByStringImage(userBillModel.getMETER_IMAGE_PATH()));
                            }
                        }
                    }).execute()
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Log.e("CKDemo", "Exception in photo callback")
        }
    }

    override fun signatureCallback(file: File) {
        try {
            //  Utility.playBeep(mActivity, null);
            if (file.exists() && surveyDataModel != null) {
                signature_file_path=file
                ImageAsync(mActivity!!,
                    file,false,
                    surveyDataModel!!,
                    true,
                    object : PhotoCompressedListener {
                        override fun compressedPhoto(path: String?) {
                            if (surveyDataModel != null) {
                                if (true && surveyDataModel?.SIGNATURE_IMAGE != null) {

                                    fragmentTabFourBinding.signatureimageoriginal1.visibility =
                                        View.VISIBLE
                                    fragmentTabFourBinding.signatureimageoriginal1.setImageBitmap(
                                        Utility.getBitmapByStringImage(
                                            surveyDataModel?.SIGNATURE_IMAGE
                                        )
                                    )
                                }
                                //img_capture.setImageBitmap(Utility.getBitmapByStringImage(userBillModel.getMETER_IMAGE_PATH()));
                            }
                        }
                    }).execute()
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Log.e("CKDemo", "Exception in photo callback")
        }
    }

}