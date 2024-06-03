package com.room.database.balakrishnantask.activity.activity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "UserList")
data class UserList(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val mobile: String,
    val gender: String
)
