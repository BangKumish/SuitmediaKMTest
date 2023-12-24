package id.bangkumis.suitmediakmtest.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.bangkumis.suitmediakmtest.databinding.UserListRowBinding
import id.bangkumis.suitmediakmtest.model.DataItem

class Adapter: RecyclerView.Adapter<Adapter.MyViewHolder>() {
    inner class MyViewHolder(private val userListBinding: UserListRowBinding): RecyclerView.ViewHolder(userListBinding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: DataItem){
            userListBinding.root.setOnClickListener{
                onItemClickCallback?.onItemClicked(data)
            }
            userListBinding.apply {
                userName.text = data.firstName +" "+ data.lastName
                userEmail.text = data.email
                Glide.with(itemView)
                    .load(data.avatar)
                    .circleCrop()
                    .into(userImage)
            }
        }

    }

    private val userList = ArrayList<DataItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = UserListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    interface OnItemClickCallback{
        fun onItemClicked(data: DataItem)
    }

    fun setClickCallback(itemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = itemClickCallback
    }
    @SuppressLint("NotifyDataSetChanged")
    fun clearUsers() {
        this.userList.clear()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(users:ArrayList<DataItem>){
        userList.clear()
        userList.addAll(users)
        notifyDataSetChanged()
    }
}