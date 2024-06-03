package com.room.database.balakrishnantask.activity.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.room.database.balakrishnantask.R
import com.room.database.balakrishnantask.databinding.ActivityUserDetailsRoomDatabaseListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserDetailsRoomDatabaseListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailsRoomDatabaseListBinding
    private var userList= listOf<UserList>()
    private lateinit var databaseUserList :RoomDatabaseAdapter

    private lateinit var userDao: UserDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserDetailsRoomDatabaseListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        callAdapter(userList)
        roomDatabase()
    }

    private fun roomDatabase() {
        userDao = UserDatabase.getInstance(this).userDao()
        try {
            GlobalScope.launch(Dispatchers.Main) {
                binding.noDataTxt.visibility= View.GONE
                userList=userDao.geAll()
                callAdapter(userList)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    private fun callAdapter(userList: List<UserList>) {
        if (userList.isNotEmpty()){
            binding.noDataTxt.visibility= View.GONE
            binding.listRv.layoutManager= LinearLayoutManager(this, RecyclerView.VERTICAL,false)
            databaseUserList =RoomDatabaseAdapter(this,userList)
            binding.listRv.adapter=databaseUserList
        }else{
            binding.noDataTxt.visibility= View.VISIBLE
        }

    }
}