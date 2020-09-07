package com.elok.githubuserapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elok.githubuserapp.repository.SavedUserRepository

class SavedUserViewModelFactory(
    private val savedUserRepository: SavedUserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return SavedUserViewModel(savedUserRepository) as T
    }
}