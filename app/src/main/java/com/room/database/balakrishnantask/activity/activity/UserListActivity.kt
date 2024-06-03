package com.room.database.balakrishnantask.activity.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.room.database.balakrishnantask.databinding.ActivityUserListBinding
import org.json.JSONArray
import org.json.JSONObject


class UserListActivity:AppCompatActivity() {
    private lateinit var binding: ActivityUserListBinding
    private lateinit var userListAdapter:UserListAdapter
    private var userList = ArrayList<UserListModel>()
    private val viewModel by lazy {
        ViewModelProvider(this)[UserDetailsViewModel::class.java]

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getList()
    }

    private fun getList() {
        ApiCustomLoader.show(this@UserListActivity)
        viewModel.getUserListRequest()
        viewModel.getUserListResponse.observe(this){
            ApiCustomLoader.dismiss()
            val aResponse: String = it.string()
            val myResponseJsonArr = JSONArray(aResponse)
            Log.d("UserJsonArr", "getList: $myResponseJsonArr")
            userList.clear()
            if(myResponseJsonArr.length()>0){
                for(i in 0 until myResponseJsonArr.length()){
                    val userObj = myResponseJsonArr.getJSONObject(i)
                    val id = userObj.getString("_id")
                    val name = userObj.getString("name")
                    val email = userObj.getString("email")
                    val mobile = userObj.getString("mobile")
                    val gender = userObj.getString("gender")
                    userList.add(UserListModel(id,name, email, mobile, gender))
                }
                callAdapter(userList)
            }else{
                binding.noDataTxt.visibility= View.VISIBLE
            }
        }

    }

    private fun callAdapter(userArrayList: ArrayList<UserListModel>) {
        binding.listRv.layoutManager= LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        userListAdapter= UserListAdapter(this, userArrayList)
        binding.listRv.adapter=userListAdapter

    }
}