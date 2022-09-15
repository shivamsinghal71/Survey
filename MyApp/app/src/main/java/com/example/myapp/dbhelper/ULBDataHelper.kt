package com.example.myapp.dbhelper

import android.content.Context
import com.example.myapp.model.ULBModel
import com.example.myapp.model.ZoneModel

class ULBDataHelper {
    companion object {
        fun saveULBData(ulbModel: ULBModel?, context: Context?): Boolean {
            var a = false
            try {
                val ulbDataSource = ULBDataSource.instance
                a = ulbDataSource!!.saveULBData(ulbModel!!, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }

            return a
        }


        fun getAll(context: Context?
        ): ArrayList<ULBModel>? {
            return ULBDataSource.instance?.getAll(context)
        }

        fun deleteAll(context: Context?) {
            ULBDataSource.instance!!.deleteAll(context)
        }


    }
}