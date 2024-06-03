package com.room.database.balakrishnantask.activity.activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.ResponseBody

class UserDetailsViewModel:ViewModel() {

    private var addUserDetails=MutableLiveData<UserDetailsModel>()
    val addUserDetailsResponse:LiveData<UserDetailsModel> get() = addUserDetails


    private var getUserList=MutableLiveData<ResponseBody>()
    val getUserListResponse:LiveData<ResponseBody>get() = getUserList


    private val repository : UserDetailsRepository by lazy {
        UserDetailsRepository()
    }


    fun userDetailsRequest(name:String,email:String,mobile:String,gender:String){
        val addDetails=HashMap<String,String>()
        addDetails["name"]=name
        addDetails["email"]=email
        addDetails["mobile"]=mobile
        addDetails["gender"]=gender

        repository.addUserDetailsResponse(addDetails,object : TaskCallback<UserDetailsModel> {
            override fun onComplete(result: UserDetailsModel?) {
                addUserDetails.postValue(result)
            }

            override fun onException(t: Throwable?) {
                ApiCustomLoader.dismiss()
                println("---process--- Calling Exception $t")
            }

        })

    }


    fun getUserListRequest(){
        repository.getUserListResponse(object : TaskCallback<ResponseBody> {
            override fun onComplete(result: ResponseBody?) {
                getUserList.postValue(result)
                Log.d("Success", "getObserver: $result")
            }

            override fun onException(t: Throwable?) {
                ApiCustomLoader.dismiss()
                println("---process--- Calling Exception $t")
            }

        })

    }

}