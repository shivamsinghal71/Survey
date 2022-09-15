package com.example.myapp.dbhelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import com.example.myapp.model.DistModel
import com.example.myapp.model.TabCModel
import com.example.myapp.model.ZoneModel

class TabCDataSource {


    companion object{
        private var hasObject = false
        private var myDataSource : TabCDataSource? = null
        private val TAG = TabCDataSource::class.java.simpleName
        val instance : TabCDataSource?
            get() = if (hasObject){
                myDataSource
            }else{
                hasObject = true
                myDataSource = TabCDataSource()
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

    fun saveTabCData(tabCModel: TabCModel, context: Context?): Boolean{
        var a = false
        synchronized(DatabaseHelper.lock){
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.MANJIL_CODE,tabCModel.MANJIL_CODE)
                values.put(DatabaseHelper.MANJIL_ID,tabCModel.MANJIL_ID)
                values.put(DatabaseHelper.CONSTRUCTION_YEAR_C,tabCModel.CONSTRUCTION_YEAR_C)
                values.put(DatabaseHelper.BUILDING_CODE,tabCModel.BUILDING_CODE)
                values.put(DatabaseHelper.FLOOR_CODE,tabCModel.FLOOR_CODE)
                values.put(DatabaseHelper.AREA_C,tabCModel.AREA_C)
                values.put(DatabaseHelper.MANJIL_USES,tabCModel.MANJIL_USES)
                values.put(DatabaseHelper.BUILDING_ID,tabCModel.BUILDING_ID)
                values.put(DatabaseHelper.FLOOR_ID,tabCModel.FLOOR_ID)
                values.put(DatabaseHelper.MANJIL_USES_ID,tabCModel.MANJIL_USES_ID)
                values.put(DatabaseHelper.UNIQUE_ID,tabCModel.UNIQUE_ID)
                values.put(DatabaseHelper.STATUS,tabCModel.STATUS)

                val db = getSQLiteDb(true, context)
                val l : Long = db.insertWithOnConflict(DatabaseHelper.TABC_MST,
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
            DatabaseHelper.MANJIL_CODE,
            DatabaseHelper.MANJIL_ID,
            DatabaseHelper.CONSTRUCTION_YEAR_C,
            DatabaseHelper.BUILDING_CODE,
            DatabaseHelper.FLOOR_CODE,
            DatabaseHelper.AREA_C,
            DatabaseHelper.MANJIL_USES,
            DatabaseHelper.BUILDING_ID,
            DatabaseHelper.FLOOR_ID,
            DatabaseHelper.MANJIL_USES_ID,
            DatabaseHelper.STATUS
        )

    private fun cursorData(cursor: Cursor) : TabCModel {
        val tabCModel = TabCModel()

        tabCModel.MANJIL_CODE = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MANJIL_CODE))
        tabCModel.MANJIL_ID = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.MANJIL_ID))
        tabCModel.CONSTRUCTION_YEAR_C = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.CONSTRUCTION_YEAR_C))
        tabCModel.BUILDING_CODE = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.BUILDING_CODE))
        tabCModel.FLOOR_CODE = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FLOOR_CODE))
        tabCModel.AREA_C = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.AREA_C))
        tabCModel.MANJIL_USES = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MANJIL_USES))
        tabCModel.BUILDING_ID = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.BUILDING_ID))
        tabCModel.FLOOR_ID = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.FLOOR_ID))
        tabCModel.MANJIL_USES_ID = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.MANJIL_USES_ID))
        tabCModel.STATUS = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.STATUS))

        return tabCModel
    }



}