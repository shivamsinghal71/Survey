package com.example.myapp.dbhelper

import android.content.Context
import com.example.myapp.model.ZoneModel

class ZoneDataHelper {
    companion object {
        fun saveZoneData(zoneModel: ZoneModel?, context: Context?): Boolean {
            var a = false
            try {
                val zoneDataSource = ZoneDataSource.instance
                a = zoneDataSource!!.saveZoneData(zoneModel!!, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }

            return a
        }


        fun getAll(context: Context?
        ): ArrayList<ZoneModel>? {
            return ZoneDataSource.instance?.getAll(context)
        }

        fun deleteAll(context: Context?) {
            ZoneDataSource.instance!!.deleteAll(context)
        }


    }
}