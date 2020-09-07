package com.elok.githubuserapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elok.githubuserapp.data.Item
import com.elok.githubuserapp.repository.SavedUserRepository
import kotlinx.coroutines.launch

class SavedUserViewModel(
    private val repository: SavedUserRepository
) : ViewModel() {
    val savedUsers: LiveData<List<Item>> = repository.getSavedUsers()

    fun addSavedUser(item: Item) =
        viewModelScope.launch {
            repository.insert(item)
    }

    fun deleteSavedUser(item: Item) =
        viewModelScope.launch {
            repository.deleteItem(item)
        }
}