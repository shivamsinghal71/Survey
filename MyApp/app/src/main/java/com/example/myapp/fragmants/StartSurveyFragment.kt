package com.example.myapp.fragmants

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.myapp.Common.Constants
import com.example.myapp.Common.Utility
import com.example.myapp.R
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.databinding.FragmentStartSurveyBinding
import com.example.myapp.dbhelper.PropertyDetailsHelper
import com.example.myapp.dbhelper.SurveyDataHelper
import com.example.myapp.dbhelper.WardDataHelper
import com.example.myapp.dbhelper.ZoneDataHelper
import com.example.myapp.listner.OnFragmentChangeListener
import com.example.myapp.model.*
import com.inventia.ugo_mici.dbhelper.UserDataHelper
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Workbook
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*


class StartSurveyFragment : Fragment, View.OnClickListener {

    private lateinit var fragmentStartSurveyBinding: FragmentStartSurveyBinding
    private lateinit var activityMainBinding: ActivityMainBinding
    private var onFragmentChange: OnFragmentChangeListener? = null
    private var mActivity: FragmentActivity? = null
    private var surveyDataModel: SurveyDataModel? = null
    private var userInfoModel: UserInfoModel? = null
    var zone_id = 0
    var ward_id = 0
    var dist_id = 0
    var ulb_id = 0

