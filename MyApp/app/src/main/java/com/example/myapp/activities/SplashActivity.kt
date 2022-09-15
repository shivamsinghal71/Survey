package com.example.myapp.activities

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.myapp.Common.Utility
import com.example.myapp.R
import com.example.myapp.databinding.ActivitySplashBinding
import com.example.myapp.model.UserInfoModel
import com.inventia.ugo_mici.dbhelper.UserDataHelper
import java.io.File


class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    var myAlertDialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            window.statusBarColor = ContextCompat.getColor(this@SplashActivity, R.color.black)
        }
        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler().postDelayed({
            accessPage()
        }, 3000) // 3000 is the delayed time in milliseconds.
    }

    private fun accessPage() {

        val mydir = File(
            applicationContext.getExternalFilesDir("Property Details")!!.absolutePath
        )
        if (!mydir.exists()) {
            mydir.mkdirs()
        }

        val model: UserInfoModel? = UserDataHelper.getLogin(this@SplashActivity)
        if (model != null) {
            intent = Intent(this@SplashActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }




        /* private fun askForStoragePermission() {
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
                         accessPage()
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
                         accessPage()
                     }
                 }
             }
             else
             {

             }
         }*/
}