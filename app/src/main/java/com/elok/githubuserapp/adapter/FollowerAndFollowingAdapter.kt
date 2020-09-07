package com.elok.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elok.githubuserapp.R
import com.elok.githubuserapp.data.Item
import com.elok.githubuserapp.databinding.ListItemUserBinding

class FollowerFollowingAdapter : PagingDataAdapter<Item, FollowerFollowingAdapter.FollowerViewHolder>(FollowerAndFollowingDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        return FollowerViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_user, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class FollowerViewHolder(
        private val binding: ListItemUserBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            binding.apply {
                user = item
                executePendingBindings()
            }
        }
    }
}

class FollowerAndFollowingDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.login == newItem.login
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }

}