package com.elok.githubuserapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elok.githubuserapp.repository.GithubRepository
import com.elok.githubuserapp.repository.SavedUserRepository

class UserDetailViewModelFactory(
    private val githubRepository: GithubRepository,
    private val savedUserRepository: SavedUserRepository,
    private val username: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return UserDetailViewModel(githubRepository, savedUserRepository, username) as T
    }
}