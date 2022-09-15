package com.example.myapp.dbhelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import com.example.myapp.model.DistModel
import com.example.myapp.model.ULBModel
import com.example.myapp.model.ZoneModel

class ULBDataSource {


    companion object{
        private var hasObject = false
        private var myDataSource : ULBDataSource? = null
        private val TAG = ULBDataSource::class.java.simpleName
        val instance : ULBDataSource?
            get() = if (hasObject){
                myDataSource
            }else{
                hasObject = true
                myDataSource = ULBDataSource()
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

    fun saveULBData(ulbModel: ULBModel, context: Context?): Boolean{
        var a = false
        synchronized(DatabaseHelper.lock){
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.ULB_NAME,ulbModel.ULB_NAME)
                values.put(DatabaseHelper.ULB_CODE,ulbModel.ULB_CODE)

                val db = getSQLiteDb(true, context)
                val l : Long = db.insertWithOnConflict(DatabaseHelper.ULB_MST,
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
            DatabaseHelper.ULB_CODE,
            DatabaseHelper.ULB_NAME
        )

    private fun cursorData(cursor: Cursor) : ULBModel {
        val ulbModel = ULBModel()

        ulbModel.ULB_CODE = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.ULB_CODE))
        ulbModel.ULB_NAME = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ULB_NAME))

        return ulbModel
    }


    fun deleteAll(context: Context?){
        synchronized(DatabaseHelper.lock) {
            try {
                val db = getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + DatabaseHelper.ULB_MST)
                // db.close;
            } catch (var8: android.database.SQLException) {
                var8.printStackTrace()
            }
        }
    }

    fun getAll(context: Context?): ArrayList<ULBModel>? {
        synchronized(DatabaseHelper.lock) {
            val itemList: ArrayList<ULBModel> = ArrayList<ULBModel>()
            val db: SQLiteDatabase =getSQLiteDb(
                false,
                context
            )
            try {
                val query = "SELECT * FROM ${DatabaseHelper.ULB_MST}"
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