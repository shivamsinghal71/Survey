package com.example.myapp.dbhelper

import android.content.Context
import com.example.myapp.model.SurveyDataModel

class SurveyDataHelper {

    companion object {
        fun saveSuvreyData(surveyDataModel: SurveyDataModel?, context: Context?): Boolean {
            var a = false
            try {
                val surveyDataSource = SurveyDataSource.instance
                a = surveyDataSource!!.saveSuvreyData(surveyDataModel!!, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }

            return a
        }

        fun getByUniqueId(accountNo: String, context: Context?): SurveyDataModel? {
            return SurveyDataSource.instance!!.getByUniqueId(accountNo, context)
        }


        fun getByCreatedUniqueNumber(unique_id: String, context: Context?): SurveyDataModel? {
            return SurveyDataSource.instance!!.getByCreatedUniqueNumber(unique_id, context)
        }



        fun getAll(
            isVisited: Int, isUploaded: Int,context: Context?
        ): ArrayList<SurveyDataModel>? {
            return SurveyDataSource.instance?.getAll(isVisited,isUploaded,context)
        }

        fun deleteAll(context: Context?) {
            SurveyDataSource.instance!!.deleteAll(context)
        }

        fun getRowCount(isVisited: Int, isUploaded: Int, context: Context): Int {
            return SurveyDataSource.instance!!.getRowCount(isVisited, isUploaded, context)
        }


    }
}