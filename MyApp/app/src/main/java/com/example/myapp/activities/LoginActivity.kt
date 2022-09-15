package com.example.myapp.activities

import android.Manifest
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.Settings
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myapp.ApiCall.Api
import com.example.myapp.Common.Utility
import com.example.myapp.app.MyApplication
import com.example.myapp.R
import com.example.myapp.databinding.ActivityLoginBinding
import com.example.myapp.dbhelper.WardDataHelper
import com.example.myapp.dbhelper.ZoneDataHelper
import com.example.myapp.model.*
import com.example.myapp.model.LoginRequestModel
import com.example.myapp.model.LoginResponseModel
import com.inventia.ugo_mici.dbhelper.UserDataHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var ulbArrayList: ArrayList<String>
    private lateinit var distModel: ArrayList<DistModel>
    private lateinit var activityLoginBinding: ActivityLoginBinding
    var myAlertDialog: AlertDialog? = null
    var showpassword = false
    private lateinit var userInfoModel: UserInfoModel
    private val READ_STORAGE_PERMISSION_REQUEST_CODE = 41

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        activityLoginBinding.showPassword.setOnClickListener(this)
        activityLoginBinding.buttonLogin.setOnClickListener(this)
        setULB()
        updateUI()
    }

    private fun updateUI() {

       // addLogin()
    }

    private fun setULB() {

        ulbArrayList = ArrayList<String>()
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

    }

   /* private fun addLogin() {
        userInfoModel = UserInfoModel()
        userInfoModel.USER_ID = "3"
        userInfoModel.USER_NAME = "Jaishree Deshmukh"
        userInfoModel.MOBILE_NO = "7023668774"
        userInfoModel.EMAIL_ID = ""
        userInfoModel.AADHAR_NO = "508154944537"
        val isSave1 = UserDataHelper.saveUserDatabaseData(
            userInfoModel,
            this
        )


        userInfoModel = UserInfoModel()
        userInfoModel.USER_ID = "1"
        userInfoModel.USER_NAME = "Kavita Kumari"
        userInfoModel.MOBILE_NO = "7049942782"
        userInfoModel.EMAIL_ID = ""
        userInfoModel.AADHAR_NO = "847951816280"
        val isSave2 = UserDataHelper.saveUserDatabaseData(
            userInfoModel,
            this
        )

        userInfoModel = UserInfoModel()
        userInfoModel.USER_ID = "2"
        userInfoModel.USER_NAME = "Priti Sahu"
        userInfoModel.MOBILE_NO = "7224955793"
        userInfoModel.EMAIL_ID = ""
        userInfoModel.AADHAR_NO = "896663482170"
        val isSave3 = UserDataHelper.saveUserDatabaseData(
            userInfoModel,
            this
        )

        userInfoModel = UserInfoModel()
        userInfoModel.USER_ID = "9"
        userInfoModel.USER_NAME = "Shreya Meshram"
        userInfoModel.MOBILE_NO = "7956865231"
        userInfoModel.EMAIL_ID = ""
        userInfoModel.AADHAR_NO = "213114160905"
        val isSave4 = UserDataHelper.saveUserDatabaseData(
            userInfoModel,
            this
        )

        userInfoModel = UserInfoModel()
        userInfoModel.USER_ID = "7"
        userInfoModel.USER_NAME = "Parveen Begam"
        userInfoModel.MOBILE_NO = "8423262142"
        userInfoModel.EMAIL_ID = ""
        userInfoModel.AADHAR_NO = "774574770611"
        val isSave5 = UserDataHelper.saveUserDatabaseData(
            userInfoModel,
            this
        )

        userInfoModel = UserInfoModel()
        userInfoModel.USER_ID = "6"
        userInfoModel.USER_NAME = "Poonam"
        userInfoModel.MOBILE_NO = "9398111090"
        userInfoModel.EMAIL_ID = ""
        userInfoModel.AADHAR_NO = "893320857065"
        val isSave6 = UserDataHelper.saveUserDatabaseData(
            userInfoModel,
            this
        )

        userInfoModel = UserInfoModel()
        userInfoModel.USER_ID = "10"
        userInfoModel.USER_NAME = "Shital Yadav"
        userInfoModel.MOBILE_NO = "9399368545"
        userInfoModel.EMAIL_ID = ""
        userInfoModel.AADHAR_NO = "861442259130"
        val isSave7 = UserDataHelper.saveUserDatabaseData(
            userInfoModel,
            this
        )

        userInfoModel = UserInfoModel()
        userInfoModel.USER_ID = "5"
        userInfoModel.USER_NAME = "Puja Thakur"
        userInfoModel.MOBILE_NO = "9826162251"
        userInfoModel.EMAIL_ID = ""
        userInfoModel.AADHAR_NO = "693708657077"
        val isSave8 = UserDataHelper.saveUserDatabaseData(
            userInfoModel,
            this
        )

        userInfoModel = UserInfoModel()
        userInfoModel.USER_ID = "8"
        userInfoModel.USER_NAME = "Durga Yadav"
        userInfoModel.MOBILE_NO = "9895454612"
        userInfoModel.EMAIL_ID = ""
        userInfoModel.AADHAR_NO = "521174428532"
        val isSave9 = UserDataHelper.saveUserDatabaseData(
            userInfoModel,
            this
        )

        userInfoModel = UserInfoModel()
        userInfoModel.USER_ID = "4"
        userInfoModel.USER_NAME = "Beena Yadav"
        userInfoModel.MOBILE_NO = "9953264715"
        userInfoModel.EMAIL_ID = ""
        userInfoModel.AADHAR_NO = "207219718403"
        val isSave10 = UserDataHelper.saveUserDatabaseData(
            userInfoModel,
            this
        )

        userInfoModel = UserInfoModel()
        userInfoModel.USER_ID = "11"
        userInfoModel.USER_NAME = "Jayant Singh"
        userInfoModel.MOBILE_NO = "9999999999"
        userInfoModel.EMAIL_ID = ""
        userInfoModel.AADHAR_NO = "473155653758"
        val isSave11 = UserDataHelper.saveUserDatabaseData(
            userInfoModel,
            this
        )


        userInfoModel = UserInfoModel()
        userInfoModel.USER_ID = "12"
        userInfoModel.USER_NAME = "testuser"
        userInfoModel.MOBILE_NO = "7078203741"
        userInfoModel.EMAIL_ID = ""
        userInfoModel.AADHAR_NO = "admin"
        val isSave12 = UserDataHelper.saveUserDatabaseData(
            userInfoModel,
            this
        )
    }
*/

    override fun onResume()
    {
        askForStoragePermission()
        super.onResume()
    }



     private fun askForStoragePermission() {
         if (!Utility.isPermissionGranted(this)!!) {
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                 if (myAlertDialog != null && myAlertDialog!!.isShowing()) return
                 val builder = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
                 builder.setTitle("Storage Permission")
                 builder.setMessage("Due to Android 11 restrictions, this app requires storage permission")
                 builder.setPositiveButton(
                     "ok"
                 ) { dialog, arg1 ->
                     dialog.dismiss()
                     takePermission()
                 }
                 builder.setIcon(this.resources.getDrawable(R.mipmap.ic_launcher))
                 builder.setCancelable(false)
                 myAlertDialog = builder.create()
                 myAlertDialog!!.show()
             } else {
                 takePermission()
             }

         }
     }


     private fun takePermission() {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
             try {
                 val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                 intent.addCategory("android.intent.category.DEFAULT")
                 val uri = Uri.fromParts("package", this.packageName, null)
                 intent.data = uri
                 startActivityForResult(intent, 101)
             } catch (e: Exception) {
                 e.printStackTrace()
                 val intent = Intent()
                 intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                 startActivityForResult(intent, 101)
             }
         } else {
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                 requestPermissions(
                     arrayOf(
                         Manifest.permission.READ_EXTERNAL_STORAGE,
                         Manifest.permission.WRITE_EXTERNAL_STORAGE
                     ), 101
                 )
             }
         }
     }



     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

         if(resultCode == RESULT_OK && requestCode == 101)
         {
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                 if (Environment.isExternalStorageManager()) {

                 }
             }

         }
         super.onActivityResult(requestCode, resultCode, data)
     }

     override fun onRequestPermissionsResult(
         requestCode: Int,
         permissions: Array<String?>,
         grantResults: IntArray
     ) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults)
         if (grantResults.size > 0) {
             if (requestCode == 101) {
                 val readExt = grantResults[0] == PackageManager.PERMISSION_GRANTED
                 if (!readExt) {
                     takePermission()
                 }
                 else
                 {

                 }
             }
         }
         else
         {

         }
     }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.showPassword -> {
                showpassword = if (showpassword) {
                    activityLoginBinding.edtPassword.setTransformationMethod(
                        PasswordTransformationMethod.getInstance()
                    )
                    activityLoginBinding.showPassword.setImageDrawable(resources.getDrawable(R.drawable.ic_eye_close))
                    false
                } else {
                    activityLoginBinding.edtPassword.setTransformationMethod(
                        HideReturnsTransformationMethod.getInstance()
                    )
                    activityLoginBinding.showPassword.setImageDrawable(resources.getDrawable(R.drawable.ic_eye_open))
                    true
                }
            }

            R.id.button_login -> {

//                addDistInDb()
//                addULBInDb()

                if (!isExternalStorageAvailable || isExternalStorageReadOnly) {

                }

                addZoneInDb()
                addWardInDb()

                if (loginCheck()) {

                            if (Utility.isOnline(this)) {
                                userSignIn(
                               activityLoginBinding.edtLoginId.getText().toString(),
                                activityLoginBinding.edtPassword.getText().toString()
                            )
                            } else {
                                Utility.snackBar(
                                    activityLoginBinding.layoutRoot,
                                    resources.getString(R.string.no_internet_connection),
                                    1200,
                                    resources.getColor(R.color.warning)
                                )
                            }

                }

            }

        }
    }




    private fun userSignIn(loginID: String, password: String) {
        val progressDialog = ProgressDialog(this@LoginActivity, R.style.MyAlertDialogStyle)
        progressDialog.setMessage("Please Wait...")
        progressDialog.show()
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        val loginRequestModel = LoginRequestModel()
        loginRequestModel.LOGIN_ID = loginID
        loginRequestModel.PASSWORD = password
//        loginRequestModel.FIREBASE_TOKEN = ""
//        loginRequestModel.APPLICATION_ID = BuildConfig.APPLICATION_ID
//        loginRequestModel.APP_RELEASE = BuildConfig.VERSION_NAME

        val gerritAPI: Api = MyApplication.mInstance!!.retrofitInstanceLogin!!.create(Api::class.java)
        val call: Call<LoginResponseModel> = gerritAPI.getUserLogin(loginRequestModel)

        val enqueue = call?.enqueue(object : Callback<LoginResponseModel?> {
            override fun onResponse(
                call: Call<LoginResponseModel?>,
                response: Response<LoginResponseModel?>
            ) {
                progressDialog.cancel()
                if (response.isSuccessful) {

                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        if (!loginResponse.IS_ERROR)
                        {
                            userInfoModel = UserInfoModel()
                            userInfoModel.USER_ID = loginResponse.USER_ID
                            userInfoModel.USER_NAME = loginResponse.USER_NAME
                            userInfoModel.MOBILE_NO = loginResponse.MOBILE_NO
                            userInfoModel.AADHAR_NO = loginResponse.AADHAR_NO
                            val isSave1 = UserDataHelper.saveUserDatabaseData(
                                userInfoModel,
                                this@LoginActivity
                            )
                            intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                        else
                        {
                            Utility.snackBar(
                                activityLoginBinding.layoutRoot, loginResponse.ERROR_MESSAGE,
                                1200,
                                applicationContext.resources.getColor(R.color.warning)
                            )
                        }
                    }


                } else {
                    Log.d("TAG", "onResponse: " + response.message())
                }
            }

            override fun onFailure(call: Call<LoginResponseModel?>, t: Throwable) {
                progressDialog.cancel()
                call.cancel()
            }
        })
    }


    private fun addWardInDb() {
        WardDataHelper.deleteAll(applicationContext)
        val wardModel = WardModel()
        for (i in 1..99) {
            wardModel.WARD_CODE = i
            if (i < 10) {
                wardModel.WARD_NAME = "0$i"
            } else {
                wardModel.WARD_NAME = "$i"
            }

            WardDataHelper.saveWardData(wardModel, applicationContext)
        }
    }


    private fun addZoneInDb() {
        ZoneDataHelper.deleteAll(applicationContext)
        val zoneModel = ZoneModel()
        for (i in 1..20) {

            zoneModel.ZONE_CODE = i
            if (i < 10) {
                zoneModel.ZONE_NAME = "0$i"
            } else {
                zoneModel.ZONE_NAME = "$i"
            }


            ZoneDataHelper.saveZoneData(zoneModel, applicationContext)
        }
    }

