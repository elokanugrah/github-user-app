package com.elok.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elok.githubuserapp.R
import com.elok.githubuserapp.data.Item
import com.elok.githubuserapp.databinding.ListItemSavedUserBinding
import com.elok.githubuserapp.ui.fragments.SavedUserFragmentDirections

class SavedUserAdapter : RecyclerView.Adapter<SavedUserAdapter.SavedUserViewHolder>() {

    private val differCallback = object  : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedUserViewHolder {
        return SavedUserViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_saved_user, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holderSaved: SavedUserViewHolder, position: Int) {
        val item = differ.currentList[position]
        if (item != null) {
            holderSaved.bind(item)
        }
    }

    inner class SavedUserViewHolder(
        private val binding: ListItemSavedUserBinding
    ): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.user?.let { userItem ->
                    navigateToDetail(userItem, it)
                }
            }
        }

        fun bind(item: Item) {
            binding.apply {
                user = item
                executePendingBindings()
            }
        }

        private fun navigateToDetail(
            item: Item,
            view: View
        ) {
            val direction = SavedUserFragmentDirections.actionSavedUserFragmentToUserDetailViewPagerFragment(item)
            view.findNavController().navigate(direction)
        }
    }
}
