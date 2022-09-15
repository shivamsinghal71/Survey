package com.example.myapp.async

import android.content.Context
import android.os.AsyncTask
import android.provider.MediaStore.Images.Media.getBitmap
import androidx.core.view.ViewCompat.getRotation
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage
import com.example.myapp.Common.Utility.bitmapToBASE64
import com.example.myapp.Common.Utility.deleteImages
import com.example.myapp.Common.Utility.deleteSignature
import com.example.myapp.Common.Utility.getBitmap
import com.example.myapp.Common.Utility.getRotation
import com.example.myapp.Common.Utility.setPic
import com.example.myapp.dbhelper.SurveyDataHelper
import com.example.myapp.listner.PhotoCompressedListener
import com.example.myapp.model.SurveyDataModel
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.ref.WeakReference
import java.util.*

class ImageAsync : AsyncTask<Void?, Void?, String?> {
    private var isSignature: Boolean
    private var isReading = false
    private var maxHeight = 500
    private var maxWidth = 500
    private var path: String?
    private var receiverName: String? = null
    private var context:Context?=null
    private var photoCompressedListener: PhotoCompressedListener?
    private  var surveyDataModel: SurveyDataModel?=null
    private var photoPath: File
    private var firstImg = false
    private var secondImg = false
    private var thirdImg = false


    constructor(
        context: Context,
        photoPath: File,
        firstImg: Boolean,
        surveyDataModel: SurveyDataModel,
        isSignature: Boolean,
        photoCompressedListener: PhotoCompressedListener?
    ) {
        path = photoPath.absolutePath
        this.firstImg = firstImg
        this.photoPath = photoPath
        this.isSignature = isSignature
        this.surveyDataModel = surveyDataModel
        this.context=context
        this.photoCompressedListener = photoCompressedListener
    }



    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg voids: Void?): String? {
        if (photoPath.exists() && path != null) {

            val myDir: File = context!!.applicationContext.filesDir
            val externalDirectory: File? =
                context!!.applicationContext.getExternalFilesDir(path)
            val documentsFolder = File(myDir, "UGO CI")
            if (!documentsFolder.exists()) {
                documentsFolder.mkdirs()
            }
            try {
                val destination = File(documentsFolder.path + "/" + externalDirectory?.name)
                val src = FileInputStream(photoPath).channel
                val dst = FileOutputStream(destination).channel
                dst.transferFrom(src, 0, src.size())
                src.close()
                dst.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val numFiles: File = File(
                context!!.applicationContext.filesDir.absolutePath
                    .toString() + "/UGO CI" + "/" + externalDirectory?.name
            )
            path = numFiles.path
            photoPath = numFiles


            if (isSignature) {
                if (photoPath.length() / 1024 > 20) {
                    while (photoPath.length() / 1024 > 20) {
                        setPic(path, maxHeight, maxWidth)
                        maxHeight = maxHeight - 100
                        maxWidth = maxWidth - 100
                    }
                }
            } else {
                val rotation = getRotation(path)
                if (photoPath.length() / 1024 > 200) {
                    while (photoPath.length() / 1024 > 200) {
                        setPic(path, maxHeight, maxWidth)
                        maxHeight = maxHeight - 100
                        maxWidth = maxWidth - 100
                    }
                }
                if (firstImg) surveyDataModel?.PROPERTY_IMAGE = bitmapToBASE64(
                    rotateImage(
                        getBitmap(path)!!, rotation
                    )
                )

            }

            if (isSignature)
            {
                surveyDataModel?.SIGNATURE_IMAGE = bitmapToBASE64(getBitmap(path)!!)
            }
            SurveyDataHelper.saveSuvreyData(surveyDataModel, context!!)
            try {
                deleteImages(context!!!!)
                deleteSignature(context!!!!)
            } catch (e: Exception) {
                e.message
            }
        }
        return path
    }

    override fun onPostExecute(path: String?) {
        super.onPostExecute(path)
        if (photoCompressedListener != null) photoCompressedListener!!.compressedPhoto(path)
    }


}