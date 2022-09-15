package com.example.myapp.fragmants

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.example.myapp.Common.Utility
import com.example.myapp.R
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.databinding.FragmentSyncOutBinding
import com.example.myapp.dbhelper.SurveyDataHelper
import com.example.myapp.listner.OnFinishListener

// TODO: Rename parameter arguments, choose names that match

class SyncOutFragment : Fragment  , View.OnClickListener
{
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var fragmentSyncOutBinding: FragmentSyncOutBinding
    private var mActivity: FragmentActivity? = null
    private var onFinishListener: OnFinishListener? = null
    var rowCount=0;

    constructor() {}
    constructor( activityMainBinding: ActivityMainBinding)
    {
        this.activityMainBinding=activityMainBinding
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentSyncOutBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_sync_out, container, false)


        onFinishListener = object : OnFinishListener {
            override fun onFinish(message: String?) {
                Handler(Looper.getMainLooper()).post {
                    updateUI()
                    if (message != null) {
                        if (message.equals("Data Uploaded Successfully", ignoreCase = true))
                        {

                            Utility.snackBar(
                                fragmentSyncOutBinding.layoutRoot,
                                message,
                                1200,
                                activity!!.resources.getColor(R.color.red)
                            )
                            fragmentSyncOutBinding.btnUploadSurvey.setEnabled(true)
                        }
                        else
                        {
                            Utility.snackBar(
                                fragmentSyncOutBinding.layoutRoot,
                                message,
                                1200,
                                activity!!.resources.getColor(R.color.warning)
                            )
                            fragmentSyncOutBinding.btnUploadSurvey.setEnabled(true)
                        }
                    }
                }
            }
        }

        return fragmentSyncOutBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        fragmentSyncOutBinding.btnUploadSurvey.setOnClickListener(this)
        updateUI()



    }

    private fun updateUI()
    {
        rowCount= SurveyDataHelper.getRowCount(1,0, mActivity!!)
        fragmentSyncOutBinding.txtPendingForSyncout.setText("Pending Data For Syncout :" + rowCount.toString())
    }

    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        mActivity = context as FragmentActivity
    }


    override fun onClick(p0: View?) {
        when(p0?.id)
        {
            R.id.btnUploadSurvey->
            {
                fragmentSyncOutBinding.btnUploadSurvey.setEnabled(false)
                if (Utility.isOnline(mActivity!!))
                {
                    //uploadData(rowCount)
                }
                else Utility.snackBar(
                    fragmentSyncOutBinding.layoutRoot,
                    requireActivity().resources.getString(R.string.no_internet_connection),
                    1200,
                    requireActivity().resources.getColor(R.color.warning)
                )
                fragmentSyncOutBinding.btnUploadSurvey.setEnabled(true)
            }
        }

    }

    /*private fun uploadData(rowCount: Int) {
        try {
            fragmentSyncOutBinding.btnUploadSurvey.setEnabled(false)
            val progressDialog = ProgressDialog(mActivity, R.style.MyAlertDialogStyle)
            progressDialog.setMessage("Uploading......")
            progressDialog.show()
            progressDialog.setCancelable(false)
            progressDialog.setCanceledOnTouchOutside(false)
            if (Utility.isOnline(mActivity!!)) {
                if (rowCount > 0 ) {
                    uploadSurveyData(mActivity!!,
                        false,
                        progressDialog,
                        onFinishListener,rowCount
                    )
                } else {
                    fragmentSyncOutBinding.btnUploadSurvey.setEnabled(true)
                    progressDialog.cancel()
                    Utility.snackBar(
                        fragmentSyncOutBinding.layoutRoot,
                        requireActivity().resources.getString(R.string.data_not_available),
                        1200,
                        mActivity!!.resources.getColor(R.color.warning)
                    )
                }
            } else {
                fragmentSyncOutBinding.btnUploadSurvey.setEnabled(true)
                progressDialog.cancel()
                Utility.snackBar(
                    fragmentSyncOutBinding.layoutRoot,
                    requireActivity().resources.getString(R.string.no_internet_connection),
                    1200,
                    requireActivity().resources.getColor(R.color.warning)
                )
            }
        } catch (e: Exception) {
            e.message
        }

    }

    private fun uploadSurveyData(mActivity: FragmentActivity, b: Boolean, progressDialog: ProgressDialog, onFinishListener: OnFinishListener?, rowCount: Int) {
        if (rowCount > 0) {
            val onFinishListener1: OnFinishListener = object : OnFinishListener {
                override fun onFinish(message: String?) {
                    onFinishListener?.onFinish(message)
                    progressDialog.cancel()
                }
            }
            uploadDataFinal(
                mActivity,
                progressDialog,
                onFinishListener1
            )
        }
    }*/


