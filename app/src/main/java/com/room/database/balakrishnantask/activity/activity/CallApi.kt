package com.room.database.balakrishnantask.activity.activity

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CallApi {

    @POST("balakrishnan")
    fun saveData(@Body userDetails:HashMap<String,String>):Call<UserDetailsModel>



    @GET("balakrishnan")
    fun listData():Call<ResponseBody>

}