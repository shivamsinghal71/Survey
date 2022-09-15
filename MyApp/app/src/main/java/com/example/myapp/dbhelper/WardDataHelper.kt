package com.example.myapp.dbhelper

import android.content.Context
import com.example.myapp.model.SurveyDataModel
import com.example.myapp.model.WardModel
import com.example.myapp.model.ZoneModel

class WardDataHelper
{
    companion object {
        fun saveWardData(wardModel: WardModel?, context: Context?): Boolean {
            var a = false
            try {
                val wardDataSource = WardDataSource.instance
                a = wardDataSource!!.saveWardData(wardModel!!, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }

            return a
        }


        fun getAll(context: Context?
        ): ArrayList<WardModel>? {
            return WardDataSource.instance?.getAll(context)
        }

        fun deleteAll(context: Context?) {
            WardDataSource.instance!!.deleteAll(context)
        }


    }
}