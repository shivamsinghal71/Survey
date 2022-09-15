package com.example.myapp.dbhelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import com.example.myapp.model.DistModel
import com.example.myapp.model.ZoneModel

class DistDataSource {


    companion object{
        private var hasObject = false
        private var myDataSource : DistDataSource? = null
        private val TAG = DistDataSource::class.java.simpleName
        val instance : DistDataSource?
            get() = if (hasObject){
                myDataSource
            }else{
                hasObject = true
                myDataSource = DistDataSource()
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

    fun saveDistData(distModel: DistModel, context: Context?): Boolean{
        var a = false
        synchronized(DatabaseHelper.lock){
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.DIST_NAME,distModel.DIST_NAME)
                values.put(DatabaseHelper.DIST_CODE,distModel.DIST_CODE)

                val db = getSQLiteDb(true, context)
                val l : Long = db.insertWithOnConflict(DatabaseHelper.DIST_MST,
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
            DatabaseHelper.DIST_CODE,
            DatabaseHelper.DIST_NAME
        )

    private fun cursorData(cursor: Cursor) : DistModel {
        val distModel = DistModel()

        distModel.DIST_CODE = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.DIST_CODE))
        distModel.DIST_NAME = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.DIST_NAME))

        return distModel
    }


    fun deleteAll(context: Context?){
        synchronized(DatabaseHelper.lock) {
            try {
                val db = getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + DatabaseHelper.DIST_MST)
                // db.close;
            } catch (var8: android.database.SQLException) {
                var8.printStackTrace()
            }
        }
    }

    fun getAll(context: Context?): ArrayList<DistModel>? {
        synchronized(DatabaseHelper.lock) {
            val itemList: ArrayList<DistModel> = ArrayList<DistModel>()
            val db: SQLiteDatabase =getSQLiteDb(
                false,
                context
            )
            try {
                val query = "SELECT * FROM ${DatabaseHelper.DIST_MST}"
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