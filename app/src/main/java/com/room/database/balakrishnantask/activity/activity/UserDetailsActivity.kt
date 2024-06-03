package com.room.database.balakrishnantask.activity.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.room.database.balakrishnantask.databinding.ActivityUserDetailsBinding
import kotlinx.coroutines.launch

class UserDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailsBinding

    private lateinit var database: UserDatabase
    private lateinit var userDao: UserDao


    private val viewModel by lazy {
        ViewModelProvider(this)[UserDetailsViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database= UserDatabase.getDatabase(this)
        userDao=database.userDao()
        clickListener()
        userDetailsObserver()

    }

    private fun userDetailsObserver() {
        viewModel.addUserDetailsResponse.observe(this){
            ApiCustomLoader.dismiss()
            if (it!=null){
                val newEntity = UserList(
                    id = it.id!!,
                    name = it.name!!,
                    email = it.email!!,
                    mobile = it.mobile!!,
                    gender = it.gender!!)
                lifecycleScope.launch {
                    userDao.insert(newEntity)
                }
                callEmpty()
            }
        }
    }

    private fun callEmpty() {
        binding.nameEditTxt.setText("")
        binding.emailEditTxt.setText("")
        binding.mobileEditTxt.setText("")
        binding.genderEditTxt.setText("")
        binding.nameEditTxt.requestFocus()
        Toast.makeText(this,"User Details Added Successfully !!",Toast.LENGTH_SHORT).show()
    }

    private fun clickListener() {
        binding.addTxt.setOnClickListener {
            checkValidation()
        }

        binding.viewTxt.setOnClickListener {
            val intent=Intent(this@UserDetailsActivity,UserListActivity::class.java)
            startActivity(intent)
        }

        binding.roomDbTxt.setOnClickListener{
            val intent=Intent(this@UserDetailsActivity,UserDetailsRoomDatabaseListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkValidation() {
        binding.apply {
            if (nameEditTxt.text.isEmpty()){
                Toast.makeText(this@UserDetailsActivity, "Please Enter Your Name", Toast.LENGTH_SHORT).show()

            }else if(emailEditTxt.text.isEmpty()){
                Toast.makeText(this@UserDetailsActivity, "Please Enter Your Email-id", Toast.LENGTH_SHORT).show()

            }else if(!Patterns.EMAIL_ADDRESS.matcher(emailEditTxt.text.toString()).matches()){
                Toast.makeText(this@UserDetailsActivity, "Please Enter valid Email-id", Toast.LENGTH_SHORT).show()

            } else if(mobileEditTxt.text.isEmpty()){
                Toast.makeText(this@UserDetailsActivity, "Please Enter Your Mobile Number", Toast.LENGTH_SHORT).show()

            }else if(genderEditTxt.text.isEmpty()){
                Toast.makeText(this@UserDetailsActivity, "Please Enter Your Gender", Toast.LENGTH_SHORT).show()

            }else{
                ApiCustomLoader.show(this@UserDetailsActivity)
                viewModel.userDetailsRequest(nameEditTxt.text.toString(),emailEditTxt.text.toString(),mobileEditTxt.text.toString(),genderEditTxt.text.toString())
            }
        }

    }
}

