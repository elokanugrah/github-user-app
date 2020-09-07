package com.elok.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elok.githubuserapp.R
import com.elok.githubuserapp.data.Item
import com.elok.githubuserapp.databinding.ListItemUserCardBinding
import com.elok.githubuserapp.ui.fragments.SearchUserFragmentDirections

class UserAdapter : PagingDataAdapter<Item, UserAdapter.UserViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_user_card,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class UserViewHolder(
        private val binding: ListItemUserCardBinding
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.userItem?.let { userItem ->
                    navigateToDetail(userItem, it)
                }
            }
        }

        private fun navigateToDetail(
            item: Item,
            view: View
        ) {
            val direction = SearchUserFragmentDirections.actionSearchUserFragmentToUserDetailViewPagerFragment(item)
            view.findNavController().navigate(direction)
        }

        fun bind(item: Item) {
            binding.apply {
                userItem = item
                executePendingBindings()
            }
        }
    }
}

class UserDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.login == newItem.login
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }

}
