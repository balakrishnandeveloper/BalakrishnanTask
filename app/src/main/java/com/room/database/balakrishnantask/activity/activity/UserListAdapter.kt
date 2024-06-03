package com.room.database.balakrishnantask.activity.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.room.database.balakrishnantask.databinding.AdapterUserListBinding

class UserListAdapter(var context:Context, private var list: ArrayList<UserListModel>):RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    class ViewHolder(itemView:AdapterUserListBinding):RecyclerView.ViewHolder(itemView.root){
        var binding=itemView

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListAdapter.ViewHolder {
        val view=AdapterUserListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserListAdapter.ViewHolder, position: Int) {
        holder.binding.apply {
            nameTxtList.text=list[position].name
            emailTxtList.text=list[position].email
            mobileTxtList.text=list[position].mobile
            genderTxtList.text=list[position].gender
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}