package com.example.myapp.dbhelper

import android.content.Context
import com.example.myapp.model.DistModel
import com.example.myapp.model.TabCModel
import com.example.myapp.model.ZoneModel

class TabCDataHelper {
    companion object {
        fun saveTabCData(tabCModel: TabCModel?, context: Context?): Boolean {
            var a = false
            try {
                val tabCDataSource = TabCDataSource.instance
                a = tabCDataSource!!.saveTabCData(tabCModel!!, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }

            return a
        }

    }
}