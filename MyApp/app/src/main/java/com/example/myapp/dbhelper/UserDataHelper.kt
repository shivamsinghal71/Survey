package com.inventia.ugo_mici.dbhelper

import android.content.Context
import com.example.myapp.model.UserInfoModel

class UserDataHelper {
        companion object {
            fun saveUserDatabaseData(ciUserModel: UserInfoModel, context: Context?): Boolean {
                var a = false
                try {
                    val ciUserDataSource = UserDataSource.instance
                    a = ciUserDataSource!!.saveUserDatabaseData(ciUserModel, context)
                } catch (et: Exception) {
                    et.printStackTrace()
                }
                return a
            }

            fun getByUserCode(userCode: String, context: Context?): UserInfoModel? {
                return UserDataSource.instance!!.getByUserCode(userCode, context)
            }

            fun getByUserName(mobileNo: String, password : String ,  context: Context?): ArrayList<UserInfoModel>? {
                return UserDataSource.instance!!.getByUserName(mobileNo,password, context)
            }

            fun deleteRow(ciUserModel: UserInfoModel, context: Context?) {
                UserDataSource.instance!!.deleteRow(ciUserModel, context)
            }

            fun deleteAll(context: Context?) {
                UserDataSource.instance!!.deleteAll(context)
            }


            fun getLogin(context: Context?): UserInfoModel? {
                return UserDataSource.instance!!.getLogin(context)
            }
        }
}