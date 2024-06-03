package com.room.database.balakrishnantask.activity.activity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [UserList::class], version = 2)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao():UserDao


    companion object{
        @Volatile private var INSTANCE: UserDatabase?=null

        fun getInstance(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: getDatabase(context).also { INSTANCE = it }
            }
        }

        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "UserListDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }
}