    constructor() {}
    constructor(activityMainBinding: ActivityMainBinding) {
        this.activityMainBinding = activityMainBinding
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentStartSurveyBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_start_survey, container, false)
        return fragmentStartSurveyBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI()
        fragmentStartSurveyBinding.btnStartSurvey.setOnClickListener(this)
        fragmentStartSurveyBinding.imgDist.setOnClickListener(this)
        fragmentStartSurveyBinding.imgUlb.setOnClickListener(this)
        fragmentStartSurveyBinding.imgZone.setOnClickListener(this)
        fragmentStartSurveyBinding.imgWard.setOnClickListener(this)
    }

    private fun updateUI() {

        setDist()
        setULB()
        setZone()
        setWard()
        val rowCount = SurveyDataHelper.getRowCount(1, 0, mActivity!!)
        fragmentStartSurveyBinding.txtTodaySurvey.setText("Today's Survey :" + rowCount.toString())

    }

    private fun setDist() {

        var distArrayList = ArrayList<String>()
        distArrayList.add("select")
        distArrayList.add("Raipur")
        distArrayList.add("Gariyabandh")
        distArrayList.add("Balodabazar")
        distArrayList.add("Dhamtari")
        distArrayList.add("Mahasamund")
        distArrayList.add("Baloda")
        distArrayList.add("Durg")
        distArrayList.add("Kabirdham")
        distArrayList.add("Bemetara")
        distArrayList.add("Bilaspur")
        distArrayList.add("Mungeli")
        distArrayList.add("Bilaspur")
        distArrayList.add("Janjgir-champa")
        distArrayList.add("Jashpur-nagar")
        distArrayList.add("Balrampur")
        distArrayList.add("Surajpur")
        distArrayList.add("Kondagaon")
        distArrayList.add("Narayanpur")
        distArrayList.add("Uttar Bastar")
        distArrayList.add("Dakshin Bastar")
        distArrayList.add("Sukma")
        distArrayList.add("Bijapur")

        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, distArrayList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentStartSurveyBinding.spinnerDist.adapter = adapter

        if (Utility.getIntPreference(Constants.DIST_CODE,mActivity)!=0)
        {
            fragmentStartSurveyBinding.spinnerDist.setSelection(Utility.getIntPreference(Constants.DIST_CODE,mActivity))
        }

        fragmentStartSurveyBinding.spinnerDist.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    dist_id = position
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

    }


    private fun setULB() {

        var ulbArrayList = ArrayList<String>()
        ulbArrayList.add("select")
        ulbArrayList.add("Tilda -Newra")
        ulbArrayList.add("Gobranawapara")
        ulbArrayList.add("Birgaon")
        ulbArrayList.add("Aarang")
        ulbArrayList.add("Gariyabandh")
        ulbArrayList.add("Balodabazar")
        ulbArrayList.add("Bhatapara")
        ulbArrayList.add("Dhamtari")
        ulbArrayList.add("Mahasamund")
        ulbArrayList.add("Bagbahara")
        ulbArrayList.add("Saraipali")
        ulbArrayList.add("Bhilai-Charoda")
        ulbArrayList.add("Kumhari")
        ulbArrayList.add("Ahiwara")
        ulbArrayList.add("Patan")
        ulbArrayList.add("Balod")
        ulbArrayList.add("Dallirajhara")
        ulbArrayList.add("Bemetara")
        ulbArrayList.add("Dongargarh")
        ulbArrayList.add("Khairagarh")
        ulbArrayList.add("Kawardha")
        ulbArrayList.add("Takhatpur")
        ulbArrayList.add("Ratanpur")
        ulbArrayList.add("Mungeli")
        ulbArrayList.add("Deepika")
        ulbArrayList.add("Katghora")
        ulbArrayList.add("Janjgir-Naila")
        ulbArrayList.add("Champa")
        ulbArrayList.add("Sakti")
        ulbArrayList.add("Akaltara")
        ulbArrayList.add("Kharsiya")
        ulbArrayList.add("Sarangarh")
        ulbArrayList.add("jashpur-nagar")
        ulbArrayList.add("Balrampur")
        ulbArrayList.add("Surajpur")
        ulbArrayList.add("Manendragarh")
        ulbArrayList.add("Baikunthpur")
        ulbArrayList.add("Shivpurcharcha")
        ulbArrayList.add("Kondagaon")
        ulbArrayList.add("Narayanpur")
        ulbArrayList.add("Kanker")
        ulbArrayList.add("Bad-Bacheli")
        ulbArrayList.add("Dantewada")
        ulbArrayList.add("Sukma")
        ulbArrayList.add("Bijapur")

        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, ulbArrayList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentStartSurveyBinding.spinnerULB.adapter = adapter


        if (Utility.getIntPreference(Constants.ULB_CODE,mActivity)!=0)
        {
            fragmentStartSurveyBinding.spinnerULB.setSelection(Utility.getIntPreference(Constants.ULB_CODE,mActivity))
        }

        fragmentStartSurveyBinding.spinnerULB.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    ulb_id = position
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

    }


    /*  private fun setDist() {
          val distArrayList: ArrayList<DistModel>? = DistDataHelper.getAll(mActivity)
          val distNameList = ArrayList<String>()
          distNameList.add(0, "Select")
          distArrayList?.indices?.forEach { i ->
              distArrayList[i].DIST_NAME?.let { distNameList.add(it) }
          }

          val adapter = ArrayAdapter(
              requireActivity(),
              R.layout.spinner_item_type, distNameList
          )
          adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
          fragmentStartSurveyBinding.spinnerDist.adapter = adapter

          if (fragmentStartSurveyBinding.spinnerDist.onItemSelectedListener!=null)
          {
              fragmentStartSurveyBinding.spinnerDist.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                  override fun onItemSelected(
                      parent: AdapterView<*>,
                      view: View,
                      position: Int,
                      id: Long
                  ) {
                      dist_id=position
                  }

                  override fun onNothingSelected(parent: AdapterView<*>) {

                  }
              }
          }
      }

      private fun setULB() {
          val ulbArrayList: ArrayList<ULBModel>? = ULBDataHelper.getAll(mActivity)
          val ulbNameList = ArrayList<String>()
          ulbNameList.add(0, "Select")
          ulbArrayList?.indices?.forEach { i ->
              ulbArrayList[i].ULB_NAME?.let { ulbNameList.add(it) }
          }

          val adapter = ArrayAdapter(
              requireActivity(),
              R.layout.spinner_item_type, ulbNameList
          )
          adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
          fragmentStartSurveyBinding.spinnerULB.adapter = adapter

          if (fragmentStartSurveyBinding.spinnerULB.onItemSelectedListener!=null)
          {
              fragmentStartSurveyBinding.spinnerULB.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                  override fun onItemSelected(
                      parent: AdapterView<*>,
                      view: View,
                      position: Int,
                      id: Long
                  ) {
                      ulb_id=position
                  }

                  override fun onNothingSelected(parent: AdapterView<*>) {

                  }
              }
          }
      }*/

    private fun setZone() {
        val zoneArrayList: ArrayList<ZoneModel>? = ZoneDataHelper.getAll(mActivity)
        val zoneNameList = ArrayList<String>()
        zoneNameList.add(0, "Select")
        zoneArrayList?.indices?.forEach { i ->
            zoneArrayList[i].ZONE_NAME?.let { zoneNameList.add(it) }
        }

        val adapter = ArrayAdapter(
            requireActivity(),
            R.layout.spinner_item_type, zoneNameList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentStartSurveyBinding.spinnerZone.adapter = adapter

        if (Utility.getIntPreference(Constants.ZONE_CODE,mActivity)!=0)
        {
            fragmentStartSurveyBinding.spinnerZone.setSelection(Utility.getIntPreference(Constants.ZONE_CODE,mActivity))
        }

        fragmentStartSurveyBinding.spinnerZone.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    zone_id = position
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
    }


    private fun setWard() {
        val wardArrayList: ArrayList<WardModel>? = WardDataHelper.getAll(mActivity)
        val wardNameList = ArrayList<String>()
        wardNameList.add(0, "Select")
        wardArrayList?.indices?.forEach { i ->
            wardArrayList[i].WARD_NAME?.let { wardNameList.add(it) }
        }

        val adapter = ArrayAdapter(
            requireActivity(),
            R.layout.spinner_item_type, wardNameList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        fragmentStartSurveyBinding.spinnerWard.adapter = adapter

        if (Utility.getIntPreference(Constants.WARD_CODE,mActivity)!=0)
        {
            fragmentStartSurveyBinding.spinnerWard.setSelection(Utility.getIntPreference(Constants.WARD_CODE,mActivity))
        }

        fragmentStartSurveyBinding.spinnerWard.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    ward_id = position
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
            R.id.btnStartSurvey -> {


                if (validateStartSurvey()) {
                    val progressDialog = ProgressDialog(mActivity, R.style.MyAlertDialogStyle)
                    progressDialog.setMessage("Please Wait...")
                    progressDialog.show()
                    progressDialog.setCancelable(false)
                    progressDialog.setCanceledOnTouchOutside(false)
                    progressDialog.show()

                    PropertyDataAsync(mActivity ,activityMainBinding).execute()

                    val fragment = MainFragment(activityMainBinding);

                    userInfoModel = UserDataHelper.getLogin(mActivity)
                    if (userInfoModel != null) {

                        var dateTime = Utility.dateTimeToString(Date())
                        var dateTimeArray = dateTime!!.split("-")

                        val random1 =
                            userInfoModel!!.MOBILE_NO + "_" + dateTimeArray[0] + "_" + dateTimeArray[1] + "_" + dateTimeArray[2] + "_" + dateTimeArray[3] + "_" + dateTimeArray[4] + "_" + dateTimeArray[5]
                        saveSurveyInfo(fragment, random1)
                        progressDialog.dismiss()
                    }
                }
            }

            R.id.imgDist -> {
                fragmentStartSurveyBinding.spinnerDist.performClick()
            }
            R.id.imgUlb -> {
                fragmentStartSurveyBinding.spinnerULB.performClick()
            }

            R.id.imgZone -> {
                fragmentStartSurveyBinding.spinnerZone.performClick()
            }
            R.id.imgWard -> {
                fragmentStartSurveyBinding.spinnerWard.performClick()
            }
        }
    }

    class PropertyDataAsync() : AsyncTask<String, String, List<PropertyDetailsModel>> ()
    {
        private var dialog: Dialog? = null
        private var mActivity: FragmentActivity? = null
        private lateinit var activityMainBinding: ActivityMainBinding


        constructor(mActivity: FragmentActivity? , activityMainBinding: ActivityMainBinding) : this() {
            this.mActivity=mActivity
            this.activityMainBinding=activityMainBinding
        }

        override fun onPreExecute() {
            super.onPreExecute()
            dialog = Utility.startProgress(mActivity!!)
            (dialog!!.findViewById<View>(R.id.txtreceivedData) as TextView).text = "Loading Property Data..."
        }

        override fun doInBackground(vararg p0: String?) : List<PropertyDetailsModel>
        {
            val propertyDetailList: List<PropertyDetailsModel> = readExcelFile("/storage/emulated/0/Property_Tax_Data.xls")

            if (propertyDetailList.size > 0)
            {
                PropertyDetailsHelper.deleteAll(mActivity)
                for (propertyDetailsModel in propertyDetailList) {
                    PropertyDetailsHelper.savePropertyDetailsData(
                        propertyDetailsModel,
                        mActivity
                    )
                }
            }
            else
            {
                Handler(Looper.getMainLooper()).post {


                    Toast.makeText(mActivity, "Property Data not available" , Toast.LENGTH_LONG).show()
                }
            }
            return propertyDetailList
        }


        override fun onPostExecute(result: List<PropertyDetailsModel> ) {
            super.onPostExecute(result)
            if (dialog != null && dialog!!.isShowing)
                Utility.stopProgress(dialog!!)

        }

        fun readExcelFile(filePath: String): List<PropertyDetailsModel> {
            val lstCustomers: MutableList<PropertyDetailsModel> = java.util.ArrayList<PropertyDetailsModel>()
            var file = File(filePath)
            var fileExists = file.exists()
            if (fileExists)
            {
                return try {

                    val excelFile = FileInputStream(File(filePath))
                    val workbook: Workbook = HSSFWorkbook(excelFile)
                    val sheet = workbook.getSheet("Sheet1")
                    val rows: MutableIterator<Row> = sheet.iterator()

                    var rowNumber = 0
                    while (rows.hasNext()) {
                        val currentRow: Row = rows.next()

                        // skip header
                        if (rowNumber == 0) {
                            rowNumber++
                            continue
                        }
                        val cellsInRow: Iterator<Cell> = currentRow.iterator()
                        val cust = PropertyDetailsModel()
                        var cellIndex = 0
                        while (cellsInRow.hasNext()) {
                            val currentCell: Cell = cellsInRow.next()

                            if (cellIndex == 0) {
                                currentCell.cellType = Cell.CELL_TYPE_STRING
                                cust.PM_PROP_NO = currentCell.stringCellValue
                            } else if (cellIndex == 1) {
                                cust.PM_PROP_OLDPROPNO = currentCell.toString()
                            } else if (cellIndex == 2) {
                                currentCell.cellType = Cell.CELL_TYPE_STRING
                                cust.Owner_Name = currentCell.stringCellValue
                            } else if (cellIndex == 3) {
                                currentCell.cellType = Cell.CELL_TYPE_STRING
                                cust.Father_Name = currentCell.stringCellValue
                            } else if (cellIndex == 4) {
                                currentCell.cellType = Cell.CELL_TYPE_STRING
                                cust.Mobile_Number = currentCell.stringCellValue
                            } else if (cellIndex == 5) {
                                currentCell.cellType = Cell.CELL_TYPE_STRING
                                cust.Email = currentCell.stringCellValue
                            } else if (cellIndex == 6) {
                                currentCell.cellType = Cell.CELL_TYPE_STRING
                                cust.Property_Location_Address = currentCell.stringCellValue
                            } else if (cellIndex == 7) {
                                currentCell.cellType = Cell.CELL_TYPE_STRING
                                cust.Location = currentCell.stringCellValue
                            } else if (cellIndex == 8) {
                                currentCell.cellType = Cell.CELL_TYPE_STRING
                                cust.LANDMARK = currentCell.stringCellValue
                            } else if (cellIndex == 9) {
                                currentCell.cellType = Cell.CELL_TYPE_STRING
                                cust.House_No = currentCell.stringCellValue
                            } else if (cellIndex == 10) {
                                currentCell.cellType = Cell.CELL_TYPE_STRING
                                cust.Kharsra_No = currentCell.stringCellValue
                            }


                            cellIndex++
                        }
                        lstCustomers.add(cust)
                    }

                    // Close WorkBook
                    lstCustomers
                } catch (e: IOException) {
                    throw RuntimeException("FAIL! -> message = " + e.message)
                }
            }
            else
            {
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(mActivity,filePath + "not available" , Toast.LENGTH_LONG).show()
                }

            }
            return lstCustomers
        }

    }




    private fun validateStartSurvey(): Boolean {
        val zone_name = fragmentStartSurveyBinding.spinnerZone.selectedItem.toString()
        val ward_name = fragmentStartSurveyBinding.spinnerWard.selectedItem.toString()
        val dist_name = fragmentStartSurveyBinding.spinnerDist.selectedItem.toString()
        val ulb_name = fragmentStartSurveyBinding.spinnerULB.selectedItem.toString()

        if (Utility.isNullOrEmpty(dist_name) || dist_name.equals("Select")) {
            fragmentStartSurveyBinding.spinnerDist.requestFocus()
            Utility.snackBar(
                fragmentStartSurveyBinding.layoutRoot,
                requireActivity().resources.getString(R.string.select_dist),
                1200,
                requireActivity().resources.getColor(R.color.warning)
            )
            return false
        } else if (Utility.isNullOrEmpty(ulb_name) || ulb_name.equals("Select")) {
            fragmentStartSurveyBinding.spinnerULB.requestFocus()
            Utility.snackBar(
                fragmentStartSurveyBinding.layoutRoot,
                requireActivity().resources.getString(R.string.select_ulb),
                1200,
                requireActivity().resources.getColor(R.color.warning)
            )
            return false
        } else if (Utility.isNullOrEmpty(zone_name) || zone_name.equals("Select")) {
            fragmentStartSurveyBinding.spinnerZone.requestFocus()
            Utility.snackBar(
                fragmentStartSurveyBinding.layoutRoot,
                requireActivity().resources.getString(R.string.select_zone),
                1200,
                requireActivity().resources.getColor(R.color.warning)
            )
            return false
        } else if (Utility.isNullOrEmpty(ward_name) || ward_name.equals("Select")) {
            fragmentStartSurveyBinding.spinnerWard.requestFocus()
            Utility.snackBar(
                fragmentStartSurveyBinding.layoutRoot,
                requireActivity().resources.getString(R.string.select_ward),
                1200,
                requireActivity().resources.getColor(R.color.warning)
            )
            return false
        }
        else if(Utility.isNullOrEmpty(fragmentStartSurveyBinding.txtPincode.text.toString()))
        {
            Utility.snackBar(
                fragmentStartSurveyBinding.layoutRoot,
                requireActivity().resources.getString(R.string.enter_pincode),
                1200,
                requireActivity().resources.getColor(R.color.warning)
            )
            fragmentStartSurveyBinding.txtPincode.requestFocus()
            return false

        }
        else if (!Utility.isNullOrEmpty(fragmentStartSurveyBinding.txtPincode.text.toString())) {
            if (!Utility.isValidPinCode(
                    fragmentStartSurveyBinding.layoutRoot,
                    fragmentStartSurveyBinding.txtPincode.text.toString()
                )
            ) {
                Utility.snackBar(
                    fragmentStartSurveyBinding.layoutRoot,
                    requireActivity().resources.getString(R.string.enter_correct_pincode),
                    1200,
                    requireActivity().resources.getColor(R.color.warning)
                )
                fragmentStartSurveyBinding.txtPincode.requestFocus()
                return false
            }
        }
        return true
    }

    private fun saveSurveyInfo(fragment: Fragment, random1: String) {


        surveyDataModel = SurveyDataHelper.getByUniqueId(random1!!, mActivity)
        if (surveyDataModel == null) {
            surveyDataModel = SurveyDataModel()
            surveyDataModel?.STATE_NAME = fragmentStartSurveyBinding.txtStateName.text.toString()
            surveyDataModel?.PINCODE = fragmentStartSurveyBinding.txtPincode.text.toString()
            surveyDataModel?.UNIQUE_ID = random1
            surveyDataModel?.ZONE_NAME =
                fragmentStartSurveyBinding.spinnerZone.selectedItem.toString()
            surveyDataModel?.ZONE_ID = zone_id.toString()
            surveyDataModel?.ULB_NAME =
                fragmentStartSurveyBinding.spinnerULB.selectedItem.toString()
            surveyDataModel?.ULB_ID = ulb_id.toString()
            surveyDataModel?.DIST_NAME =
                fragmentStartSurveyBinding.spinnerDist.selectedItem.toString()
            surveyDataModel?.DIST_ID = dist_id.toString()
            surveyDataModel?.WARD_NAME =
                fragmentStartSurveyBinding.spinnerWard.selectedItem.toString()
            surveyDataModel?.WARD_ID = ward_id.toString()

            Utility.saveStringPreference(Constants.LOGIN_ID, userInfoModel?.MOBILE_NO, mActivity)
            Utility.saveStringPreference(
                Constants.STATE_NAME,
                fragmentStartSurveyBinding.txtStateName.text.toString(), mActivity
            )
            Utility.saveStringPreference(
                Constants.PINCODE,
                fragmentStartSurveyBinding.txtPincode.text.toString(), mActivity
            )
            Utility.saveStringPreference(
                Constants.ZONE_NAME,
                fragmentStartSurveyBinding.spinnerZone.selectedItem.toString(), mActivity
            )
            Utility.saveStringPreference(
                Constants.ULB_NAME,
                fragmentStartSurveyBinding.spinnerULB.selectedItem.toString(), mActivity
            )
            Utility.saveStringPreference(
                Constants.DIST_NAME,
                fragmentStartSurveyBinding.spinnerDist.selectedItem.toString(), mActivity
            )
            Utility.saveStringPreference(
                Constants.WARD_NAME,
                fragmentStartSurveyBinding.spinnerWard.selectedItem.toString(), mActivity
            )

            Utility.saveIntPreference(Constants.ZONE_CODE, zone_id, mActivity)
            Utility.saveIntPreference(Constants.ULB_CODE, ulb_id, mActivity)
            Utility.saveIntPreference(Constants.DIST_CODE, dist_id, mActivity)
            Utility.saveIntPreference(Constants.WARD_CODE, ward_id, mActivity)


            val isSave = SurveyDataHelper.saveSuvreyData(surveyDataModel, mActivity)

            if (isSave) {

                val bundle = Bundle()
                bundle.putString("UNIQUE_ID", random1)
                Utility.replaceFragmentWithBundle(
                    fragment,
                    mActivity!!.supportFragmentManager,
                    R.id.layout_fragment,
                    bundle
                )
            }
        }


    }
}