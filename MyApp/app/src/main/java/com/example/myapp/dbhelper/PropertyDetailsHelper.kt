package com.example.myapp.dbhelper

import android.content.Context
import com.example.myapp.model.PropertyDetailsModel
import com.example.myapp.model.ULBModel
import com.example.myapp.model.ZoneModel

class PropertyDetailsHelper {
    companion object {
        fun savePropertyDetailsData(propertyDetailsModel: PropertyDetailsModel?, context: Context?): Boolean {
            var a = false
            try {
                val propertyDetailsDataSource = PropertyDetailsDataSource.instance
                a = propertyDetailsDataSource!!.savePropertyDetailsData(propertyDetailsModel!!, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }

            return a
        }


        fun getByUniqueId(unique_id : String , context: Context?
        ): ArrayList<PropertyDetailsModel>? {
            return PropertyDetailsDataSource.instance?.getByUniqueId(unique_id, context)
        }


        fun deleteAll(context: Context?) {
            PropertyDetailsDataSource.instance!!.deleteAll(context)
        }


    }
}