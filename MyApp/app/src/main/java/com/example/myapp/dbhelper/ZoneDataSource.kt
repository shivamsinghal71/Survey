package com.example.myapp.dbhelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import com.example.myapp.model.ZoneModel

class ZoneDataSource {


    companion object{
        private var hasObject = false
        private var myDataSource : ZoneDataSource? = null
        private val TAG = ZoneDataSource::class.java.simpleName
        val instance : ZoneDataSource?
            get() = if (hasObject){
                myDataSource
            }else{
                hasObject = true
                myDataSource = ZoneDataSource()
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

    fun saveZoneData(zoneModel: ZoneModel, context: Context?): Boolean{
        var a = false
        synchronized(DatabaseHelper.lock){
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.ZONE_NAME,zoneModel.ZONE_NAME)
                values.put(DatabaseHelper.ZONE_CODE,zoneModel.ZONE_CODE)

                val db = getSQLiteDb(true, context)
                val l : Long = db.insertWithOnConflict(DatabaseHelper.ZONE_MST,
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
            DatabaseHelper.ZONE_CODE,
            DatabaseHelper.ZONE_NAME
        )

    private fun cursorData(cursor: Cursor) : ZoneModel {
        val zoneModel = ZoneModel()

        zoneModel.ZONE_CODE = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.ZONE_CODE))
        zoneModel.ZONE_NAME = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ZONE_NAME))

        return zoneModel
    }


    fun deleteAll(context: Context?){
        synchronized(DatabaseHelper.lock) {
            try {
                val db = getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + DatabaseHelper.ZONE_MST)
                // db.close;
            } catch (var8: android.database.SQLException) {
                var8.printStackTrace()
            }
        }
    }

    fun getAll(context: Context?): ArrayList<ZoneModel>? {
        synchronized(DatabaseHelper.lock) {
            val itemList: ArrayList<ZoneModel> = ArrayList<ZoneModel>()
            val db: SQLiteDatabase =getSQLiteDb(
                false,
                context
            )
            try {
                val query = "SELECT * FROM ${DatabaseHelper.ZONE_MST}"
                val cursor = db.rawQuery(query, null)
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