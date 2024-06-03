package com.room.database.balakrishnantask.activity.activity

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class UserDetailsRepository {
    private val completedJob = Job()
    private val backgroundScope = CoroutineScope(Dispatchers.IO + completedJob)
    private val foregroundScope = CoroutineScope(Dispatchers.Main)

    private val addApi: CallApi by lazy {
        NetworkManager.baseURL(BaseUrl.baseUrl).serviceClass(CallApi::class.java).create()
    }


    fun addUserDetailsResponse(addDetails: HashMap<String, String>, taskCallback: TaskCallback<UserDetailsModel>) {
        backgroundScope.launch {
            when (val result: Result<UserDetailsModel> = addApi.saveData(addDetails).awaitResult()) {
                is Result.Ok -> foregroundScope.launch {
                    println("---process--- Calling completed repository")
                    taskCallback.onComplete(result.value)
                }
                is Result.Error -> foregroundScope.launch {
                    println("---process--- Calling error repository")
                    taskCallback.onException(result.exception)
                }
                is Result.Exception -> foregroundScope.launch {
                    println("---process--- Calling exception repository")
                    taskCallback.onException(result.exception)
                }
            }
        }

    }


    fun getUserListResponse(taskCallback: TaskCallback<ResponseBody>) {
        backgroundScope.launch {
            when (val result: Result<ResponseBody> = addApi.listData().awaitResult()) {
                is Result.Ok -> foregroundScope.launch {
                    println("---process--- Calling completed repository")
                    taskCallback.onComplete(result.value)
                }
                is Result.Error -> foregroundScope.launch {
                    println("---process--- Calling error repository")
                    taskCallback.onException(result.exception)
                }
                is Result.Exception -> foregroundScope.launch {
                    println("---process--- Calling exception repository")
                    taskCallback.onException(result.exception)
                }
            }
        }

    }
}