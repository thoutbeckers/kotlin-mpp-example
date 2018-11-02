package houtbecke.rs.mpp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import houtbecke.rs.mpp.databinding.StatusItemBinding

class UserStatusAdapter : RecyclerView.Adapter<UserStatusAdapter.UserStatusHolder>() {

    fun setData(items: List<UserStatusModel>) {
        userStatusList = items
        notifyDataSetChanged()
    }

    var userStatusList = emptyList<UserStatusModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserStatusHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StatusItemBinding.inflate(inflater)
        return UserStatusHolder(binding)
    }

    override fun getItemCount() = userStatusList.size

    override fun onBindViewHolder(holder: UserStatusHolder, position: Int) {
        holder.bind(userStatusList[position])
    }

    class UserStatusHolder(val binding: StatusItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(userStatus: UserStatusModel) {
            binding.userStatus = userStatus
            binding.executePendingBindings()
        }
    }
}