/*
    private fun addDistInDb() {


        DistDataHelper.deleteAll(applicationContext)
        val distModel = DistModel()

        var distId = 0
        for (dModel in ulbArrayList) {
            distModel.DIST_CODE = i
            if (i < 10) {
                distModel.DIST_NAME = "0$i"
            } else {
                distModel.DIST_NAME = "$i"
            }

            distId++
            DistDataHelper.saveDistData(distModel, applicationContext)
        }
    }


    private fun addULBInDb() {
        ZoneDataHelper.deleteAll(applicationContext)
        val ulbModel = ULBModel()
        for (i in 1..20) {

            ulbModel.ULB_CODE = i
            if (i < 10) {
                ulbModel.ULB_NAME = "0$i"
            } else {
                ulbModel.ULB_NAME = "$i"
            }


            ULBDataHelper.saveULBData(ulbModel, applicationContext)
        }
    }*/


    private fun loginCheck(): Boolean {
        if (activityLoginBinding.edtLoginId.getText().toString()
                .isEmpty() && activityLoginBinding.edtPassword.getText().toString()
                .isEmpty()
        ) {
            Utility.snackBar(
                activityLoginBinding.layoutRoot,
                applicationContext.resources.getString(R.string.enter_both),
                1200,
                applicationContext.resources.getColor(R.color.warning)
            )
            return false
        } else if (activityLoginBinding.edtLoginId.getText().toString().isEmpty()) {
            Utility.snackBar(
                activityLoginBinding.layoutRoot,
                applicationContext.resources.getString(R.string.enter_login_id),
                1200,
                applicationContext.resources.getColor(R.color.warning)
            )
            return false
        } else if (activityLoginBinding.edtPassword.getText().toString().isEmpty()) {
            Utility.snackBar(
                activityLoginBinding.layoutRoot,
                applicationContext.resources.getString(R.string.enter_password),
                1200,
                applicationContext.resources.getColor(R.color.warning)
            )
            return false
        }
        return true
    }


    private val isExternalStorageReadOnly: Boolean
        get() {
            val extStorageState = Environment.getExternalStorageState()
            return if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
                true
            } else {
                false
            }
        }
    private val isExternalStorageAvailable: Boolean
        get() {
            val extStorageState = Environment.getExternalStorageState()
            return if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
                true
            } else {
                false
            }
        }

}