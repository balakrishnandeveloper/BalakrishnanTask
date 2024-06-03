package com.room.database.balakrishnantask.activity.activity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    suspend fun insert(userList:UserList)

    @Query("SELECT * FROM UserList")
    suspend fun geAll():List<UserList>
}