/*
    fun uploadDataFinal(
        context: Context?,
        progressDialog: ProgressDialog,
        onFinishListener: OnFinishListener?
    ) {
        try {
            val retrofit: Retrofit = MyApplication.mInstance!!.retrofitInstanceLogin!!
            val gerritAPI: Api = retrofit.create(Api::class.java)
            Thread {
                try {
                    var jsonObject: JSONObject? = null
                    val jsonParser = JsonParser()
                    var response: Response<MeterIndexingResponse>? = null
                    var failedMessage: String? = ""
                    var totalCount = 0
                    var uploadedCount = 0
                    var failedCount = 0
                    val userInfoModel: UserInfoModel? = UserDataHelper.getLogin(context)
                    val surveyDataArrayList: ArrayList<SurveyDataModel> =
                        SurveyDataHelper.getAll(1, 0, context)!!
                    if (surveyDataArrayList != null) totalCount =
                        totalCount + surveyDataArrayList.size

                    for (surveyDataModel in surveyDataArrayList)
                    {
                        if (isOnline(MyApplication.mInstance!!))
                        {
                            val jsonObject= createUploadJSON(surveyDataModel)

                            val jsonObject1 = jsonParser.parse(jsonObject.toString()) as JsonObject
                            val call: Call<MeterIndexingResponse> =
                                gerritAPI.saveSurveyData(surveyDataModel)!!
                            response = call.clone().execute()
                            if (response != null && response.isSuccessful()) {
                                val meterIndexingResponse: MeterIndexingResponse? = response.body()
                                if (meterIndexingResponse != null) {
                                    if (!meterIndexingResponse.STATUS!!) {
                                        failedCount++
                                        failedMessage = meterIndexingResponse.MESSAGE
                                    } else {
                                        surveyDataModel.ISUPLOADED=true
                                        val isDeleted = MIConsumerDataHelper.deleteByAccountNumber(surveyDataModel.ACCOUNT_NO, context)
                                        uploadedCount++
                                    }
                                }
                            } else {
                                failedCount++
                                if (response != null) {
                                    failedMessage = response.message()
                                } else {
                                }
                            }
                        } else break
                        publishStatus(
                            progressDialog,
                            totalCount,
                            uploadedCount,
                            failedCount,
                            "Survey",
                        )
                    }
                } catch (e: java.lang.Exception) {
                    e.message
                }
                onFinishListener?.onFinish("Data Uploaded Successfully")
            }.start()
        } catch (e: java.lang.Exception) {
            progressDialog.cancel()
        }
    }

    private fun publishStatus(
        progressDialog: ProgressDialog,
        totalCount: Int,
        uploadedCount: Int,
        failedCount: Int,
        uploadingType: String
    ) {
        Handler(Looper.getMainLooper()).post {
            progressDialog.setMessage(
                """
    Uploading $uploadingType......

    Total Count    :$totalCount
    Success :$uploadedCount
    Fail  :$failedCount
    """.trimIndent()
            )
        }
    }

    fun createUploadJSON(surveyDataModel: SurveyDataModel) : JSONObject
    {

        val object1 = JSONObject()
        try {
            object1.put("SURVEY_DATA", getJson(surveyDataModel))
        } catch (ex: java.lang.Exception) {
            ex.message
        }
        return object1

    }



    fun getJson(surveyDataModel: SurveyDataModel?): JSONObject? {
        val gson = Gson()
        var request: JSONObject? = null
        try {
            request = JSONObject(gson.toJson(surveyDataModel))
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return request
    }

*/

}