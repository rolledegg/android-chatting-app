package smu.app.chattingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(val context: Context, val userList:ArrayList<User>)
    :RecyclerView.Adapter<UserAdapter.UserVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
        val view:View = LayoutInflater.from(context).inflate(R.layout.user_layout, parent,false)
        return UserVH(view)
    }

    override fun onBindViewHolder(holder: UserVH, position: Int) {
        val currentUser = userList[position]

        holder.textName.text = currentUser.name
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserVH(itemView: View): RecyclerView.ViewHolder(itemView){
        val textName = itemView.findViewById<TextView>(R.id.name_tv)
    }

}