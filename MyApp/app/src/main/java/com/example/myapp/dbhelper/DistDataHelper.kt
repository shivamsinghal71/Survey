package com.example.myapp.dbhelper

import android.content.Context
import com.example.myapp.model.DistModel
import com.example.myapp.model.ZoneModel

class DistDataHelper {
    companion object {
        fun saveDistData(distModel: DistModel?, context: Context?): Boolean {
            var a = false
            try {
                val distDataSource = DistDataSource.instance
                a = distDataSource!!.saveDistData(distModel!!, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }

            return a
        }


        fun getAll(context: Context?
        ): ArrayList<DistModel>? {
            return DistDataSource.instance?.getAll(context)
        }

        fun deleteAll(context: Context?) {
            DistDataSource.instance!!.deleteAll(context)
        }


    }
}