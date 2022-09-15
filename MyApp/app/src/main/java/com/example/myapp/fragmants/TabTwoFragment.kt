package com.example.myapp.fragmants

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.myapp.Common.Constants
import com.example.myapp.Common.Utility
import com.example.myapp.R
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.databinding.FragmentTabTwoBinding
import com.example.myapp.dbhelper.PropertyDetailsHelper
import com.example.myapp.dbhelper.SurveyDataHelper
import com.example.myapp.listner.OnFragmentChangeListener
import com.example.myapp.model.SurveyDataModel


class TabTwoFragment : Fragment, View.OnClickListener {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var fragmentTabTwoBinding: FragmentTabTwoBinding
    private var onFragmentChange: OnFragmentChangeListener? = null
    private var mActivity: FragmentActivity? = null
    private var surveyDataModel: SurveyDataModel? = null
    var sampattiNagarNigamRecordValue = ""
    var aapKyaHaiValue = ""
    var aaykardaataValue = ""
    private var bundle: Bundle? = null
    var unique_id: String? = null
    var property_uses_id=0
    var property_uses_other_id=0
    var relation_other_id=0
    var property_classification_id=0
    var land_type_id=0

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
        fragmentTabTwoBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_tab_two, container, false)
        return fragmentTabTwoBinding.root

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            bundle = arguments
            unique_id = bundle?.getString("UNIQUE_ID")!!
            surveyDataModel = SurveyDataHelper.getByUniqueId(unique_id!!, mActivity)


            if (surveyDataModel!=null)
            {
            val model = PropertyDetailsHelper.getByUniqueId(surveyDataModel!!.UNIQUE_ID_TEXT.toString(),mActivity)
            if (model!!.size>0)
            {
                fragmentTabTwoBinding.txtShriKumari.setText(model.get(0).Owner_Name)
                fragmentTabTwoBinding.txtEmailID.setText(model.get(0).Email)
                fragmentTabTwoBinding.txtMakanKramank.setText(model.get(0).House_No)
                fragmentTabTwoBinding.txtPati.setText(model.get(0).Father_Name)
                fragmentTabTwoBinding.txtPincodeName.setText(Utility.getStringPreference(
                    Constants.PINCODE,mActivity))
            }


            val ss = ""
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentTabTwoBinding.btnSaveInfo.setOnClickListener(this)
        fragmentTabTwoBinding.img3.setOnClickListener(this)
        fragmentTabTwoBinding.img4.setOnClickListener(this)
        fragmentTabTwoBinding.img5.setOnClickListener(this)
        fragmentTabTwoBinding.img6.setOnClickListener(this)
        fragmentTabTwoBinding.imgPrivarSamuhNo.setOnClickListener(this)
        fragmentTabTwoBinding.imgPrivarSamuhKirayedar.setOnClickListener(this)
        updateUI(view)
    }

    private fun updateUI(view: View) {

        bundle = arguments
        unique_id = bundle?.getString("UNIQUE_ID")!!
        surveyDataModel = SurveyDataHelper.getByUniqueId(unique_id!!, mActivity)




        fragmentTabTwoBinding.rgSampattiNagarNigamRecord.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> // checkedId is the RadioButton selected
            val radio: RadioButton = view.findViewById(checkedId)
            sampattiNagarNigamRecordValue = radio.text.toString()
        })

        fragmentTabTwoBinding.radioGroupAapKyaHai.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> // checkedId is the RadioButton selected
            val radio: RadioButton = view.findViewById(checkedId)
            aapKyaHaiValue = radio.text.toString()

            if (aapKyaHaiValue.equals("संपत्ति मालिक"))
            {
                if (!Utility.isNullOrEmpty(surveyDataModel?.ROAD_STREET_NO)) {
                    fragmentTabTwoBinding.txtRoadKramank.setText(surveyDataModel!!.ROAD_STREET_NO)
                }
                if (!Utility.isNullOrEmpty(surveyDataModel?.ROAD_STREET_NAME)) {
                    fragmentTabTwoBinding.txtRoadName.setText(surveyDataModel!!.ROAD_STREET_NAME)
                }
                if (!Utility.isNullOrEmpty(surveyDataModel?.COLONY_NAME)) {
                    fragmentTabTwoBinding.txtMohllaName.setText(surveyDataModel!!.COLONY_NAME)
                }
                if (!Utility.isNullOrEmpty(surveyDataModel?.STATION_NAME)) {
                    fragmentTabTwoBinding.txtThanaName.setText(surveyDataModel!!.STATION_NAME)
                }
                if (!Utility.isNullOrEmpty(surveyDataModel?.PINCODE)) {
                    fragmentTabTwoBinding.txtPincodeName.setText(surveyDataModel!!.PINCODE)
                }

            }
            if (radio.text.equals("अन्य")) {
                fragmentTabTwoBinding.lytRista.visibility = View.VISIBLE
            } else {
                fragmentTabTwoBinding.lytRista.visibility = View.GONE
                fragmentTabTwoBinding.lytOtherRelationName.visibility=View.GONE
            }
        })

        fragmentTabTwoBinding.rgAaykardaata.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> // checkedId is the RadioButton selected
            val radio: RadioButton = view.findViewById(checkedId)
            aaykardaataValue = radio.text.toString()
        })

        bindSampattiUpyog()
        bindSampattiUpyogIfOne()
        bindRista()
        bindVargikaran()
        bindBhumiPrakar()
        bindParivar()
        bindRental()
    }

    private fun bindRental() {
        val rentalList = ArrayList<String>()
        rentalList.add("0")
        rentalList.add("1")
        rentalList.add("2")
        rentalList.add("3")
        rentalList.add("4")
        rentalList.add("5")
        rentalList.add("6")
        rentalList.add("7")
        rentalList.add("8")
        rentalList.add("9")
        rentalList.add("10")
        rentalList.add("11")
        rentalList.add("12")
        rentalList.add("13")
        rentalList.add("14")
        rentalList.add("15")
        rentalList.add("16")
        rentalList.add("17")
        rentalList.add("18")
        rentalList.add("19")
        rentalList.add("20")

        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, rentalList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentTabTwoBinding.spinnerPrivarSamuhKirayedar.adapter = adapter


            fragmentTabTwoBinding.spinnerPrivarSamuhKirayedar.onItemSelectedListener =
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

    private fun bindParivar() {
        val familyMemberList = ArrayList<String>()
        familyMemberList.add("0")
        familyMemberList.add("1")
        familyMemberList.add("2")
        familyMemberList.add("3")
        familyMemberList.add("4")
        familyMemberList.add("5")
        familyMemberList.add("6")
        familyMemberList.add("7")
        familyMemberList.add("8")
        familyMemberList.add("9")
        familyMemberList.add("10")
        familyMemberList.add("11")
        familyMemberList.add("12")
        familyMemberList.add("13")
        familyMemberList.add("14")
        familyMemberList.add("15")
        familyMemberList.add("16")
        familyMemberList.add("17")
        familyMemberList.add("18")
        familyMemberList.add("19")
        familyMemberList.add("20")

        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, familyMemberList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentTabTwoBinding.spinnerPrivarSamuhNo.adapter = adapter

            fragmentTabTwoBinding.spinnerPrivarSamuhNo.onItemSelectedListener =
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


    private fun bindSampattiUpyog() {
        val sampattiUpyogList = ArrayList<String>()
        sampattiUpyogList.add("select")
        sampattiUpyogList.add("आवासीय")
        sampattiUpyogList.add("व्यावसायिक")
        sampattiUpyogList.add("ऑफिस")
        sampattiUpyogList.add("गोदाम")
        sampattiUpyogList.add("ओधोगिक")
        sampattiUpyogList.add("धार्मिक स्थल")
        sampattiUpyogList.add("अन्य संस्थागत स्थल")

        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, sampattiUpyogList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentTabTwoBinding.spinnerSampattiUpyog.adapter = adapter

            fragmentTabTwoBinding.spinnerSampattiUpyog.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {

                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        property_uses_id=position
                        if (parent.selectedItem.toString().equals("आवासीय") || parent.selectedItem.toString().equals("select")) {
                            fragmentTabTwoBinding.lytSampattiUpyogIfOne.visibility = View.GONE
                            fragmentTabTwoBinding.lytSampattiUpyogIfOneRemark.visibility=View.GONE
                            fragmentTabTwoBinding.lytSampattiUpyogIfOneRemarkTwo.visibility=View.GONE
                            fragmentTabTwoBinding.lytSampattiUpyogIfOneRemarkThree.visibility=View.GONE
                            fragmentTabTwoBinding.spinnerSampattiUpyogIfOne.setSelection(0)
                        } else {
                            fragmentTabTwoBinding.lytSampattiUpyogIfOne.visibility = View.VISIBLE
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {

                }
        }
    }


    private fun bindSampattiUpyogIfOne() {
        val sampattiUpyogIfOneList = ArrayList<String>()
        sampattiUpyogIfOneList.add("select")
        sampattiUpyogIfOneList.add("आवासीय")
        sampattiUpyogIfOneList.add("गेर आवासीय")
        sampattiUpyogIfOneList.add("रेस्तरा")
        sampattiUpyogIfOneList.add("होटल")
        sampattiUpyogIfOneList.add("धर्मशाला")
        sampattiUpyogIfOneList.add("बारात घर")
        sampattiUpyogIfOneList.add("प्राथमिक शाला")
        sampattiUpyogIfOneList.add("उच्च/उच्चतर माध्यमिक शाला")
        sampattiUpyogIfOneList.add("महाविधालय")
        sampattiUpyogIfOneList.add("विश्व विधालय")
        sampattiUpyogIfOneList.add("अन्य शक्षणिक संस्थान")
        sampattiUpyogIfOneList.add("अस्पताल / नर्शिंग होम")
        sampattiUpyogIfOneList.add("चिकित्शाल्या")
        sampattiUpyogIfOneList.add("पथोलोजी लैब")
        sampattiUpyogIfOneList.add("किराना स्टोर")
        sampattiUpyogIfOneList.add("वाणीजीक संस्थाऐ")
        sampattiUpyogIfOneList.add("शॉपिंग मॉल")
        sampattiUpyogIfOneList.add("सब्जी/फल/फूल/मुर्गी/मटन मार्केट")
        sampattiUpyogIfOneList.add("ब्युटि पार्लर")
        sampattiUpyogIfOneList.add("सिनेमा हॉल/थियेटर")
        sampattiUpyogIfOneList.add("मनोरंजन पार्क")
        sampattiUpyogIfOneList.add("आश्रम शालाए")
        sampattiUpyogIfOneList.add("सर्कस/प्रदर्शनी/मेला/खुला स्थान मे सार्वजनिक कार्य")


        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, sampattiUpyogIfOneList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentTabTwoBinding.spinnerSampattiUpyogIfOne.adapter = adapter


            fragmentTabTwoBinding.spinnerSampattiUpyogIfOne.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {

                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        property_uses_other_id=position
                        if (parent.selectedItem.toString().equals("रेस्तरा")) {
                            fragmentTabTwoBinding.lblSampattiUpyogIfOneRemark.setText("कुर्सीयो की संख्या")
                            fragmentTabTwoBinding.lytSampattiUpyogIfOneRemark.visibility =
                                View.VISIBLE
                        } else if (parent.selectedItem.toString().equals("होटल")) {
                            fragmentTabTwoBinding.lblSampattiUpyogIfOneRemark.setText("कमरो की संख्या")
                            fragmentTabTwoBinding.lblSampattiUpyogIfOneRemarkTwo.setText("हॉल की संख्या")
                            fragmentTabTwoBinding.lytSampattiUpyogIfOneRemark.visibility =
                                View.VISIBLE
                            fragmentTabTwoBinding.lytSampattiUpyogIfOneRemarkTwo.visibility =
                                View.VISIBLE
                        } else if (parent.selectedItem.toString().equals("धर्मशाला")) {
                            fragmentTabTwoBinding.lblSampattiUpyogIfOneRemark.setText("कमरो की संख्या")
                            fragmentTabTwoBinding.lytSampattiUpyogIfOneRemark.visibility =
                                View.VISIBLE
                        } else if (parent.selectedItem.toString().equals("अस्पताल / नर्शिंग होम")) {
                            fragmentTabTwoBinding.lblSampattiUpyogIfOneRemark.setText("बिस्तरों की संख्या")
                            fragmentTabTwoBinding.lytSampattiUpyogIfOneRemark.visibility =
                                View.VISIBLE
                        } else if (parent.selectedItem.toString().equals("वाणीजीक संस्थाऐ")) {
                            fragmentTabTwoBinding.lblSampattiUpyogIfOneRemark.setText("फर्श छेत्रफल")
                            fragmentTabTwoBinding.lytSampattiUpyogIfOneRemark.visibility =
                                View.VISIBLE
                        } else if (parent.selectedItem.toString()
                                .equals("सब्जी/फल/फूल/मुर्गी/मटन मार्केट")
                        ) {
                            fragmentTabTwoBinding.lblSampattiUpyogIfOneRemark.setText("लोडिंग/अनलोडिंग ट्रक की संख्या (LCV)")
                            fragmentTabTwoBinding.lblSampattiUpyogIfOneRemarkTwo.setText("लोडिंग/अनलोडिंग ट्रक की संख्या (HV)")
                            fragmentTabTwoBinding.lblSampattiUpyogIfOneRemarkThree.setText("फर्श छेत्रफल")
                            fragmentTabTwoBinding.lytSampattiUpyogIfOneRemark.visibility =
                                View.VISIBLE
                            fragmentTabTwoBinding.lytSampattiUpyogIfOneRemarkTwo.visibility =
                                View.VISIBLE
                            fragmentTabTwoBinding.lytSampattiUpyogIfOneRemarkThree.visibility =
                                View.VISIBLE
                        } else {
                            fragmentTabTwoBinding.lytSampattiUpyogIfOneRemark.visibility = View.GONE
                            fragmentTabTwoBinding.lytSampattiUpyogIfOneRemarkTwo.visibility =
                                View.GONE
                            fragmentTabTwoBinding.lytSampattiUpyogIfOneRemarkThree.visibility =
                                View.GONE
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {


                }
        }

    }


    private fun bindVargikaran() {
        val vargikaranList = ArrayList<String>()
        vargikaranList.add("select")
        vargikaranList.add("व्यतिगत एकल")
        vargikaranList.add("सनयुंक्त परिवार")
        vargikaranList.add("पार्टनर्शिप फर्म")
        vargikaranList.add("ट्रस्ट/सोसाइटी")
        vargikaranList.add("लिमिटेड कंपनी")
        vargikaranList.add("को-आपरेटिव सोसाइटी")
        vargikaranList.add("केंद्र शासन के सार्वजनिक उपकर्म")
        vargikaranList.add("राज्य शासन के सार्वजनिक उपकर्म")
        vargikaranList.add("केंद्र शासन ")
        vargikaranList.add("राज्य शासन ")
        vargikaranList.add("नगर निगम ")
        vargikaranList.add("वेध राजनतिक दल")
        vargikaranList.add("अन्य")

        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, vargikaranList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentTabTwoBinding.spinnerVargikaran.adapter = adapter


            fragmentTabTwoBinding.spinnerVargikaran.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {

                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        property_classification_id=position
                        if (parent.selectedItem.toString().equals("अन्य")) {
                            fragmentTabTwoBinding.lytOtherVargikaranName.visibility = View.VISIBLE
                        } else {
                            fragmentTabTwoBinding.lytOtherVargikaranName.visibility = View.GONE

                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {

                }
        }
    }

    private fun bindBhumiPrakar() {
        val bhumiPrakarList = ArrayList<String>()
        bhumiPrakarList.add("select")
        bhumiPrakarList.add("निजी/ ऋण पुस्तिका")
        bhumiPrakarList.add("नाजुल या शासकिए भूमि")
        bhumiPrakarList.add("पुरानी आबादी भूमि")
        bhumiPrakarList.add("नगर निगम की भूमि")
        bhumiPrakarList.add("हाउसिंग बोर्ड/सोसाइटी/ट्रस्ट की भूमि")
        bhumiPrakarList.add("अन्य")

        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, bhumiPrakarList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentTabTwoBinding.spinnerBhumiPrakar.adapter = adapter


            fragmentTabTwoBinding.spinnerBhumiPrakar.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {

                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        land_type_id=position
                        if (parent.selectedItem.toString().equals("अन्य")) {
                            fragmentTabTwoBinding.lytOtherBhumiPrakarName.visibility = View.VISIBLE
                        } else {
                            fragmentTabTwoBinding.lytOtherBhumiPrakarName.visibility = View.GONE
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {

                }
        }
    }

    private fun bindRista() {
        val ristaList = ArrayList<String>()
        ristaList.add("select")
        ristaList.add("पति/पत्नी")
        ristaList.add("पुत्र/पुत्री")
        ristaList.add("भाई/बहन")
        ristaList.add("रिश्तेदार")
        ristaList.add("पड़ोसी")
        ristaList.add("अधिकारी")
        ristaList.add("अन्य")

        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, ristaList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentTabTwoBinding.spinnerRista.adapter = adapter


            fragmentTabTwoBinding.spinnerRista.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {

                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        relation_other_id=position
                        if (parent.selectedItem.toString().equals("अन्य")) {
                            fragmentTabTwoBinding.lytOtherRelationName.visibility = View.VISIBLE
                        } else {
                            fragmentTabTwoBinding.lytOtherRelationName.visibility = View.GONE
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

                if (validateTabTwo()) {
                    saveSurveyInfo()
                }

            }
            R.id.img3 -> {
                fragmentTabTwoBinding.spinnerVargikaran.performClick()
            }
            R.id.img4 -> {
                fragmentTabTwoBinding.spinnerBhumiPrakar.performClick()
            }
            R.id.img5 -> {
                fragmentTabTwoBinding.spinnerSampattiUpyog.performClick()
            }
            R.id.img6 -> {
                fragmentTabTwoBinding.spinnerSampattiUpyogIfOne.performClick()
            }
            R.id.imgPrivarSamuhNo -> {
                fragmentTabTwoBinding.spinnerPrivarSamuhNo.performClick()
            }
            R.id.imgPrivarSamuhKirayedar -> {
                fragmentTabTwoBinding.spinnerPrivarSamuhKirayedar.performClick()
            }
        }
    }

    private fun saveSurveyInfo() {
        surveyDataModel?.PROPERTY_REGISTERED = sampattiNagarNigamRecordValue
        surveyDataModel?.PROPERTY_RELATION = aapKyaHaiValue
        surveyDataModel?.RESPONDENT_NAME = fragmentTabTwoBinding.txtRespondentName.text.toString()
        surveyDataModel?.PROPERTY_RELATION_OTHER_NAME =
            fragmentTabTwoBinding.txtOtherRelationName.text.toString()
        surveyDataModel?.OTHER_CLASSIFICATION_NAME =
            fragmentTabTwoBinding.txtOtherVargikaranName.text.toString()
        surveyDataModel?.LAND_TYPE_OTHER =
            fragmentTabTwoBinding.txtOtherBhumiPrakarName.text.toString()
        surveyDataModel?.NAME = fragmentTabTwoBinding.txtShriKumari.text.toString()
        surveyDataModel?.HUSBAND_FATHER_NAME = fragmentTabTwoBinding.txtPati.text.toString()
        surveyDataModel?.HOUSE_NO = fragmentTabTwoBinding.txtMakanKramank.text.toString()
        surveyDataModel?.ROAD_STREET_NO2 = fragmentTabTwoBinding.txtRoadKramank.text.toString()
        surveyDataModel?.ROAD_STREET_NAME2 = fragmentTabTwoBinding.txtRoadName.text.toString()
        surveyDataModel?.COLONY_NAME2 = fragmentTabTwoBinding.txtMohllaName.text.toString()
        surveyDataModel?.STATION_NAME2 = fragmentTabTwoBinding.txtThanaName.text.toString()
        surveyDataModel?.CITY = fragmentTabTwoBinding.txtSeharName.text.toString()
        surveyDataModel?.STATE_NAME2 = fragmentTabTwoBinding.txtPradeshName.text.toString()
        surveyDataModel?.PINCODE2 = fragmentTabTwoBinding.txtPincodeName.text.toString()
        surveyDataModel?.EMAIL = fragmentTabTwoBinding.txtEmailID.text.toString()
        surveyDataModel?.INCOMETAX_PAYER = aaykardaataValue
        surveyDataModel?.FEMALE_MEMBER = fragmentTabTwoBinding.txtFemale.text.toString()
        surveyDataModel?.MALE_MEMBER = fragmentTabTwoBinding.txtMale.text.toString()
        surveyDataModel?.PROPERTY_USES_OTHER_REMARK1 =
            fragmentTabTwoBinding.txtSampattiUpyogIfOneRemark.text.toString()
        surveyDataModel?.PROPERTY_USES_OTHER_REMARK2 =
            fragmentTabTwoBinding.txtSampattiUpyogIfOneRemarkTwo.text.toString()
        surveyDataModel?.PROPERTY_USES_OTHER_REMARK3 =
            fragmentTabTwoBinding.txtSampattiUpyogIfOneRemarkThree.text.toString()
        surveyDataModel?.CONSTRUCTION_YEAR =
            fragmentTabTwoBinding.txtBhawanNirmanYear.text.toString()
        surveyDataModel?.LAND_AREA = fragmentTabTwoBinding.txtBhumiArea.text.toString()
        surveyDataModel?.PROPERTY_RELATION_OTHER =
            fragmentTabTwoBinding.spinnerRista.selectedItem.toString()
        surveyDataModel?.PROPERTY_RELATION_OTHER_ID=relation_other_id
        surveyDataModel?.PROPERTY_OWNERSHIP_CLASSIFICATION =
            fragmentTabTwoBinding.spinnerVargikaran.selectedItem.toString()
        surveyDataModel?.PROPERTY_OWNERSHIP_CLASSIFICATION_ID=property_classification_id
        surveyDataModel?.LAND_TYPE =
            fragmentTabTwoBinding.spinnerBhumiPrakar.selectedItem.toString()
        surveyDataModel?.LAND_TYPE_ID=land_type_id
        surveyDataModel?.PROPERTY_USES =
            fragmentTabTwoBinding.spinnerSampattiUpyog.selectedItem.toString()
        surveyDataModel?.PROPERTY_USES_ID=property_uses_id
        surveyDataModel?.PROPERTY_USES_OTHER =
            fragmentTabTwoBinding.spinnerSampattiUpyogIfOne.selectedItem.toString()
        surveyDataModel?.PROPERTY_USES_OTHER_ID=property_uses_other_id
        surveyDataModel?.FAMILY_GROUP =
            fragmentTabTwoBinding.spinnerPrivarSamuhNo.selectedItem.toString()

        surveyDataModel?.FAMILY_RENTAL_GROUP =
            fragmentTabTwoBinding.spinnerPrivarSamuhKirayedar.selectedItem.toString()
        surveyDataModel?.UNIQUE_ID = unique_id
        surveyDataModel?.ISVISITED = false
        val isSave = SurveyDataHelper.saveSuvreyData(surveyDataModel, mActivity)

        if (isSave)
            this.onFragmentChange?.onFragmentChange(2)
    }


    private fun validateTabTwo(): Boolean {


        if (!Utility.isNullOrEmpty(fragmentTabTwoBinding.txtPincodeName.text.toString())) {
            if (!Utility.isValidPinCode(
                    fragmentTabTwoBinding.layoutRoot,
                    fragmentTabTwoBinding.txtPincodeName.text.toString()
                )
            ) {
                Utility.snackBar( fragmentTabTwoBinding.layoutRoot, requireActivity().resources.getString(R.string.enter_correct_pincode),1200, requireActivity().resources.getColor(R.color.warning))
                fragmentTabTwoBinding.txtPincodeName.requestFocus()
                return false
            }
        }
        if (!Utility.isNullOrEmpty(fragmentTabTwoBinding.txtEmailID.text.toString())) {
            if (!Utility.isValidMail(
                    fragmentTabTwoBinding.layoutRoot,
                    fragmentTabTwoBinding.txtEmailID.text.toString()
                )
            ) {
                Utility.snackBar( fragmentTabTwoBinding.layoutRoot, requireActivity().resources.getString(R.string.enter_correct_email),1200, requireActivity().resources.getColor(R.color.warning))
                fragmentTabTwoBinding.txtEmailID.requestFocus()
                return false
            }
        }

        return true
    }

}
