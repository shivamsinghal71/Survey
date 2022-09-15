package com.example.myapp.dbhelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import com.example.myapp.model.DistModel
import com.example.myapp.model.PropertyDetailsModel
import com.example.myapp.model.ULBModel
import com.example.myapp.model.ZoneModel
import org.apache.poi.ss.formula.functions.BooleanFunction.OR

class PropertyDetailsDataSource {


    companion object{
        private var hasObject = false
        private var myDataSource : PropertyDetailsDataSource? = null
        private val TAG = PropertyDetailsDataSource::class.java.simpleName
        val instance : PropertyDetailsDataSource?
            get() = if (hasObject){
                myDataSource
            }else{
                hasObject = true
                myDataSource = PropertyDetailsDataSource()
                myDataSource
            }

        fun getSQLiteDb(isWritable: Boolean, context: Context?): SQLiteDatabase {
            val databaseHelper = DatabaseHelper.getInstance(context!!)
            return if (isWritable){
                databaseHelper!!.writableDatabase
            }else{
                databaseHelper!!.readableDatabase
            }
        }
    }

    fun savePropertyDetailsData(propertyDetailsModel: PropertyDetailsModel, context: Context?): Boolean{
        var a = false
        synchronized(DatabaseHelper.lock){
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.PM_PROP_NO,propertyDetailsModel.PM_PROP_NO)
                values.put(DatabaseHelper.PM_PROP_OLDPROPNO,propertyDetailsModel.PM_PROP_OLDPROPNO)
                values.put(DatabaseHelper.Owner_Name,propertyDetailsModel.Owner_Name)
                values.put(DatabaseHelper.Father_Name,propertyDetailsModel.Father_Name)
                values.put(DatabaseHelper.Mobile_Number,propertyDetailsModel.Mobile_Number)
                values.put(DatabaseHelper.Email,propertyDetailsModel.Email)
                values.put(DatabaseHelper.Property_Location_Address,propertyDetailsModel.Property_Location_Address)
                values.put(DatabaseHelper.Location,propertyDetailsModel.Location)
                values.put(DatabaseHelper.LANDMARK,propertyDetailsModel.LANDMARK)
                values.put(DatabaseHelper.House_No,propertyDetailsModel.House_No)
                values.put(DatabaseHelper.Kharsra_No,propertyDetailsModel.Kharsra_No)

                val db = getSQLiteDb(true, context)
                val l : Long = db.insertWithOnConflict(DatabaseHelper.PROPERTY_DATA_MST,
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE
                )

                if (l > 0) a = true

            }catch (se : SQLiteException){
                se.printStackTrace()
            }

            return a
        }
    }


    private val queryData: Array<String>
        get() = arrayOf(
            DatabaseHelper.PM_PROP_NO,
            DatabaseHelper.PM_PROP_OLDPROPNO,
            DatabaseHelper.Owner_Name,
            DatabaseHelper.Father_Name,
            DatabaseHelper.Mobile_Number,
            DatabaseHelper.Email,
            DatabaseHelper.Property_Location_Address,
            DatabaseHelper.Location,
            DatabaseHelper.LANDMARK,
            DatabaseHelper.House_No,
            DatabaseHelper.Kharsra_No
        )

    private fun cursorData(cursor: Cursor) : PropertyDetailsModel {
        val propertyDetailsModel = PropertyDetailsModel()

        propertyDetailsModel.PM_PROP_NO = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PM_PROP_NO))
        propertyDetailsModel.PM_PROP_OLDPROPNO = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PM_PROP_OLDPROPNO))
        propertyDetailsModel.Owner_Name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.Owner_Name))
        propertyDetailsModel.Father_Name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.Father_Name))
        propertyDetailsModel.Mobile_Number = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.Mobile_Number))
        propertyDetailsModel.Email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.Email))
        propertyDetailsModel.Property_Location_Address = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.Property_Location_Address))
        propertyDetailsModel.Location = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.Location))
        propertyDetailsModel.LANDMARK = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.LANDMARK))
        propertyDetailsModel.House_No = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.House_No))
        propertyDetailsModel.Kharsra_No = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.Kharsra_No))

        return propertyDetailsModel
    }


    fun deleteAll(context: Context?){
        synchronized(DatabaseHelper.lock) {
            try {
                val db = getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + DatabaseHelper.PROPERTY_DATA_MST)
                // db.close;
            } catch (var8: android.database.SQLException) {
                var8.printStackTrace()
            }
        }
    }

    fun getByUniqueId(unique_id :  String , context: Context?): ArrayList<PropertyDetailsModel>? {
        synchronized(DatabaseHelper.lock) {
            val itemList: ArrayList<PropertyDetailsModel> = ArrayList<PropertyDetailsModel>()
            val db: SQLiteDatabase =getSQLiteDb(
                false,
                context
            )
            try {
               // val query = "SELECT * FROM ${DatabaseHelper.PROPERTY_DATA_MST}"
                val cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.PROPERTY_DATA_MST + " WHERE " + DatabaseHelper.PM_PROP_NO + " = '" + unique_id + "'" + " OR " + DatabaseHelper.Mobile_Number + " = '" + unique_id + "'" + " OR " + DatabaseHelper.PM_PROP_OLDPROPNO + " = '" + unique_id + "'"  , null)
                if (cursor.moveToFirst()) {
                    do {
                        itemList.add(cursorData(cursor))
                    } while (cursor.moveToNext())
                }
                cursor.close()
            } catch (se: SQLException) {
                se.printStackTrace()
            }
            return itemList
        }
    }
}