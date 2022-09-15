package com.example.myapp.app

import android.app.Application
import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit
import kotlin.jvm.Synchronized
class MyApplication : Application() {

    private var mRetrofitLogin: Retrofit? = null
    val API_URL = "https://testsurvey.tnaionline.org/"

    override fun onCreate() {
        super.onCreate()
        mInstance = this
      }


    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(200, TimeUnit.SECONDS)
        .writeTimeout(200, TimeUnit.SECONDS)
        .readTimeout(200, TimeUnit.SECONDS)
        .build()



    val retrofitInstanceLogin: Retrofit?
        get() {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            if (mRetrofitLogin == null) {
                mRetrofitLogin = Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build()
            }
            return mRetrofitLogin
        }


    companion object {
        val TAG = MyApplication::class.java.simpleName!!
        private val context: WeakReference<Context>? = null
        const val CHANNEL_ID = "CIServiceChannel"
        @get:Synchronized
        var mInstance: MyApplication? = null
            private set
        fun checkExpireTime(): Boolean {
            // Login loginIBILL = Login_Helper.getLogin(MyApplication.getInstance());
            try {
                val formatter = SimpleDateFormat("dd-MMM-yyyy")

            } catch (e: Exception) {
                e.message
            }
            return false
        }

        fun checkAPPRelease(): Boolean {
            return false
        }
    }
}

