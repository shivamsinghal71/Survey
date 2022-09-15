package com.inventia.ugo_mici.dbhelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.example.myapp.dbhelper.DatabaseHelper
import com.example.myapp.dbhelper.DatabaseHelper.Companion.USER_MST
import com.example.myapp.dbhelper.DatabaseHelper.Companion.getInstance
import com.example.myapp.dbhelper.DatabaseHelper.Companion.lock
import com.example.myapp.model.UserInfoModel

class UserDataSource private constructor() {

    companion object {
        private var hasObject = false
        private var myDataSource: UserDataSource? = null
        private val TAG = UserDataSource::class.java.simpleName
        val instance: UserDataSource?
            get() = if (hasObject) {
                myDataSource
            } else {
                hasObject = true
                myDataSource = UserDataSource()
                myDataSource
            }

        fun getSQLiteDb(isWritable: Boolean, context: Context?): SQLiteDatabase {
            val databaseHelper = getInstance(context!!)
            return if (isWritable) {
                databaseHelper!!.writableDatabase
            } else {
                databaseHelper!!.readableDatabase
            }
        }
    }

    fun saveUserDatabaseData(userInfoModel: UserInfoModel, context: Context?): Boolean {
        var a = false
        synchronized(lock) {
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.USER_ID, userInfoModel.USER_ID)
                values.put(DatabaseHelper.USER_NAME, userInfoModel.USER_NAME)
                values.put(DatabaseHelper.MOBILE_NO, userInfoModel.MOBILE_NO)
                values.put(DatabaseHelper.EMAIL_ID, userInfoModel.EMAIL_ID)
                values.put(DatabaseHelper.AADHAR_NO, userInfoModel.AADHAR_NO)


                // check account number if not null
                if (userInfoModel.MOBILE_NO != null) {
                    val ciUserModel1 =
                        getByUserName(userInfoModel.MOBILE_NO!!, userInfoModel.AADHAR_NO!!, context)
                    val db = getSQLiteDb(true, context)
                    var l: Long = 0
                    l = if (ciUserModel1.size == 0) db.insertWithOnConflict(
                        DatabaseHelper.USER_MST,
                        null,
                        values,
                        SQLiteDatabase.CONFLICT_REPLACE
                    ) else db.update(
                        DatabaseHelper.USER_MST,
                        values,
                        DatabaseHelper.MOBILE_NO + "=?",
                        arrayOf(userInfoModel.MOBILE_NO)
                    ).toLong()

                    //db.close;
                    if (l > 0) a = true
                } else {
                    val db = getSQLiteDb(true, context)
                    var l: Long = 0
                    l = db.insertWithOnConflict(
                        DatabaseHelper.MOBILE_NO,
                        null,
                        values,
                        SQLiteDatabase.CONFLICT_REPLACE
                    )
                    //db.close;
                    if (l > 0) a = true
                }
            } catch (se: SQLException) {
                se.printStackTrace()
            }
            return a
        }
    }

    //  add increased column here also
    private val queryData: Array<String>
        private get() = arrayOf(
            DatabaseHelper.ID,
            DatabaseHelper.USER_ID,
            DatabaseHelper.USER_NAME,
            DatabaseHelper.MOBILE_NO,
            DatabaseHelper.EMAIL_ID,
            DatabaseHelper.AADHAR_NO
        )

    private fun cursorData(cursor: Cursor): UserInfoModel {
        val userInfoModel = UserInfoModel()
        userInfoModel.USER_ID =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.USER_ID))
        userInfoModel.USER_NAME =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.USER_NAME))
        userInfoModel.MOBILE_NO =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MOBILE_NO))
        userInfoModel.EMAIL_ID =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.EMAIL_ID))
        userInfoModel.AADHAR_NO =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.AADHAR_NO))
        return userInfoModel
    }

    fun getByUserCode(userCode: String, context: Context?): UserInfoModel? {
        synchronized(lock) {
            var ciUserModel: UserInfoModel? = null
            val db = getSQLiteDb(false, context)
            try {
                val cursor = db.query(
                    DatabaseHelper.USER_MST,
                    queryData,
                    DatabaseHelper.MOBILE_NO + "=?",
                    arrayOf(userCode),
                    null,
                    null,
                    null
                )
                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    ciUserModel = cursorData(cursor)
                }
                cursor.close()
                //db.close;
            } catch (se: SQLException) {
                se.printStackTrace()
            }
            return ciUserModel
        }
    }


    fun getByUserName(
        mobileNo: String, aadharNo: String, context: Context?
    ): ArrayList<UserInfoModel> {
        // please select any type of boolean flagField as a column name and flag value will be corrosponding value
        synchronized(lock) {
            val user_infos: ArrayList<UserInfoModel> = ArrayList<UserInfoModel>()
            val db: SQLiteDatabase = getSQLiteDb(
                false,
                context
            )
            try {
                val cursor = db.rawQuery(
                    "SELECT * FROM " + DatabaseHelper.USER_MST + " WHERE " + DatabaseHelper.MOBILE_NO + " = '" + mobileNo + "'" + " AND " + DatabaseHelper.AADHAR_NO + " = '" + aadharNo + "'",
                    null
                )
                if (cursor.moveToFirst()) {
                    do {
                        user_infos.add(cursorData(cursor))
                    } while (cursor.moveToNext())
                }
                cursor.close()
                //db.close;
            } catch (se: android.database.SQLException) {
                se.printStackTrace()
            }
            return user_infos
        }
    }

    fun deleteRow(ciUserModel: UserInfoModel, context: Context?) {
        synchronized(lock) {
            val db = getSQLiteDb(true, context)
            db.delete(
                DatabaseHelper.USER_MST,
                DatabaseHelper.MOBILE_NO + "=?",
                arrayOf(ciUserModel.MOBILE_NO)
            )
        }
    }

    fun deleteAll(context: Context?) {
        synchronized(lock) {
            try {
                val db = getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + DatabaseHelper.USER_MST)
            } catch (var8: SQLException) {
                var8.printStackTrace()
            }
        }
    }

    fun getLogin(context: Context?): UserInfoModel? {
        var login: UserInfoModel? = null
        synchronized(lock) {
            val db: SQLiteDatabase = getSQLiteDb(false, context)
            try {
                val query = "SELECT * FROM $USER_MST"
                val cursor = db.rawQuery(query, null)
                if (cursor.moveToFirst()) login = cursorData(cursor)
                cursor.close()
                db.close()
            } catch (se: SQLException) {
                se.printStackTrace()
            }
        }
        return login
    }
}