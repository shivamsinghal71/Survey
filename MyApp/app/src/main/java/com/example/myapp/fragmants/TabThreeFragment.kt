package com.example.myapp.fragmants

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.Common.Utility
import com.example.myapp.R
import com.example.myapp.adapters.BhawanVargikaranAdapter
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.databinding.FragmentTabThreeBinding
import com.example.myapp.dbhelper.SurveyDataHelper
import com.example.myapp.dbhelper.TabCDataHelper
import com.example.myapp.listner.OnFragmentChangeListener
import com.example.myapp.model.SurveyDataModel
import com.example.myapp.model.TabCModel


class TabThreeFragment : Fragment, View.OnClickListener {
    private lateinit var bhawanVargikaranAdapter: BhawanVargikaranAdapter
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var fragmentTabThreeBinding: FragmentTabThreeBinding
    private var onFragmentChange: OnFragmentChangeListener? = null
    private var mActivity: FragmentActivity? = null
    private var surveyDataModel: SurveyDataModel? = null
    var bhawanVargikaranList = ArrayList<HashMap<String, String>>()
    private var bundle: Bundle? = null
    var unique_id: String? = null
    var manjil_id=0;
    var bhawan_id=0;
    var floor_id=0;
    var upyog_id=0;

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
        fragmentTabThreeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_tab_three, container, false)
        return fragmentTabThreeBinding.root

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            bundle = arguments
            unique_id = bundle?.getString("UNIQUE_ID")!!
            surveyDataModel = SurveyDataHelper.getByUniqueId(unique_id!!, mActivity)
            val ss=""
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI()

        fragmentTabThreeBinding.btnSaveInfo.setOnClickListener(this)
        fragmentTabThreeBinding.addRow.setOnClickListener(this)
        fragmentTabThreeBinding.img7.setOnClickListener(this)
        fragmentTabThreeBinding.img8.setOnClickListener(this)
        fragmentTabThreeBinding.img9.setOnClickListener(this)
        fragmentTabThreeBinding.img10.setOnClickListener(this)

    }

    private fun updateUI() {
        bundle = arguments
        unique_id = bundle?.getString("UNIQUE_ID")!!
        surveyDataModel = SurveyDataHelper.getByUniqueId(unique_id!!, mActivity)

        bindManjilTalCode()
        bindBhawanCode()
        bindFloorCode()
        bindUpyog()
    }

    private fun bindManjilTalCode() {
        val manjilTalList = ArrayList<String>()
        manjilTalList.add("select")
        manjilTalList.add("बेसमेंट")
        manjilTalList.add("भूतल")
        manjilTalList.add("मजेनाइल तल")
        manjilTalList.add("प्रथम तल")
        manjilTalList.add("द्वितीय तल")
        manjilTalList.add("तृतीय तल")
        manjilTalList.add("चतुर्थ तल")
        manjilTalList.add("N तल")
        manjilTalList.add("बरामद/पोर्च")

        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, manjilTalList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentTabThreeBinding.spinnerManjilTalCode.adapter = adapter

            fragmentTabThreeBinding.spinnerManjilTalCode.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {

                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        manjil_id=position
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {

                    }
        }
    }

    private fun bindBhawanCode() {
        val bhawanCodeList = ArrayList<String>()
        bhawanCodeList.add("select")
        bhawanCodeList.add("आरसीसी छत, आरसीसी कॉलम से साथ")
        bhawanCodeList.add("आरसीसी छत, बिना आरसीसी कॉलम के")
        bhawanCodeList.add("आरबीसी, चिप ग्रिडर के साथ")
        bhawanCodeList.add("जीआई/एडबेसटर कारुगेटेड शीट के साथ")
        bhawanCodeList.add("कंट्री टाइल्स, ब्रिक मेसेनरी दीवाल के साथ")
        bhawanCodeList.add("कंट्री टाइल्स, बिना ब्रिक मेसेनरी दीवाल के साथ")

        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, bhawanCodeList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentTabThreeBinding.spinnerBhawanCode.adapter = adapter

            fragmentTabThreeBinding.spinnerBhawanCode.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {

                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        bhawan_id=position
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {

                }
        }
    }

    private fun bindFloorCode() {
        val floorCodeList = ArrayList<String>()
        floorCodeList.add("select")
        floorCodeList.add("ग्रेनाइट/मार्बल")
        floorCodeList.add("मोजाइक फ्लोर/विट्री फाइड़ टाइल्स")
        floorCodeList.add("सीमेंट फ्लोरिंग / ब्लैक स्लैब स्टोन फ्लोरिंग")
        floorCodeList.add("अन्य फ्लोर")

        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, floorCodeList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentTabThreeBinding.spinnerFloorCode.adapter = adapter

            fragmentTabThreeBinding.spinnerFloorCode.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {

                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        floor_id=position
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {

                }
        }
    }

    private fun bindUpyog() {
        val upyogList = ArrayList<String>()
        upyogList.add("select")
        upyogList.add("आवासीय (स्वयं के लिए)")
        upyogList.add("आवासीय (किराए के लिए)")
        upyogList.add("व्यावसायिक")
        upyogList.add("ऑफिस")
        upyogList.add("गोदाम")

        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, upyogList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentTabThreeBinding.spinnerUpyog.adapter = adapter

        fragmentTabThreeBinding.spinnerUpyog.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    upyog_id=position
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
                saveSurveyInfo()
            }

            R.id.img7 -> {
                fragmentTabThreeBinding.spinnerManjilTalCode.performClick()
            }
            R.id.img8 -> {
                fragmentTabThreeBinding.spinnerBhawanCode.performClick()
            }
            R.id.img9 -> {
                fragmentTabThreeBinding.spinnerFloorCode.performClick()
            }
            R.id.img10 -> {
                fragmentTabThreeBinding.spinnerUpyog.performClick()
            }

            R.id.add_row -> {
                if (validateTabC()) {
                    fragmentTabThreeBinding.scrollviewRec.visibility = View.VISIBLE

                    var map: HashMap<String, String> = HashMap<String, String>()
                    map.put("manjil_tal_code", fragmentTabThreeBinding.spinnerManjilTalCode.selectedItem.toString())
                    map.put("manjil_tal_id", manjil_id.toString())
                    map.put("construction_year_c", fragmentTabThreeBinding.txtNirmanYear.text.toString())
                    map.put("bhawan_code", fragmentTabThreeBinding.spinnerBhawanCode.selectedItem.toString())
                    map.put("bhawan_id", bhawan_id.toString())
                    map.put("floor_code", fragmentTabThreeBinding.spinnerFloorCode.selectedItem.toString())
                    map.put("floor_id",floor_id.toString())
                    map.put("area_c", fragmentTabThreeBinding.txtArea.text.toString())
                    map.put("majil_uses_code", fragmentTabThreeBinding.spinnerUpyog.selectedItem.toString())
                    map.put("majil_uses_id", upyog_id.toString())

                    bhawanVargikaranList.add(map)

                    fragmentTabThreeBinding.spinnerManjilTalCode.setSelection(0)
                    fragmentTabThreeBinding.spinnerBhawanCode.setSelection(0)
                    fragmentTabThreeBinding.spinnerFloorCode.setSelection(0)
                    fragmentTabThreeBinding.spinnerUpyog.setSelection(0)
                    fragmentTabThreeBinding.txtNirmanYear.setText("")
                    fragmentTabThreeBinding.txtArea.setText("")

                    bhawanVargikaranAdapter =
                        BhawanVargikaranAdapter(bhawanVargikaranList, mActivity!!)
                    val layoutManager = LinearLayoutManager(mActivity)
                    fragmentTabThreeBinding.bhawanVargikaranRec.layoutManager = layoutManager
                    fragmentTabThreeBinding.bhawanVargikaranRec.itemAnimator = DefaultItemAnimator()
                    fragmentTabThreeBinding.bhawanVargikaranRec.adapter = bhawanVargikaranAdapter
                }

                //bhawanVargikaranAdapter.setItemListener(utilityItemListener)

            }
        }
    }

    private fun validateTabC(): Boolean {
        if (Utility.isNullOrEmpty(fragmentTabThreeBinding.spinnerManjilTalCode.selectedItem.toString())) {
            Toast.makeText(mActivity, "मजिल कोड सेलेक्ट करे", Toast.LENGTH_SHORT).show()
            return false
        } else if (Utility.isNullOrEmpty(fragmentTabThreeBinding.txtNirmanYear.text.toString())) {
            Toast.makeText(mActivity, "वर्ष डाले", Toast.LENGTH_SHORT).show()
            return false
        } else if (Utility.isNullOrEmpty(fragmentTabThreeBinding.spinnerBhawanCode.selectedItem.toString())) {
            Toast.makeText(mActivity, "भवन कोड सेलेक्ट करे", Toast.LENGTH_SHORT).show()
            return false
        } else if (Utility.isNullOrEmpty(fragmentTabThreeBinding.spinnerFloorCode.selectedItem.toString())) {
            Toast.makeText(mActivity, "फ्लोर कोड सेलेक्ट करे", Toast.LENGTH_SHORT).show()
            return false
        } else if (Utility.isNullOrEmpty(fragmentTabThreeBinding.txtArea.text.toString())) {
            Toast.makeText(mActivity, "छेत्रफल डाले", Toast.LENGTH_SHORT).show()
            return false
        } else if (Utility.isNullOrEmpty(fragmentTabThreeBinding.spinnerUpyog.selectedItem.toString())) {
            Toast.makeText(mActivity, "उपयोग कोड सेलेक्ट करे", Toast.LENGTH_SHORT).show()
            return false
        }


        return true
    }


    private fun saveSurveyInfo() {


        if(bhawanVargikaranList !=null && bhawanVargikaranList.size>0) {


            bhawanVargikaranList?.forEachIndexed { index, data ->

                var tabCModel = TabCModel()
                tabCModel?.CONSTRUCTION_YEAR_C =
                    bhawanVargikaranList.get(index).get("construction_year_c").toString()
                tabCModel?.AREA_C = bhawanVargikaranList.get(index).get("area_c").toString()
                tabCModel?.MANJIL_CODE =
                    bhawanVargikaranList.get(index).get("manjil_tal_code").toString()
                tabCModel?.MANJIL_ID =
                    bhawanVargikaranList.get(index).get("manjil_tal_id")!!.toInt()
                tabCModel?.BUILDING_CODE =
                    bhawanVargikaranList.get(index).get("bhawan_code").toString()
                tabCModel?.BUILDING_ID = bhawanVargikaranList.get(index).get("bhawan_id")!!.toInt()
                tabCModel?.FLOOR_CODE = bhawanVargikaranList.get(index).get("floor_code").toString()
                tabCModel?.FLOOR_ID = bhawanVargikaranList.get(index).get("floor_id")!!.toInt()
                tabCModel?.MANJIL_USES =
                    bhawanVargikaranList.get(index).get("majil_uses_code").toString()
                tabCModel?.MANJIL_USES_ID =
                    bhawanVargikaranList.get(index).get("majil_uses_id")!!.toInt()


                tabCModel?.UNIQUE_ID = unique_id
                tabCModel?.STATUS = "true"
                val isSave = TabCDataHelper.saveTabCData(tabCModel, mActivity)
                if (isSave) {

                }
            }

        }
        surveyDataModel?.ISVISITED = false
        val isSave = SurveyDataHelper.saveSuvreyData(surveyDataModel, mActivity)

        if (isSave)
            this.onFragmentChange?.onFragmentChange(3)
    }
}