package com.room.database.balakrishnantask.activity.activity

interface TaskCallback<T>  {
    fun onComplete(result: T?)
    fun onException(t: Throwable?)
}