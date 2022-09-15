package com.example.myapp.dbhelper

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import kotlin.jvm.Synchronized

class DatabaseHelper private constructor(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {


    companion object {
        private const val DB_NAME = "survey.db"
        private const val DB_VERSION = 1
        private var databaseHelper: DatabaseHelper? = null
        val lock = Any()

        //tables
        const val USER_MST = "USER_MST"
        const val ULB_MST = "ULB_MST"
        const val DIST_MST = "DIST_MST"
        const val ZONE_MST = "ZONE_MST"
        const val WARD_MST = "WARD_MST"
        const val SURVEY_DATA_MST = "SURVEY_DATA_MST"
        const val PROPERTY_DATA_MST = "PROPERTY_DATA_MST"
        const val TABC_MST = "TABC_MST"

        //user tables fields
        const val ID = "ID"
        const val LOGIN_ID = "LOGIN_ID"
        const val PASSWORD = "PASSWORD"
        const val USER_CODE = "USER_CODE"
        const val USERNAME = "USERNAME"
        const val DESIGN_NAME = "DESIGN_NAME"
        const val CHANGE_PASSWORD_FLAG = "CHANGE_PASSWORD_FLAG"
        const val UTILITY_TYPE = "UTILITY_TYPE"
        const val SC_NO = "SC_NO"
        const val UTILITY_CODE = "UTILITY_CODE"
        const val UTILITY_NAME = "UTILITY_NAME"
        const val SIM_CARD_NAME = "SIM_CARD_NAME"
        const val LEVEL1_NAME = "LEVEL1_NAME"
        const val LEVEL2_NAME = "LEVEL2_NAME"
        const val LEVEL3_NAME = "LEVEL3_NAME"


        //survey data feilds

        const val STATE_NAME = "STATE_NAME"
        const val ZONE_NAME = "ZONE_NAME"
        const val WARD_NAME = "WARD_NAME"
        const val STATE_ID = "STATE_ID"
        const val ZONE_ID = "ZONE_ID"
        const val WARD_ID = "WARD_ID"
        const val GRID_NO = "GRID_NO"
        const val MAP_ID = "MAP_ID"
        const val UNIQUE_ID_TEXT = "UNIQUE_ID_TEXT"
        const val PARINAM_NAME = "PARINAM_NAME"
        const val PARINAM_ID = "PARINAM_ID"
        const val PARINAM_NAME_OTHER = "PARINAM_NAME_OTHER"
        const val NEXT_DATE = "NEXT_DATE"
        const val MAAP_AADHAR_NAME = "MAAP_AADHAR_NAME"
        const val MAAP_AADHAR_ID = "MAAP_AADHAR_ID"
        const val PARCEL_NAME = "PARCEL_NAME"
        const val PARCEL_NOT_IN_MAP = "PARCEL_NOT_IN_MAP"
        const val PROPERTY_NO = "PROPERTY_NO"
        const val PROPERTY_ADDRESS = "PROPERTY_ADDRESS"
        const val ROAD_STREET_NO = "ROAD_STREET_NO"
        const val ROAD_STREET_NAME = "ROAD_STREET_NAME"
        const val COLONY_NAME = "COLONY_NAME"
        const val STATION_NAME = "STATION_NAME"
        const val LANDMARK_NAME = "LANDMARK_NAME"
        const val PINCODE = "PINCODE"
        const val LAST_PAYMENT_YEAR_SURVEYOR = "LAST_PAYMENT_YEAR_SURVEYOR"
        const val LAST_PAYMENT_DATE_SURVEYOR = "LAST_PAYMENT_DATE_SURVEYOR"
        const val RECIEPT_NO_SURVEYOR = "RECIEPT_NO_SURVEYOR"
        const val PAID_TAX_AMOUNT = "PAID_TAX_AMOUNT"
        const val LAST_PAYMENT_YEAR_CORP = "LAST_PAYMENT_YEAR_CORP"
        const val LAST_PAYMENT_DATE_CORP = "LAST_PAYMENT_DATE_CORP"
        const val RECIEPT_NO_CORP = "RECIEPT_NO_CORP"
        const val PROPERTY_REGISTERED = "PROPERTY_REGISTERED"
        const val RESPONDENT_NAME = "RESPONDENT_NAME"
        const val PROPERTY_RELATION = "PROPERTY_RELATION"
        const val PROPERTY_RELATION_OTHER = "PROPERTY_RELATION_OTHER"
        const val PROPERTY_RELATION_OTHER_NAME = "PROPERTY_RELATION_OTHER_NAME"
        const val PROPERTY_OWNERSHIP_CLASSIFICATION = "PROPERTY_OWNERSHIP_CLASSIFICATION"
        const val OTHER_CLASSIFICATION_NAME = "OTHER_CLASSIFICATION_NAME"
        const val LAND_TYPE = "LAND_TYPE"
        const val LAND_TYPE_OTHER = "LAND_TYPE_OTHER"
        const val NAME = "NAME"
        const val HUSBAND_FATHER_NAME = "HUSBAND_FATHER_NAME"
        const val HOUSE_NO = "HOUSE_NO"
        const val ROAD_STREET_NO2 = "ROAD_STREET_NO2"
        const val ROAD_STREET_NAME2 = "ROAD_STREET_NAME2"
        const val COLONY_NAME2 = "COLONY_NAME2"
        const val STATION_NAME2 = "STATION_NAME2"
        const val CITY = "CITY"
        const val STATE_NAME2 = "STATE_NAME2"
        const val PINCODE2 = "PINCODE2"
        const val EMAIL = "EMAIL"
        const val MOBILE_NO2 = "MOBILE_NO2"
        const val INCOMETAX_PAYER = "INCOMETAX_PAYER"
        const val FEMALE_MEMBER = "FEMALE_MEMBER"
        const val MALE_MEMBER = "MALE_MEMBER"
        const val FAMILY_GROUP = "FAMILY_GROUP"
        const val FAMILY_RENTAL_GROUP = "FAMILY_RENTAL_GROUP"
        const val PROPERTY_USES = "PROPERTY_USES"
        const val PROPERTY_USES_OTHER = "PROPERTY_USES_OTHER"
        const val PROPERTY_USES_OTHER_REMARK1 = "PROPERTY_USES_OTHER_REMARK1"
        const val PROPERTY_USES_OTHER_REMARK2 = "PROPERTY_USES_OTHER_REMARK2"
        const val PROPERTY_USES_OTHER_REMARK3 = "PROPERTY_USES_OTHER_REMARK3"
        const val CONSTRUCTION_YEAR = "CONSTRUCTION_YEAR"
        const val LAND_AREA = "LAND_AREA"
        const val MANJIL_CODE = "MANJIL_CODE"
        const val CONSTRUCTION_YEAR_C = "CONSTRUCTION_YEAR_C"
        const val BUILDING_CODE = "BUILDING_CODE"
        const val FLOOR_CODE = "FLOOR_CODE"
        const val AREA_C = "AREA_C"
        const val MANJIL_USES = "MANJIL_USES"
        const val DISCOUNT = "DISCOUNT"
        const val DISCOUNT_TYPE = "DISCOUNT_TYPE"
        const val DISCOUNT_TYPE_CODE = "DISCOUNT_TYPE_CODE"
        const val DISCOUNT_TYPE_OTHER_NAME = "DISCOUNT_TYPE_OTHER_NAME"
        const val HISTORIC_PROPERTY = "HISTORIC_PROPERTY"
        const val WATER_SUPPLY_TYPE = "WATER_SUPPLY_TYPE"
        const val WATER_SUPPLY_HOURS = "WATER_SUPPLY_HOURS"
        const val WATER_SOURCE = "WATER_SOURCE"
        const val PIPE_THICKNESS = "PIPE_THICKNESS"
        const val WATER_PERIOD = "WATER_PERIOD"
        const val PIPE_PRESSURE = "PIPE_PRESSURE"
        const val WATER_METER = "WATER_METER"
        const val WATER_METER_CONDITION = "WATER_METER_CONDITION"
        const val RAIN_WATER_HARVESTING = "RAIN_WATER_HARVESTING"
        const val RAIN_WATER_HARVESTING_USES = "RAIN_WATER_HARVESTING_USES"
        const val SEWAGE_CONNECTION = "SEWAGE_CONNECTION"
        const val SEWAGE_NETWORK_IF_NOT = "SEWAGE_NETWORK_IF_NOT"
        const val TOILET_COUNT = "TOILET_COUNT"
        const val SEWAGE_TYPE = "SEWAGE_TYPE"
        const val TREE_COUNT = "TREE_COUNT"
        const val WATER_TANK = "WATER_TANK"
        const val TWO_WHEELER = "TWO_WHEELER"
        const val FOUR_WHEELER = "FOUR_WHEELER"
        const val BUILDING_PERMIT = "BUILDING_PERMIT"
        const val PROP_IMAGE = "PROP_IMAGE"
        const val SIGN_IMAGE = "SIGN_IMAGE"
        const val PROPERTY_IMAGE_NAME = "PROPERTY_IMAGE_NAME"
        const val SIGNATURE_IMAGE_NAME = "SIGNATURE_IMAGE_NAME"
        const val ISUPLOADED = "ISUPLOADED"
        const val ISVISITED = "ISVISITED"
        const val UNIQUE_ID = "UNIQUE_ID"
        const val WARD_CODE = "WARD_CODE"
        const val ZONE_CODE = "ZONE_CODE"

        const val PROPERTY_RELATION_OTHER_ID="PROPERTY_RELATION_OTHER_ID"
        const val PROPERTY_OWNERSHIP_CLASSIFICATION_ID="PROPERTY_OWNERSHIP_CLASSIFICATION_ID"
        const val LAND_TYPE_ID="LAND_TYPE_ID"
        const val PROPERTY_USES_ID="PROPERTY_USES_ID"
        const val PROPERTY_USES_OTHER_ID="PROPERTY_USES_OTHER_ID"
        const val MANJIL_ID="MANJIL_ID"
        const val BUILDING_ID="BUILDING_ID"
        const val FLOOR_ID="FLOOR_ID"
        const val MANJIL_USES_ID="MANJIL_USES_ID"
        const val WATER_SOURCE_ID="WATER_SOURCE_ID"
        const val PIPE_THICKNESS_ID="PIPE_THICKNESS_ID"
        const val PIPE_PRESSURE_ID="PIPE_PRESSURE_ID"
        const val WATER_METER_ID="WATER_METER_ID"
        const val WATER_METER_CONDITION_ID="WATER_METER_CONDITION_ID"
        const val RAIN_WATER_HARVESTING_USES_ID="RAIN_WATER_HARVESTING_USES_ID"
        const val SEWAGE_NETWORK_IF_NOT_ID="SEWAGE_NETWORK_IF_NOT_ID"
        const val SEWAGE_TYPE_ID="SEWAGE_TYPE_ID"


//   prefrence column
        const val DIST_NAME = "DIST_NAME"
        const val DIST_CODE = "DIST_CODE"
        const val ULB_CODE = "ULB_CODE"
        const val ULB_NAME = "ULB_NAME"
        const val ULB_P_ID = "ULB_P_ID"
        const val KHASRA_NO = "KHASRA_NO"

        // property Details Column

        const val  PM_PROP_NO="PM_PROP_NO"
        const val  PM_PROP_OLDPROPNO="PM_PROP_OLDPROPNO"
        const val  Owner_Name="Owner_Name"
        const val  Father_Name="Father_Name"
        const val  Mobile_Number="Mobile_Number"
        const val  Email="Email"
        const val  Property_Location_Address="Property_Location_Address"
        const val  Location="Location"
        const val  LANDMARK="LANDMARK"
        const val  House_No="House_No"
        const val  Kharsra_No="Kharsra_No"


        // USERINFO COLUMN

        const val  USER_ID="USER_ID"
        const val  USER_NAME="USER_NAME"
        const val  MOBILE_NO="MOBILE_NO"
        const val  EMAIL_ID="EMAIL_ID"
        const val  AADHAR_NO="AADHAR_NO"


        const val  STATUS="STATUS"


        @Synchronized
        fun getInstance(context: Context): DatabaseHelper? {
            synchronized(lock) {
                if (databaseHelper == null) {
                    databaseHelper = DatabaseHelper(context.applicationContext)
                }
            }
            return databaseHelper
        }
    }


    override fun onCreate(db: SQLiteDatabase) {

        val TABLE_USER_MST = "CREATE TABLE " + USER_MST + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                USER_ID + " TEXT," +
                USER_NAME + " TEXT," +
                MOBILE_NO + " TEXT," +
                EMAIL_ID + " TEXT," +
                AADHAR_NO + " TEXT" +
                ");"

        val TABLE_DIST_MST = "CREATE TABLE " + DIST_MST + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DIST_CODE + " INTEGER," +
                DIST_NAME + " TEXT" +
                ");"

        val TABLE_ULB_MST = "CREATE TABLE " + ULB_MST + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ULB_CODE + " INTEGER," +
                ULB_NAME + " TEXT" +
                ");"


        val TABLE_ZONE_MST = "CREATE TABLE " + ZONE_MST + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ZONE_CODE + " INTEGER," +
                ZONE_NAME + " TEXT" +
                ");"

        val TABLE_WARD_MST = "CREATE TABLE " + WARD_MST + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                WARD_CODE + " INTEGER," +
                WARD_NAME + " TEXT" +
                ");"


        val TABLE_PROPERTY_DATA_MST = "CREATE TABLE " + PROPERTY_DATA_MST + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PM_PROP_NO + " TEXT," +
                PM_PROP_OLDPROPNO + " TEXT," +
                Owner_Name + " TEXT," +
                Father_Name + " TEXT," +
                Mobile_Number + " TEXT," +
                Email + " TEXT," +
                Property_Location_Address + " TEXT," +
                Location + " TEXT," +
                LANDMARK + " TEXT," +
                House_No + " TEXT," +
                Kharsra_No + " TEXT" +
                ")"



        val TABLE_TABC_MST = "CREATE TABLE " + TABC_MST + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MANJIL_CODE + " TEXT," +
                CONSTRUCTION_YEAR_C + " TEXT," +
                BUILDING_CODE + " TEXT," +
                FLOOR_CODE + " TEXT," +
                AREA_C + " TEXT," +
                MANJIL_USES + " TEXT," +
                MANJIL_ID + " TEXT," +
                BUILDING_ID + " TEXT," +
                FLOOR_ID + " TEXT," +
                MANJIL_USES_ID + " TEXT," +
                UNIQUE_ID + " TEXT," +
                STATUS + " TEXT" +
                ")"



        val TABLE_SURVEY_DATA_MST = "CREATE TABLE " + SURVEY_DATA_MST + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                LOGIN_ID + " TEXT," +
                STATE_NAME + " TEXT," +
                ZONE_NAME + " TEXT," +
                WARD_NAME + " TEXT," +
                STATE_ID + " TEXT," +
                ZONE_ID + " TEXT," +
                DIST_NAME + " TEXT," +
                DIST_CODE + " TEXT," +
                ULB_NAME + " TEXT," +
                ULB_CODE + " TEXT," +
                WARD_ID + " TEXT," +
                GRID_NO + " TEXT," +
                MAP_ID + " TEXT," +
                UNIQUE_ID_TEXT + " TEXT," +
                ULB_P_ID + " TEXT," +
                PARINAM_NAME + " TEXT," +
                PARINAM_ID + " TEXT," +
                PARINAM_NAME_OTHER + " TEXT," +
                NEXT_DATE + " TEXT," +
                MAAP_AADHAR_NAME + " TEXT," +
                MAAP_AADHAR_ID + " TEXT," +
                PARCEL_NAME + " TEXT," +
                PARCEL_NOT_IN_MAP + " TEXT," +
                PROPERTY_NO + " TEXT," +
                KHASRA_NO + " TEXT," +
                PROPERTY_ADDRESS + " TEXT," +
                ROAD_STREET_NO + " TEXT," +
                ROAD_STREET_NAME + " TEXT," +
                COLONY_NAME + " TEXT," +
                STATION_NAME + " TEXT," +
                LANDMARK_NAME + " TEXT," +
                PINCODE + " TEXT," +
                LAST_PAYMENT_YEAR_SURVEYOR + " TEXT," +
                LAST_PAYMENT_DATE_SURVEYOR + " INTEGER," +
                RECIEPT_NO_SURVEYOR + " TEXT," +
                PAID_TAX_AMOUNT + " TEXT," +
                LAST_PAYMENT_YEAR_CORP + " TEXT," +
                LAST_PAYMENT_DATE_CORP + " TEXT," +
                RECIEPT_NO_CORP + " TEXT," +
                PROPERTY_REGISTERED + " TEXT," +
                RESPONDENT_NAME + " TEXT," +
                MOBILE_NO + " TEXT," +
                PROPERTY_RELATION + " TEXT," +
                PROPERTY_RELATION_OTHER + " TEXT," +
                PROPERTY_RELATION_OTHER_NAME + " TEXT," +
                PROPERTY_OWNERSHIP_CLASSIFICATION + " TEXT," +
                OTHER_CLASSIFICATION_NAME + " TEXT," +
                LAND_TYPE + " TEXT," +
                LAND_TYPE_OTHER + " TEXT," +
                NAME + " TEXT," +
                HUSBAND_FATHER_NAME + " TEXT," +
                HOUSE_NO + " TEXT," +
                ROAD_STREET_NO2 + " TEXT," +
                ROAD_STREET_NAME2 + " TEXT," +
                COLONY_NAME2 + " TEXT," +
                STATION_NAME2 + " TEXT," +
                CITY + " TEXT," +
                STATE_NAME2 + " TEXT," +
                PINCODE2 + " TEXT," +
                EMAIL + " TEXT," +
                MOBILE_NO2 + " TEXT," +
                INCOMETAX_PAYER + " TEXT," +
                FEMALE_MEMBER + " TEXT," +
                MALE_MEMBER + " TEXT," +
                FAMILY_GROUP + " TEXT," +
                FAMILY_RENTAL_GROUP + " TEXT," +
                PROPERTY_USES + " TEXT," +
                PROPERTY_USES_OTHER + " TEXT," +
                PROPERTY_USES_OTHER_REMARK1 + " TEXT," +
                PROPERTY_USES_OTHER_REMARK2 + " TEXT," +
                PROPERTY_USES_OTHER_REMARK3 + " TEXT," +
                CONSTRUCTION_YEAR + " TEXT," +
                LAND_AREA + " TEXT," +
                MANJIL_CODE + " TEXT," +
                CONSTRUCTION_YEAR_C + " TEXT," +
                BUILDING_CODE + " TEXT," +
                FLOOR_CODE + " TEXT," +
                AREA_C + " TEXT," +
                MANJIL_USES + " TEXT," +
                DISCOUNT + " TEXT," +
                DISCOUNT_TYPE + " TEXT," +
                DISCOUNT_TYPE_CODE + " TEXT," +
                DISCOUNT_TYPE_OTHER_NAME + " TEXT," +
                HISTORIC_PROPERTY + " TEXT," +
                WATER_SUPPLY_TYPE + " TEXT," +
                WATER_SUPPLY_HOURS + " TEXT," +
                WATER_SOURCE + " TEXT," +
                PIPE_THICKNESS + " TEXT," +
                WATER_PERIOD + " TEXT," +
                PIPE_PRESSURE + " TEXT," +
                WATER_METER + " TEXT," +
                WATER_METER_CONDITION + " TEXT," +
                RAIN_WATER_HARVESTING + " TEXT," +
                RAIN_WATER_HARVESTING_USES + " TEXT," +
                SEWAGE_CONNECTION + " TEXT," +
                SEWAGE_NETWORK_IF_NOT + " TEXT," +
                TOILET_COUNT + " TEXT," +
                SEWAGE_TYPE + " TEXT," +
                TREE_COUNT + " TEXT," +
                WATER_TANK + " TEXT," +
                TWO_WHEELER + " TEXT," +
                FOUR_WHEELER + " TEXT," +
                BUILDING_PERMIT + " TEXT," +
                PROP_IMAGE + " TEXT," +
                SIGN_IMAGE + " TEXT," +
                PROPERTY_IMAGE_NAME + " TEXT," +
                SIGNATURE_IMAGE_NAME + " TEXT," +
                UNIQUE_ID + " TEXT," +
                PROPERTY_RELATION_OTHER_ID + " TEXT," +
                PROPERTY_OWNERSHIP_CLASSIFICATION_ID + " TEXT," +
                LAND_TYPE_ID + " TEXT," +
                PROPERTY_USES_ID + " TEXT," +
                PROPERTY_USES_OTHER_ID + " TEXT," +
                MANJIL_ID + " TEXT," +
                BUILDING_ID + " TEXT," +
                FLOOR_ID + " TEXT," +
                MANJIL_USES_ID + " TEXT," +
                WATER_SOURCE_ID + " TEXT," +
                PIPE_THICKNESS_ID + " TEXT," +
                PIPE_PRESSURE_ID + " TEXT," +
                WATER_METER_ID + " TEXT," +
                WATER_METER_CONDITION_ID + " TEXT," +
                RAIN_WATER_HARVESTING_USES_ID + " TEXT," +
                SEWAGE_NETWORK_IF_NOT_ID + " TEXT," +
                SEWAGE_TYPE_ID + " TEXT," +
                ISUPLOADED + " BOOLEAN," +
                ISVISITED + " BOOLEAN" +
                ")"





        db.execSQL(TABLE_USER_MST)
        db.execSQL(TABLE_ZONE_MST)
        db.execSQL(TABLE_WARD_MST)
        db.execSQL(TABLE_DIST_MST)
        db.execSQL(TABLE_ULB_MST)
        db.execSQL(TABLE_PROPERTY_DATA_MST)
        db.execSQL(TABLE_SURVEY_DATA_MST)
        db.execSQL(TABLE_TABC_MST)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

}