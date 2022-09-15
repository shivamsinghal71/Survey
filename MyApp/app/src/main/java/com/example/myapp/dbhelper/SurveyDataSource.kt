package com.example.myapp.dbhelper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.myapp.Common.Utility
import com.example.myapp.dbhelper.DatabaseHelper.Companion.ID
import com.example.myapp.dbhelper.DatabaseHelper.Companion.ISUPLOADED
import com.example.myapp.dbhelper.DatabaseHelper.Companion.ISVISITED
import com.example.myapp.dbhelper.DatabaseHelper.Companion.SURVEY_DATA_MST
import com.example.myapp.dbhelper.DatabaseHelper.Companion.lock
import com.example.myapp.model.SurveyDataModel
import com.inventia.ugo_mici.dbhelper.UserDataSource
import java.sql.SQLException

class SurveyDataSource
private constructor() {

    companion object {
        private var hasObject = false
        private var myDataSource: SurveyDataSource? = null

        //private val TAG = CIConsumerDataSource::class.java.simpleName
        val instance: SurveyDataSource?
            get() = if (hasObject) {
                myDataSource
            } else {
                hasObject = true
                myDataSource = SurveyDataSource()
                myDataSource
            }

        fun getSQLiteDb(isWritable: Boolean, context: Context?): SQLiteDatabase {
            val databaseHelper = DatabaseHelper.getInstance(context!!)
            return if (isWritable) {
                databaseHelper!!.writableDatabase
            } else {
                databaseHelper!!.readableDatabase
            }
        }
    }

    fun saveSuvreyData(surveyDataModel: SurveyDataModel, context: Context?): Boolean {
        var a = false
        synchronized(lock)
        {
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.LOGIN_ID, surveyDataModel.LOGIN_ID)
                values.put(DatabaseHelper.STATE_NAME, surveyDataModel.STATE_NAME)
                values.put(DatabaseHelper.ULB_NAME, surveyDataModel.ULB_NAME)
                values.put(DatabaseHelper.ULB_CODE, surveyDataModel.ULB_ID)
                values.put(DatabaseHelper.DIST_NAME, surveyDataModel.DIST_NAME)
                values.put(DatabaseHelper.DIST_CODE, surveyDataModel.DIST_ID)
                values.put(DatabaseHelper.ZONE_NAME, surveyDataModel.ZONE_NAME)
                values.put(DatabaseHelper.WARD_NAME, surveyDataModel.WARD_NAME)
                values.put(DatabaseHelper.STATE_ID, surveyDataModel.STATE_ID)
                values.put(DatabaseHelper.ZONE_ID, surveyDataModel.ZONE_ID)
                values.put(DatabaseHelper.WARD_ID, surveyDataModel.WARD_ID)
                values.put(DatabaseHelper.PARINAM_NAME, surveyDataModel.PARINAM_NAME)
                values.put(DatabaseHelper.PARINAM_ID, surveyDataModel.PARINAM_ID)
                values.put(DatabaseHelper.PARINAM_NAME_OTHER, surveyDataModel.PARINAM_NAME_OTHER)
                values.put(DatabaseHelper.NEXT_DATE, surveyDataModel.NEXT_DATE)
                values.put(DatabaseHelper.MAAP_AADHAR_NAME, surveyDataModel.MAAP_AADHAR_NAME)
                values.put(DatabaseHelper.MAAP_AADHAR_ID, surveyDataModel.MAAP_AADHAR_ID)
                values.put(DatabaseHelper.PARCEL_NAME, surveyDataModel.PARCEL_NAME)
                values.put(DatabaseHelper.PARCEL_NOT_IN_MAP, surveyDataModel.PARCEL_NOT_IN_MAP)
                values.put(DatabaseHelper.PROPERTY_NO, surveyDataModel.PROPERTY_NO)
                values.put(DatabaseHelper.KHASRA_NO, surveyDataModel.KHASRA_NO)
                values.put(DatabaseHelper.PROPERTY_ADDRESS, surveyDataModel.PROPERTY_ADDRESS)
                values.put(DatabaseHelper.ROAD_STREET_NO, surveyDataModel.ROAD_STREET_NO)
                values.put(DatabaseHelper.ROAD_STREET_NAME, surveyDataModel.ROAD_STREET_NAME)
                values.put(DatabaseHelper.COLONY_NAME, surveyDataModel.COLONY_NAME)
                values.put(DatabaseHelper.STATION_NAME, surveyDataModel.STATION_NAME)
                values.put(DatabaseHelper.LANDMARK_NAME, surveyDataModel.LANDMARK_NAME)
                values.put(DatabaseHelper.PINCODE, surveyDataModel.PINCODE)
                values.put(
                    DatabaseHelper.LAST_PAYMENT_YEAR_SURVEYOR,
                    surveyDataModel.LAST_PAYMENT_YEAR_SURVEYOR
                )
                values.put(
                    DatabaseHelper.LAST_PAYMENT_DATE_SURVEYOR,
                    surveyDataModel.LAST_PAYMENT_DATE_SURVEYOR
                )
                values.put(DatabaseHelper.RECIEPT_NO_SURVEYOR, surveyDataModel.RECIEPT_NO_SURVEYOR)
                values.put(DatabaseHelper.PAID_TAX_AMOUNT, surveyDataModel.PAID_TAX_AMOUNT)
                values.put(
                    DatabaseHelper.LAST_PAYMENT_YEAR_CORP,
                    surveyDataModel.LAST_PAYMENT_YEAR_CORP
                )
                values.put(
                    DatabaseHelper.LAST_PAYMENT_DATE_CORP,
                    surveyDataModel.LAST_PAYMENT_DATE_CORP
                )
                values.put(DatabaseHelper.RECIEPT_NO_CORP, surveyDataModel.RECIEPT_NO_CORP)
                values.put(DatabaseHelper.PROPERTY_REGISTERED, surveyDataModel.PROPERTY_REGISTERED)
                values.put(DatabaseHelper.RESPONDENT_NAME, surveyDataModel.RESPONDENT_NAME)
                values.put(DatabaseHelper.MOBILE_NO, surveyDataModel.MOBILE_NO)
                values.put(DatabaseHelper.PROPERTY_RELATION, surveyDataModel.PROPERTY_RELATION)
                values.put(DatabaseHelper.PROPERTY_RELATION_OTHER, surveyDataModel.PROPERTY_RELATION_OTHER
                )
                values.put(
                    DatabaseHelper.PROPERTY_RELATION_OTHER_NAME,
                    surveyDataModel.PROPERTY_RELATION_OTHER_NAME
                )
                values.put(
                    DatabaseHelper.PROPERTY_OWNERSHIP_CLASSIFICATION,
                    surveyDataModel.PROPERTY_OWNERSHIP_CLASSIFICATION
                )
                values.put(
                    DatabaseHelper.OTHER_CLASSIFICATION_NAME,
                    surveyDataModel.OTHER_CLASSIFICATION_NAME.toString()
                )
                values.put(DatabaseHelper.LAND_TYPE, surveyDataModel.LAND_TYPE)
                values.put(DatabaseHelper.LAND_TYPE_OTHER, surveyDataModel.LAND_TYPE_OTHER)
                values.put(DatabaseHelper.NAME, surveyDataModel.NAME)
                values.put(DatabaseHelper.HUSBAND_FATHER_NAME, surveyDataModel.HUSBAND_FATHER_NAME)
                values.put(DatabaseHelper.HOUSE_NO, surveyDataModel.HOUSE_NO)
                values.put(DatabaseHelper.ROAD_STREET_NO2, surveyDataModel.ROAD_STREET_NO2)
                values.put(DatabaseHelper.ROAD_STREET_NAME2, surveyDataModel.ROAD_STREET_NAME2)
                values.put(DatabaseHelper.COLONY_NAME2, surveyDataModel.COLONY_NAME2)
                values.put(DatabaseHelper.STATION_NAME2, surveyDataModel.STATION_NAME2)
                values.put(DatabaseHelper.CITY, surveyDataModel.CITY)
                values.put(DatabaseHelper.STATE_NAME2, surveyDataModel.STATION_NAME2)
                values.put(DatabaseHelper.PINCODE2, surveyDataModel.PINCODE2)
                values.put(DatabaseHelper.EMAIL, surveyDataModel.EMAIL)
                values.put(DatabaseHelper.MOBILE_NO2, surveyDataModel.MOBILE_NO2)
                values.put(DatabaseHelper.INCOMETAX_PAYER, surveyDataModel.INCOMETAX_PAYER)
                values.put(DatabaseHelper.FEMALE_MEMBER, surveyDataModel.FEMALE_MEMBER)
                values.put(DatabaseHelper.MALE_MEMBER, surveyDataModel.MALE_MEMBER)
                values.put(DatabaseHelper.FAMILY_GROUP, surveyDataModel.FAMILY_GROUP)
                values.put(DatabaseHelper.FAMILY_RENTAL_GROUP, surveyDataModel.FAMILY_RENTAL_GROUP)
                values.put(DatabaseHelper.PROPERTY_USES, surveyDataModel.PROPERTY_USES)
                values.put(DatabaseHelper.PROPERTY_USES_OTHER, surveyDataModel.PROPERTY_USES_OTHER)
                values.put(
                    DatabaseHelper.PROPERTY_USES_OTHER_REMARK1,
                    surveyDataModel.PROPERTY_USES_OTHER_REMARK1
                )
                values.put(
                    DatabaseHelper.PROPERTY_USES_OTHER_REMARK2,
                    surveyDataModel.PROPERTY_USES_OTHER_REMARK2
                )
                values.put(
                    DatabaseHelper.PROPERTY_USES_OTHER_REMARK3,
                    surveyDataModel.PROPERTY_USES_OTHER_REMARK3
                )
                values.put(DatabaseHelper.CONSTRUCTION_YEAR, surveyDataModel.CONSTRUCTION_YEAR)
                values.put(DatabaseHelper.LAND_AREA, surveyDataModel.LAND_AREA)
                values.put(DatabaseHelper.MANJIL_CODE, surveyDataModel.MANJIL_CODE)
                values.put(DatabaseHelper.CONSTRUCTION_YEAR_C, surveyDataModel.CONSTRUCTION_YEAR_C)
                values.put(DatabaseHelper.BUILDING_CODE, surveyDataModel.BUILDING_CODE)
                values.put(DatabaseHelper.FLOOR_CODE, surveyDataModel.FLOOR_CODE)
                values.put(DatabaseHelper.AREA_C, surveyDataModel.AREA_C)
                values.put(DatabaseHelper.MANJIL_USES, surveyDataModel.MANJIL_USES)
                values.put(DatabaseHelper.DISCOUNT, surveyDataModel.DISCOUNT)
                values.put(DatabaseHelper.DISCOUNT_TYPE, surveyDataModel.DISCOUNT_TYPE)
                values.put(DatabaseHelper.DISCOUNT_TYPE_CODE, surveyDataModel.DISCOUNT_TYPE_CODE)
                values.put(
                    DatabaseHelper.DISCOUNT_TYPE_OTHER_NAME,
                    surveyDataModel.DISCOUNT_TYPE_OTHER_NAME
                )
                values.put(DatabaseHelper.HISTORIC_PROPERTY, surveyDataModel.HISTORIC_PROPERTY)
                values.put(DatabaseHelper.WATER_SUPPLY_TYPE, surveyDataModel.WATER_SUPPLY_TYPE)
                values.put(DatabaseHelper.WATER_SUPPLY_HOURS, surveyDataModel.WATER_SUPPLY_HOURS)
                values.put(DatabaseHelper.WATER_SOURCE, surveyDataModel.WATER_SOURCE)
                values.put(DatabaseHelper.PIPE_THICKNESS, surveyDataModel.PIPE_THICKNESS)
                values.put(DatabaseHelper.WATER_PERIOD, surveyDataModel.WATER_PERIOD)
                values.put(DatabaseHelper.PIPE_PRESSURE, surveyDataModel.PIPE_PRESSURE)
                values.put(DatabaseHelper.WATER_METER, surveyDataModel.WATER_METER)
                values.put(
                    DatabaseHelper.WATER_METER_CONDITION,
                    surveyDataModel.WATER_METER_CONDITION
                )
                values.put(
                    DatabaseHelper.RAIN_WATER_HARVESTING,
                    surveyDataModel.RAIN_WATER_HARVESTING
                )
                values.put(
                    DatabaseHelper.RAIN_WATER_HARVESTING_USES,
                    surveyDataModel.RAIN_WATER_HARVESTING_USES
                )
                values.put(DatabaseHelper.SEWAGE_CONNECTION, surveyDataModel.SEWAGE_CONNECTION)
                values.put(
                    DatabaseHelper.SEWAGE_NETWORK_IF_NOT,
                    surveyDataModel.SEWAGE_NETWORK_IF_NOT
                )
                values.put(DatabaseHelper.TOILET_COUNT, surveyDataModel.TOILET_COUNT)
                values.put(DatabaseHelper.SEWAGE_TYPE, surveyDataModel.SEWAGE_TYPE)
                values.put(DatabaseHelper.TREE_COUNT, surveyDataModel.TREE_COUNT)
                values.put(DatabaseHelper.WATER_TANK, surveyDataModel.WATER_TANK)
                values.put(DatabaseHelper.TWO_WHEELER, surveyDataModel.TWO_WHEELER)
                values.put(DatabaseHelper.FOUR_WHEELER, surveyDataModel.FOUR_WHEELER)
                values.put(DatabaseHelper.BUILDING_PERMIT, surveyDataModel.BUILDING_PERMIT)
                values.put(DatabaseHelper.PROP_IMAGE, surveyDataModel.PROPERTY_IMAGE)
                values.put(DatabaseHelper.SIGN_IMAGE, surveyDataModel.SIGNATURE_IMAGE)
                values.put(DatabaseHelper.PROPERTY_IMAGE_NAME, surveyDataModel.PROPERTY_IMAGE_NAME)
                values.put(
                    DatabaseHelper.SIGNATURE_IMAGE_NAME,
                    surveyDataModel.SIGNATURE_IMAGE_NAME
                )
                values.put(DatabaseHelper.GRID_NO, surveyDataModel.GRID_NO)
                values.put(DatabaseHelper.MAP_ID, surveyDataModel.MAP_ID)
                values.put(DatabaseHelper.UNIQUE_ID_TEXT, surveyDataModel.UNIQUE_ID_TEXT)
                values.put(DatabaseHelper.ULB_P_ID, surveyDataModel.ULB_P_ID)
                values.put(
                    DatabaseHelper.PROPERTY_RELATION_OTHER_ID,
                    surveyDataModel.PROPERTY_RELATION_OTHER_ID
                )
                values.put(
                    DatabaseHelper.PROPERTY_OWNERSHIP_CLASSIFICATION_ID,
                    surveyDataModel.PROPERTY_OWNERSHIP_CLASSIFICATION_ID
                )
                values.put(DatabaseHelper.LAND_TYPE_ID, surveyDataModel.LAND_TYPE_ID)
                values.put(DatabaseHelper.PROPERTY_USES_ID, surveyDataModel.PROPERTY_USES_ID)
                values.put(
                    DatabaseHelper.PROPERTY_USES_OTHER_ID,
                    surveyDataModel.PROPERTY_USES_OTHER_ID
                )
                values.put(DatabaseHelper.MANJIL_ID, surveyDataModel.MANJIL_ID)
                values.put(DatabaseHelper.BUILDING_ID, surveyDataModel.BUILDING_ID)
                values.put(DatabaseHelper.FLOOR_ID, surveyDataModel.FLOOR_ID)
                values.put(DatabaseHelper.MANJIL_USES_ID, surveyDataModel.MANJIL_USES_ID)
                values.put(DatabaseHelper.WATER_SOURCE_ID, surveyDataModel.WATER_SOURCE_ID)
                values.put(DatabaseHelper.PIPE_THICKNESS_ID, surveyDataModel.PIPE_THICKNESS_ID)
                values.put(DatabaseHelper.PIPE_PRESSURE_ID, surveyDataModel.PIPE_PRESSURE_ID)
                values.put(DatabaseHelper.WATER_METER_ID, surveyDataModel.WATER_METER_ID)
                values.put(
                    DatabaseHelper.WATER_METER_CONDITION_ID,
                    surveyDataModel.WATER_METER_CONDITION_ID
                )
                values.put(
                    DatabaseHelper.RAIN_WATER_HARVESTING_USES_ID,
                    surveyDataModel.RAIN_WATER_HARVESTING_USES_ID
                )
                values.put(
                    DatabaseHelper.SEWAGE_NETWORK_IF_NOT_ID,
                    surveyDataModel.SEWAGE_NETWORK_IF_NOT_ID
                )
                values.put(DatabaseHelper.SEWAGE_TYPE_ID, surveyDataModel.SEWAGE_TYPE_ID)
                values.put(ISVISITED, surveyDataModel.ISVISITED)
                values.put(ISUPLOADED, surveyDataModel.ISUPLOADED)
                values.put(DatabaseHelper.UNIQUE_ID, surveyDataModel.UNIQUE_ID)

                if (surveyDataModel.UNIQUE_ID != null) {
                    val surveyDataModel = getByUniqueId(surveyDataModel.UNIQUE_ID!!, context)
                    val db = getSQLiteDb(true, context)

                    val l: Long =
                        if (surveyDataModel == null)
                            db.insertWithOnConflict(
                                DatabaseHelper.SURVEY_DATA_MST,
                                null,
                                values,
                                SQLiteDatabase.CONFLICT_REPLACE
                            )
                        else
                            db.update(
                                DatabaseHelper.SURVEY_DATA_MST,
                                values,
                                "$ID=?",
                                arrayOf(surveyDataModel.id.toString() + "")
                            ).toLong()

                    if (l > 0) a = true
                } else {
                    val db = getSQLiteDb(true, context)
                    val l: Long = db.insertWithOnConflict(
                        DatabaseHelper.SURVEY_DATA_MST,
                        null,
                        values,
                        SQLiteDatabase.CONFLICT_REPLACE
                    )
                    if (l > 0) a = true
                }
            } catch (se: SQLException) {
                se.printStackTrace()
            }
            return a
        }
    }


    @SuppressLint("Range")
    private fun cursorData(cursor: Cursor): SurveyDataModel {
        val surveyDataModel = SurveyDataModel()
        surveyDataModel.id = cursor.getInt(cursor.getColumnIndex(ID))
        surveyDataModel.LOGIN_ID =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.LOGIN_ID))
        surveyDataModel.STATE_NAME =
            cursor.getString(cursor.getColumnIndex(DatabaseHelper.STATE_NAME))
        surveyDataModel.DIST_NAME =
            cursor.getString(cursor.getColumnIndex(DatabaseHelper.DIST_NAME))
        surveyDataModel.DIST_ID = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DIST_CODE))
        surveyDataModel.ULB_NAME = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ULB_NAME))
        surveyDataModel.ULB_ID = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ULB_CODE))
        surveyDataModel.ZONE_NAME =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ZONE_NAME))
        surveyDataModel.WARD_NAME =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.WARD_NAME))
        surveyDataModel.STATE_ID =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.STATE_ID))
        surveyDataModel.ZONE_ID =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ZONE_ID))
        surveyDataModel.WARD_ID = cursor.getString(cursor.getColumnIndex(DatabaseHelper.WARD_ID))
        surveyDataModel.GRID_NO = cursor.getString(cursor.getColumnIndex(DatabaseHelper.GRID_NO))
        surveyDataModel.MAP_ID = cursor.getString(cursor.getColumnIndex(DatabaseHelper.MAP_ID))
        surveyDataModel.UNIQUE_ID_TEXT =
            cursor.getString(cursor.getColumnIndex(DatabaseHelper.UNIQUE_ID_TEXT))
        surveyDataModel.ULB_P_ID = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ULB_P_ID))
        surveyDataModel.PARINAM_NAME =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PARINAM_NAME))
        surveyDataModel.PARINAM_ID =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PARINAM_ID))
        surveyDataModel.PARINAM_NAME_OTHER =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PARINAM_NAME_OTHER))
        surveyDataModel.NEXT_DATE =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.NEXT_DATE))
        surveyDataModel.MAAP_AADHAR_NAME =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MAAP_AADHAR_NAME))
        surveyDataModel.MAAP_AADHAR_ID =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MAAP_AADHAR_ID))
        surveyDataModel.PARCEL_NAME =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PARCEL_NAME))
        surveyDataModel.PARCEL_NOT_IN_MAP =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PARCEL_NOT_IN_MAP))
        surveyDataModel.PROPERTY_NO =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PROPERTY_NO))
        surveyDataModel.KHASRA_NO =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KHASRA_NO))
        surveyDataModel.PROPERTY_ADDRESS =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PROPERTY_ADDRESS))
        surveyDataModel.ROAD_STREET_NO =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ROAD_STREET_NO))
        surveyDataModel.ROAD_STREET_NAME =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ROAD_STREET_NAME))
        surveyDataModel.COLONY_NAME =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLONY_NAME))
        surveyDataModel.STATION_NAME =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.STATION_NAME))
        surveyDataModel.LANDMARK_NAME =
            cursor.getString(cursor.getColumnIndex(DatabaseHelper.LANDMARK_NAME))
        surveyDataModel.PINCODE =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PINCODE))
        surveyDataModel.LAST_PAYMENT_YEAR_SURVEYOR =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.LAST_PAYMENT_YEAR_SURVEYOR))
        surveyDataModel.LAST_PAYMENT_DATE_SURVEYOR =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.LAST_PAYMENT_DATE_SURVEYOR))
        surveyDataModel.RECIEPT_NO_SURVEYOR =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.RECIEPT_NO_SURVEYOR))
        surveyDataModel.PAID_TAX_AMOUNT =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PAID_TAX_AMOUNT))
        surveyDataModel.LAST_PAYMENT_YEAR_CORP =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.LAST_PAYMENT_YEAR_CORP))
        surveyDataModel.LAST_PAYMENT_DATE_CORP =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.LAST_PAYMENT_DATE_CORP))
        surveyDataModel.RECIEPT_NO_CORP =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.RECIEPT_NO_CORP))
        surveyDataModel.PROPERTY_REGISTERED =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PROPERTY_REGISTERED))
        surveyDataModel.RESPONDENT_NAME =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.RESPONDENT_NAME))
        surveyDataModel.PROPERTY_RELATION =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PROPERTY_RELATION))
        surveyDataModel.MOBILE_NO =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MOBILE_NO))
        surveyDataModel.PROPERTY_RELATION_OTHER =
            cursor.getString(cursor.getColumnIndex(DatabaseHelper.PROPERTY_RELATION_OTHER))
        surveyDataModel.PROPERTY_RELATION_OTHER_NAME =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PROPERTY_RELATION_OTHER_NAME))
        surveyDataModel.PROPERTY_OWNERSHIP_CLASSIFICATION =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PROPERTY_OWNERSHIP_CLASSIFICATION))
        surveyDataModel.OTHER_CLASSIFICATION_NAME =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.OTHER_CLASSIFICATION_NAME))
        surveyDataModel.LAND_TYPE =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.LAND_TYPE))
        surveyDataModel.LAND_TYPE_OTHER =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.LAND_TYPE_OTHER))
        surveyDataModel.NAME = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.NAME))
        surveyDataModel.HUSBAND_FATHER_NAME =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.HUSBAND_FATHER_NAME))
        surveyDataModel.HOUSE_NO =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.HOUSE_NO))
        surveyDataModel.ROAD_STREET_NO2 =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ROAD_STREET_NO2))
        surveyDataModel.ROAD_STREET_NAME2 =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.ROAD_STREET_NAME2))
        surveyDataModel.COLONY_NAME2 =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLONY_NAME2))
        surveyDataModel.STATION_NAME2 =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.STATION_NAME2))
        surveyDataModel.CITY = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.CITY))
        surveyDataModel.STATE_NAME2 =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.STATION_NAME2))
        surveyDataModel.PINCODE2 =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PINCODE2))
        surveyDataModel.EMAIL = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.EMAIL))
        surveyDataModel.MOBILE_NO2 =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MOBILE_NO2))
        surveyDataModel.INCOMETAX_PAYER =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.INCOMETAX_PAYER))
        surveyDataModel.FEMALE_MEMBER =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FEMALE_MEMBER))
        surveyDataModel.MALE_MEMBER =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MALE_MEMBER))
        surveyDataModel.FAMILY_GROUP =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FAMILY_GROUP))
        surveyDataModel.FAMILY_RENTAL_GROUP =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FAMILY_RENTAL_GROUP))
        surveyDataModel.PROPERTY_USES =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PROPERTY_USES))
        surveyDataModel.PROPERTY_USES_OTHER =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PROPERTY_USES_OTHER))
        surveyDataModel.PROPERTY_USES_OTHER_REMARK1 =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PROPERTY_USES_OTHER_REMARK1))
        surveyDataModel.PROPERTY_USES_OTHER_REMARK2 =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PROPERTY_USES_OTHER_REMARK2))
        surveyDataModel.PROPERTY_USES_OTHER_REMARK3 =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PROPERTY_USES_OTHER_REMARK3))
        surveyDataModel.CONSTRUCTION_YEAR =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.CONSTRUCTION_YEAR))
        surveyDataModel.LAND_AREA =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.LAND_AREA))
        surveyDataModel.MANJIL_CODE =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MANJIL_CODE))
        surveyDataModel.CONSTRUCTION_YEAR_C =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.CONSTRUCTION_YEAR_C))
        surveyDataModel.BUILDING_CODE =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.BUILDING_CODE))
        surveyDataModel.FLOOR_CODE =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FLOOR_CODE))
        surveyDataModel.AREA_C =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.AREA_C))
        surveyDataModel.MANJIL_USES =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.MANJIL_USES))
        surveyDataModel.DISCOUNT =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.DISCOUNT))
        surveyDataModel.DISCOUNT_TYPE =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.DISCOUNT_TYPE))
        surveyDataModel.DISCOUNT_TYPE_CODE =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.DISCOUNT_TYPE_CODE))
        surveyDataModel.DISCOUNT_TYPE_OTHER_NAME =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.DISCOUNT_TYPE_OTHER_NAME))
        surveyDataModel.HISTORIC_PROPERTY =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.HISTORIC_PROPERTY))
        surveyDataModel.WATER_SUPPLY_TYPE =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.WATER_SUPPLY_TYPE))
        surveyDataModel.WATER_SUPPLY_HOURS =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.WATER_SUPPLY_HOURS))
        surveyDataModel.WATER_SOURCE =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.WATER_SOURCE))
        surveyDataModel.PIPE_THICKNESS =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PIPE_THICKNESS))
        surveyDataModel.WATER_PERIOD =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.WATER_PERIOD))
        surveyDataModel.PIPE_PRESSURE =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PIPE_PRESSURE))
        surveyDataModel.WATER_METER =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.WATER_METER))
        surveyDataModel.WATER_METER_CONDITION =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.WATER_METER_CONDITION))
        surveyDataModel.RAIN_WATER_HARVESTING =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.RAIN_WATER_HARVESTING))
        surveyDataModel.RAIN_WATER_HARVESTING_USES =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.RAIN_WATER_HARVESTING_USES))
        surveyDataModel.SEWAGE_CONNECTION =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.SEWAGE_CONNECTION))
        surveyDataModel.SEWAGE_NETWORK_IF_NOT =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.SEWAGE_NETWORK_IF_NOT))
        surveyDataModel.TOILET_COUNT =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.TOILET_COUNT))
        surveyDataModel.SEWAGE_TYPE =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.SEWAGE_TYPE))
        surveyDataModel.TREE_COUNT =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.TREE_COUNT))
        surveyDataModel.WATER_TANK =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.WATER_TANK))
        surveyDataModel.TWO_WHEELER =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.TWO_WHEELER))
        surveyDataModel.FOUR_WHEELER =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FOUR_WHEELER))
        surveyDataModel.BUILDING_PERMIT =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.BUILDING_PERMIT))
        surveyDataModel.PROPERTY_IMAGE =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PROP_IMAGE))
        surveyDataModel.SIGNATURE_IMAGE =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.SIGN_IMAGE))
        surveyDataModel.PROPERTY_IMAGE_NAME =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PROPERTY_IMAGE_NAME))
        surveyDataModel.SIGNATURE_IMAGE_NAME =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.SIGNATURE_IMAGE_NAME))
        surveyDataModel.UNIQUE_ID =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.UNIQUE_ID))



        surveyDataModel.PROPERTY_RELATION_OTHER_ID =
            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.PROPERTY_RELATION_OTHER_ID))
        surveyDataModel.PROPERTY_OWNERSHIP_CLASSIFICATION_ID =
            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.PROPERTY_OWNERSHIP_CLASSIFICATION_ID))
        surveyDataModel.LAND_TYPE_ID =
            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.LAND_TYPE_ID))
        surveyDataModel.PROPERTY_USES_ID =
            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.PROPERTY_USES_ID))
        surveyDataModel.PROPERTY_USES_OTHER_ID =
            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.PROPERTY_USES_OTHER_ID))
        surveyDataModel.MANJIL_ID =
            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.MANJIL_ID))
        surveyDataModel.BUILDING_ID =
            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.BUILDING_ID))
        surveyDataModel.FLOOR_ID =
            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.FLOOR_ID))
        surveyDataModel.MANJIL_USES_ID =
            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.MANJIL_USES_ID))
        surveyDataModel.WATER_SOURCE_ID =
            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.WATER_SOURCE_ID))
        surveyDataModel.PIPE_THICKNESS_ID =
            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.PIPE_THICKNESS_ID))
        surveyDataModel.PIPE_PRESSURE_ID =
            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.PIPE_PRESSURE_ID))
        surveyDataModel.WATER_METER_ID =
            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.WATER_METER_ID))
        surveyDataModel.WATER_METER_CONDITION_ID =
            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.WATER_METER_CONDITION_ID))
        surveyDataModel.RAIN_WATER_HARVESTING_USES_ID =
            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.RAIN_WATER_HARVESTING_USES_ID))
        surveyDataModel.SEWAGE_NETWORK_IF_NOT_ID =
            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.SEWAGE_NETWORK_IF_NOT_ID))
        surveyDataModel.SEWAGE_TYPE_ID =
            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.SEWAGE_TYPE_ID))
        surveyDataModel.ISUPLOADED =
            Utility.getBooleanValue(cursor.getString(cursor.getColumnIndexOrThrow(ISUPLOADED)))
        surveyDataModel.ISVISITED =
            Utility.getBooleanValue(cursor.getString(cursor.getColumnIndexOrThrow(ISVISITED)))

        return surveyDataModel
    }

    private val queryData: Array<String>
        get() = arrayOf(
            ID,
            DatabaseHelper.LOGIN_ID,
            DatabaseHelper.STATE_NAME,
            DatabaseHelper.DIST_NAME,
            DatabaseHelper.DIST_CODE,
            DatabaseHelper.ULB_NAME,
            DatabaseHelper.ULB_CODE,
            DatabaseHelper.ZONE_NAME,
            DatabaseHelper.WARD_NAME,
            DatabaseHelper.STATE_ID,
            DatabaseHelper.ZONE_ID,
            DatabaseHelper.WARD_ID,
            DatabaseHelper.GRID_NO,
            DatabaseHelper.MAP_ID,
            DatabaseHelper.UNIQUE_ID_TEXT,
            DatabaseHelper.ULB_P_ID,
            DatabaseHelper.PARINAM_NAME,
            DatabaseHelper.PARINAM_ID,
            DatabaseHelper.PARINAM_NAME_OTHER,
            DatabaseHelper.NEXT_DATE,
            DatabaseHelper.MAAP_AADHAR_NAME,
            DatabaseHelper.MAAP_AADHAR_ID,
            DatabaseHelper.PARCEL_NAME,
            DatabaseHelper.PARCEL_NOT_IN_MAP,
            DatabaseHelper.PROPERTY_NO,
            DatabaseHelper.KHASRA_NO,
            DatabaseHelper.PROPERTY_ADDRESS,
            DatabaseHelper.ROAD_STREET_NO,
            DatabaseHelper.ROAD_STREET_NAME,
            DatabaseHelper.COLONY_NAME,
            DatabaseHelper.STATION_NAME,
            DatabaseHelper.LANDMARK_NAME,
            DatabaseHelper.PINCODE,
            DatabaseHelper.LAST_PAYMENT_YEAR_SURVEYOR,
            DatabaseHelper.LAST_PAYMENT_DATE_SURVEYOR,
            DatabaseHelper.RECIEPT_NO_SURVEYOR,
            DatabaseHelper.PAID_TAX_AMOUNT,
            DatabaseHelper.LAST_PAYMENT_YEAR_CORP,
            DatabaseHelper.LAST_PAYMENT_DATE_CORP,
            DatabaseHelper.RECIEPT_NO_CORP,
            DatabaseHelper.PROPERTY_REGISTERED,
            DatabaseHelper.RESPONDENT_NAME,
            DatabaseHelper.MOBILE_NO,
            DatabaseHelper.PROPERTY_RELATION,
            DatabaseHelper.PROPERTY_RELATION_OTHER,
            DatabaseHelper.PROPERTY_RELATION_OTHER_NAME,
            DatabaseHelper.PROPERTY_OWNERSHIP_CLASSIFICATION,
            DatabaseHelper.OTHER_CLASSIFICATION_NAME,
            DatabaseHelper.LAND_TYPE,
            DatabaseHelper.LAND_TYPE_OTHER,
            DatabaseHelper.NAME,
            DatabaseHelper.HUSBAND_FATHER_NAME,
            DatabaseHelper.HOUSE_NO,
            DatabaseHelper.ROAD_STREET_NO2,
            DatabaseHelper.ROAD_STREET_NAME2,
            DatabaseHelper.COLONY_NAME2,
            DatabaseHelper.STATION_NAME2,
            DatabaseHelper.CITY,
            DatabaseHelper.STATE_NAME2,
            DatabaseHelper.PINCODE2,
            DatabaseHelper.EMAIL,
            DatabaseHelper.MOBILE_NO2,
            DatabaseHelper.INCOMETAX_PAYER,
            DatabaseHelper.FEMALE_MEMBER,
            DatabaseHelper.MALE_MEMBER,
            DatabaseHelper.FAMILY_GROUP,
            DatabaseHelper.FAMILY_RENTAL_GROUP,
            DatabaseHelper.PROPERTY_USES,
            DatabaseHelper.PROPERTY_USES_OTHER,
            DatabaseHelper.PROPERTY_USES_OTHER_REMARK1,
            DatabaseHelper.PROPERTY_USES_OTHER_REMARK2,
            DatabaseHelper.PROPERTY_USES_OTHER_REMARK3,
            DatabaseHelper.CONSTRUCTION_YEAR,
            DatabaseHelper.LAND_AREA,
            DatabaseHelper.MANJIL_CODE,
            DatabaseHelper.CONSTRUCTION_YEAR_C,
            DatabaseHelper.BUILDING_CODE,
            DatabaseHelper.FLOOR_CODE,
            DatabaseHelper.AREA_C,
            DatabaseHelper.MANJIL_USES,
            DatabaseHelper.DISCOUNT,
            DatabaseHelper.DISCOUNT_TYPE,
            DatabaseHelper.DISCOUNT_TYPE_CODE,
            DatabaseHelper.DISCOUNT_TYPE_OTHER_NAME,
            DatabaseHelper.HISTORIC_PROPERTY,
            DatabaseHelper.WATER_SUPPLY_TYPE,
            DatabaseHelper.WATER_SUPPLY_HOURS,
            DatabaseHelper.WATER_SOURCE,
            DatabaseHelper.PIPE_THICKNESS,
            DatabaseHelper.WATER_PERIOD,
            DatabaseHelper.PIPE_PRESSURE,
            DatabaseHelper.WATER_METER,
            DatabaseHelper.WATER_METER_CONDITION,
            DatabaseHelper.RAIN_WATER_HARVESTING,
            DatabaseHelper.RAIN_WATER_HARVESTING_USES,
            DatabaseHelper.SEWAGE_CONNECTION,
            DatabaseHelper.SEWAGE_NETWORK_IF_NOT,
            DatabaseHelper.TOILET_COUNT,
            DatabaseHelper.SEWAGE_TYPE,
            DatabaseHelper.TREE_COUNT,
            DatabaseHelper.WATER_TANK,
            DatabaseHelper.TWO_WHEELER,
            DatabaseHelper.FOUR_WHEELER,
            DatabaseHelper.BUILDING_PERMIT,
            DatabaseHelper.PROP_IMAGE,
            DatabaseHelper.SIGN_IMAGE,
            DatabaseHelper.PROPERTY_IMAGE_NAME,
            DatabaseHelper.SIGNATURE_IMAGE_NAME,
            DatabaseHelper.PROPERTY_RELATION_OTHER_ID,
            DatabaseHelper.PROPERTY_OWNERSHIP_CLASSIFICATION_ID,
            DatabaseHelper.LAND_TYPE_ID,
            DatabaseHelper.PROPERTY_USES_ID,
            DatabaseHelper.PROPERTY_USES_OTHER_ID,
            DatabaseHelper.MANJIL_ID,
            DatabaseHelper.BUILDING_ID,
            DatabaseHelper.FLOOR_ID,
            DatabaseHelper.MANJIL_USES_ID,
            DatabaseHelper.WATER_SOURCE_ID,
            DatabaseHelper.PIPE_THICKNESS_ID,
            DatabaseHelper.PIPE_PRESSURE_ID,
            DatabaseHelper.WATER_METER_ID,
            DatabaseHelper.WATER_METER_CONDITION_ID,
            DatabaseHelper.RAIN_WATER_HARVESTING_USES_ID,
            DatabaseHelper.SEWAGE_NETWORK_IF_NOT_ID,
            DatabaseHelper.SEWAGE_TYPE_ID,
            DatabaseHelper.UNIQUE_ID,
            ISUPLOADED,
            ISVISITED
        )

    fun deleteAll(context: Context?) {
        synchronized(lock) {
            try {
                val db = UserDataSource.getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + DatabaseHelper.SURVEY_DATA_MST)
                //db.close;
            } catch (var8: android.database.SQLException) {
                var8.printStackTrace()
            }
        }
    }


    fun getAll(
        isVisited: Int, isUploaded: Int, context: Context?
    ): ArrayList<SurveyDataModel> {
        // please select any type of boolean flagField as a column name and flag value will be corrosponding value
        synchronized(lock) {
            val survey_infos: ArrayList<SurveyDataModel> = ArrayList<SurveyDataModel>()
            val db: SQLiteDatabase = getSQLiteDb(
                false,
                context
            )
            try {
                val cursor = db.rawQuery(
                    "SELECT * FROM " + SURVEY_DATA_MST + " WHERE " + ISUPLOADED + " =    '" + isUploaded + "'" + " AND " + ISVISITED + " = '" + isVisited + "'",
                    null
                )
                if (cursor.moveToFirst()) {
                    do {
                        survey_infos.add(cursorData(cursor))
                    } while (cursor.moveToNext())
                }
                cursor.close()
                //db.close;
            } catch (se: android.database.SQLException) {
                se.printStackTrace()
            }
            return survey_infos
        }
    }


    fun getByUniqueId(accountNo: String, context: Context?): SurveyDataModel? {
        synchronized(lock)
        {
            var miConsumerModel: SurveyDataModel? = null
            val db = getSQLiteDb(false, context)
            try {
                val cursor = db.query(
                    DatabaseHelper.SURVEY_DATA_MST,
                    queryData,
                    DatabaseHelper.UNIQUE_ID + "=?",
                    arrayOf(accountNo),
                    null,
                    null,
                    null
                )
                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    miConsumerModel = cursorData(cursor)
                }
                cursor.close()
                //db.close;
            } catch (se: android.database.SQLException) {
                se.printStackTrace()
            }
            return miConsumerModel
        }
    }


    fun getByCreatedUniqueNumber(unique_id: String, context: Context?): SurveyDataModel? {
        synchronized(lock)
        {
            var miConsumerModel: SurveyDataModel? = null
            val db = getSQLiteDb(false, context)
            try {
                val cursor = db.query(
                    DatabaseHelper.SURVEY_DATA_MST,
                    queryData,
                    DatabaseHelper.UNIQUE_ID + "=?",
                    arrayOf(unique_id),
                    null,
                    null,
                    null
                )
                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    miConsumerModel = cursorData(cursor)
                }
                cursor.close()
                //db.close;
            } catch (se: android.database.SQLException) {
                se.printStackTrace()
            }
            return miConsumerModel
        }
    }


    @SuppressLint("Range")
    fun getRowCount(isVisited: Int, isUploaded: Int, context: Context?): Int {
        synchronized(lock) {
            var count = 0
            try {
                val db: SQLiteDatabase = getSQLiteDb(
                    false,
                    context
                )
                val cursor = db.rawQuery(
                    "SELECT  COUNT(*) AS TOTALCOUNT FROM " + SURVEY_DATA_MST + " WHERE " + ISUPLOADED + " = '" + isUploaded + "'" + " AND " + ISVISITED + " = '" + isVisited + "'",
                    null
                )
                if (cursor.moveToFirst()) count = cursor.getInt(cursor.getColumnIndex("TOTALCOUNT"))
                cursor.close()
                //db.close;
            } catch (ex: android.database.SQLException) {
                ex.printStackTrace()
            }
            return count
        }
    }


}