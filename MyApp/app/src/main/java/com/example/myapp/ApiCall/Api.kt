package com.example.myapp.ApiCall


import com.example.myapp.model.LoginRequestModel
import com.example.myapp.model.LoginResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {


    @POST("SURVEYAPI/Login")
    fun getUserLogin(@Body loginModel: LoginRequestModel?): Call<LoginResponseModel>

//    @POST("api/Meter/MeterInstallation")
//    fun  saveMeterIndexingData(@Body miConsumerModel: MIConsumerModel?): Call<MeterIndexingResponse>?


}