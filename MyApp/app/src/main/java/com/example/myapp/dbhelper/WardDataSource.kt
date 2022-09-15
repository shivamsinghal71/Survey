package com.example.myapp.dbhelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import com.example.myapp.model.WardModel
import com.example.myapp.model.ZoneModel

class WardDataSource {

    companion object{
        private var hasObject = false
        private var myDataSource : WardDataSource? = null
        private val TAG = WardDataSource::class.java.simpleName
        val instance : WardDataSource?
            get() = if (hasObject){
                myDataSource
            }else{
                hasObject = true
                myDataSource = WardDataSource()
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

    fun saveWardData(wardModel: WardModel, context: Context?): Boolean{
        var a = false
        synchronized(DatabaseHelper.lock){
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.WARD_NAME,wardModel.WARD_NAME)
                values.put(DatabaseHelper.WARD_CODE,wardModel.WARD_CODE)

                val db = getSQLiteDb(true, context)
                val l : Long = db.insertWithOnConflict(DatabaseHelper.WARD_MST,
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
            DatabaseHelper.WARD_CODE,
            DatabaseHelper.WARD_NAME
        )

    private fun cursorData(cursor: Cursor) : WardModel {
        val wardModel = WardModel()

        wardModel.WARD_CODE = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.WARD_CODE))
        wardModel.WARD_NAME = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.WARD_NAME))

        return wardModel
    }


    fun deleteAll(context: Context?){
        synchronized(DatabaseHelper.lock) {
            try {
                val db = getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + DatabaseHelper.WARD_MST)
                // db.close;
            } catch (var8: android.database.SQLException) {
                var8.printStackTrace()
            }
        }
    }

    fun getAll(context: Context?): ArrayList<WardModel>? {
        synchronized(DatabaseHelper.lock) {
            val itemList: ArrayList<WardModel> = ArrayList<WardModel>()
            val db: SQLiteDatabase =getSQLiteDb(
                false,
                context
            )
            try {
                val query = "SELECT * FROM ${DatabaseHelper.WARD_MST}"
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