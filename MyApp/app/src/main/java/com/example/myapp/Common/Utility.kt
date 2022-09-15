package com.example.myapp.Common

import android.Manifest
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.media.ExifInterface
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.myapp.app.MyApplication
import com.example.myapp.R
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


object Utility {
    private const val MY_PREFS_NAME = "MyPrefsFile"
    private val TAG = Utility::class.java.simpleName
    private const val update = false
    private const val cancel = 0

    //  private static Iterator<Row> rowIter;
    private val jsonString: JSONObject? = null
    private const val uploadCount = 0
    private const val errCount = 0
    fun playBeep(context: Context, beepSoundFile: String?) {
        var m = MediaPlayer()
        try {
            if (m.isPlaying) {
                m.stop()
                m.release()
                m = MediaPlayer()
            }
            val descriptor = context.assets.openFd(beepSoundFile ?: "beep.mp3")
            m.setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
            descriptor.close()
            m.prepare()
            m.setVolume(1f, 1f)
            m.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideSoftKeyboard(context: Context, view: View?) {
        if (view != null) {
            if (view is EditText) {
                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
                view.clearFocus()
            }
        }
    }


    fun addFragment(fragment: Fragment?, fragmentManager: FragmentManager, resId: Int) {
        try {
            if (fragment != null) {
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.add(resId, fragment).commit()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun replaceFragment(fragment: Fragment?, fragmentManager: FragmentManager, resId: Int) {
        if (fragment != null) {
            val fragmentTransaction = fragmentManager.beginTransaction()
            //   fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            fragmentTransaction.replace(resId, fragment)
            // fragmentTransaction.commit();
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commitAllowingStateLoss()
        }
    }

    @JvmStatic
    fun replaceFragmentWithBundle(
        fragment: Fragment?,
        fragmentManager: FragmentManager,
        resId: Int,
        bundle: Bundle
    ) {
        if (fragment != null) {
            val fragmentTransaction = fragmentManager.beginTransaction()
            //   fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            fragment.arguments = bundle
            fragmentTransaction.replace(resId, fragment)
            // fragmentTransaction.commit();
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commitAllowingStateLoss()
        }
    }

    fun clearAllFragment(fragmentActivity: FragmentActivity) {
        val fm = fragmentActivity.supportFragmentManager // or 'getSupportFragmentManager();'
        val count = fm.backStackEntryCount
        for (i in 0 until count) {
            fm.popBackStack()
        }
    }

    val currentDate: String?
        get() = dateToString(Date())


    fun dateToString(date: Date?): String? {
        var date1: String? = null
        try {
            val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
            date1 = formatter.format(date)
        } catch (pe: Exception) {
            pe.printStackTrace()
        }
        return date1
    }


    fun dateTimeToString(date: Date?): String? {
        var date1: String? = null
        try {
            val formatter = SimpleDateFormat("dd-MM-yyyy-hh-mm-ss", Locale.ENGLISH)
            date1 = formatter.format(date)
        } catch (pe: Exception) {
            pe.printStackTrace()
        }
        return date1
    }

    fun isNullOrEmpty(var0: String?): Boolean {
        return var0 == null || var0.trim { it <= ' ' }.isEmpty() || var0.trim { it <= ' ' }
            .equals("null", ignoreCase = true)
    }


    fun bitmapToBASE64(bitmap: Bitmap?): String {
        return if (bitmap != null) {
            val baos = ByteArrayOutputStream()
            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val imageBytes = baos.toByteArray()
            Base64.encodeToString(imageBytes, Base64.DEFAULT)
        } else ""
    }

    fun snackBar(view: View?, msg: String?, duration: Int?, color: Int) {
        val sb = Snackbar.make(view!!, msg!!, duration!!)
        val sbView = sb.view
        sbView.setBackgroundColor(color)
        sb.show()
    }

    fun getBooleanValue(value: String?): Boolean {
        var i = false
        try {
            i = java.lang.Boolean.parseBoolean(value)
        } catch (e: Exception) {
        }
        return i
    }


    fun setPic(imagePath: String?, maxHeight: Int, maxWidth: Int) {
        try {
            var photoBm = getBitmap(imagePath)
            val bmOriginalWidth = photoBm!!.width
            val bmOriginalHeight = photoBm.height
            val originalWidthToHeightRatio = 1.0 * bmOriginalWidth / bmOriginalHeight
            val originalHeightToWidthRatio = 1.0 * bmOriginalHeight / bmOriginalWidth
            photoBm = getScaledBitmap(
                photoBm, bmOriginalWidth, bmOriginalHeight,
                originalWidthToHeightRatio, originalHeightToWidthRatio,
                maxHeight, maxWidth
            )
            val bytes = ByteArrayOutputStream()
            photoBm!!.compress(Bitmap.CompressFormat.PNG, 80, bytes)
            val fo = FileOutputStream(imagePath)
            fo.write(bytes.toByteArray())
            fo.close()
        } catch (se: Exception) {
        }
    }


    fun getScaledBitmap(
        bm: Bitmap?,
        bmOriginalWidth: Int,
        bmOriginalHeight: Int,
        originalWidthToHeightRatio: Double,
        originalHeightToWidthRatio: Double,
        maxHeight: Int,
        maxWidth: Int
    ): Bitmap? {
        var bm = bm
        if (bmOriginalWidth > maxWidth || bmOriginalHeight > maxHeight) {
            Log.v(
                TAG,
                String.format("RESIZING bitmap FROM %sx%s ", bmOriginalWidth, bmOriginalHeight)
            )
            bm = if (bmOriginalWidth > bmOriginalHeight) {
                scaleDeminsFromWidth(
                    bm,
                    maxWidth,
                    bmOriginalHeight,
                    originalHeightToWidthRatio
                )
            } else {
                scaleDeminsFromHeight(
                    bm,
                    maxHeight,
                    bmOriginalHeight,
                    originalWidthToHeightRatio
                )
            }
            Log.v(TAG, String.format("RESIZED bitmap TO %sx%s ", bm!!.width, bm.height))
        }
        return bm
    }

    private fun scaleDeminsFromHeight(
        bm: Bitmap?,
        maxHeight: Int,
        bmOriginalHeight: Int,
        originalWidthToHeightRatio: Double
    ): Bitmap? {
        var bm = bm
        val newHeight = Math.min(maxHeight, bmOriginalHeight)
        val newWidth = (newHeight * originalWidthToHeightRatio).toInt()
        bm = Bitmap.createScaledBitmap(bm!!, newWidth, newHeight, true)
        return bm
    }

    private fun scaleDeminsFromWidth(
        bm: Bitmap?,
        maxWidth: Int,
        bmOriginalWidth: Int,
        originalHeightToWidthRatio: Double
    ): Bitmap? {
        //scale the width
        var bm = bm
        val newWidth = Math.min(maxWidth, bmOriginalWidth)
        val newHeight = (newWidth * originalHeightToWidthRatio).toInt()
        bm = Bitmap.createScaledBitmap(bm!!, newWidth, newHeight, true)
        return bm
    }

    fun getBitmapAsByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream)
        return outputStream.toByteArray()
    }

    fun getBitmap(path: String?): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.ARGB_8888
            bitmap = BitmapFactory.decodeFile(path, options)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }

    fun getRotation(photoPath: String?): Int {
        var ei: ExifInterface? = null
        var rotation = 0
        try {
            ei = ExifInterface(photoPath!!)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        var orientation = 0
        if (ei != null) {
            orientation = ei.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
        }
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotation = 90
            ExifInterface.ORIENTATION_ROTATE_180 -> rotation = 180
            ExifInterface.ORIENTATION_ROTATE_270 -> rotation = 270
            ExifInterface.ORIENTATION_TRANSVERSE -> rotation = -90
        }
        return rotation
    }

    fun rotateImage(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }

    fun deleteImages(context: Context): Boolean {
        try {
            val dir = File(
                Environment.getExternalStorageDirectory()
                    .toString() + "/Android/media/com.inventive.ugobill/" + context.resources.getString(
                    R.string.app_name
                )
            )
            if (dir.isDirectory) {
                for (file in dir.listFiles()) {
                    file.delete()
                }
                return true
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return false
    }


    fun deleteSignature(context: Context): Boolean {
        try {
            val dir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString())
            if (dir.isDirectory) {
                for (file in dir.listFiles()) {
                    file.delete()
                }
                return true
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return false
    }

    fun getBitmapByStringImage(base64: String?): Bitmap {
        val decodedString = Base64.decode(base64, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }

    fun isValidPinCode(view: View?, mobile: String): Boolean {
        val check: Boolean
        val pinCodeRegex = "[1-9][0-9]{5}"
        check = mobile.matches(pinCodeRegex.toRegex())
        if (!check) {
            snackBar(view, "Please Enter Valid PinCode", 1200, Color.parseColor("#f32013"))
        }
        return check
    }

    fun isValidMail(view: View?, email: String?): Boolean {
        val check: Boolean
        val p: Pattern
        val m: Matcher
        val emailRegex = "[a-zA-Z0-9][a-zA-Z0-9_.]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+"
        val EMAIL_STRING = ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        p = Pattern.compile(emailRegex)
        m = p.matcher(email)
        check = m.matches()
        if (!check) {
            snackBar(view, "Email ID is Not Valid", 1000, Color.parseColor("#f32013"))
        }
        return check
    }


    fun isValidMobile(view: View?, mobile: String): Boolean {
        val check: Boolean
        val mobileRegex = "[6-9][0-9]{9}"
        check = mobile.matches(mobileRegex.toRegex())
        return check
    }

    fun isOnline(context: Context): Boolean {
        var connection = false
        try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            connection = netInfo != null && netInfo.isConnectedOrConnecting
            if (connection) {

                val wifiManager =
                    MyApplication.mInstance?.applicationContext?.getSystemService(
                        Context.WIFI_SERVICE
                    ) as WifiManager
//                val ssid = wifiManager.connectionInfo.ssid
//                if (ssid.contains("INVENTIA")) connection = false
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return connection
    }

    fun saveStringPreference(key: String?, value: String?, mActivity: FragmentActivity?) {
        val editor: SharedPreferences.Editor = mActivity?.getSharedPreferences(
            MY_PREFS_NAME, Context.MODE_PRIVATE
        )!!.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun saveIntPreference(key: String?, value: Int, mActivity: FragmentActivity?) {
        val editor: SharedPreferences.Editor = mActivity?.getSharedPreferences(
            MY_PREFS_NAME, Context.MODE_PRIVATE
        )!!.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getIntPreference(key: String?, mActivity: FragmentActivity?): Int {
        return mActivity?.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)
            ?.getInt(key, 0)!!
    }


    fun getStringPreference(key: String?, mActivity: FragmentActivity?): String {
        return mActivity?.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)
            ?.getString(key, null)!!
    }


    fun startProgress(context: Context?): Dialog? {
        val dialog: Dialog = ProgressDialog(context, R.style.MyAlertDialogStyle)
        val inflater = LayoutInflater.from(context)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        dialog.setContentView(inflater.inflate(R.layout.layout_dialog_h, null))
        val bar = dialog.findViewById<View>(R.id.progressBar) as ProgressBar
        return dialog
    }

    fun stopProgress(dialog: Dialog) {
        try {
            dialog.dismiss()
        } catch (e: java.lang.Exception) {
        }
        // return 1;
    }

    fun isPermissionGranted(context: Context?): Boolean? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val readExtStorage = ContextCompat.checkSelfPermission(
                context!!,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            val writeExtStorage = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            readExtStorage == PackageManager.PERMISSION_GRANTED && writeExtStorage == PackageManager.PERMISSION_GRANTED
        }
    }
}