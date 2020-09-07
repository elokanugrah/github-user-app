package com.elok.githubuserapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elok.githubuserapp.data.Item
import com.elok.githubuserapp.data.UserDetailResponse
import com.elok.githubuserapp.repository.GithubRepository
import com.elok.githubuserapp.repository.SavedUserRepository
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val githubRepository: GithubRepository,
    private val savedUserRepository: SavedUserRepository,
    private val username: String
) : ViewModel() {
    private val userDetails: MutableLiveData<UserDetailResponse> = MutableLiveData()
    val isSaved = savedUserRepository.isSaved(username)

    init {
        viewModelScope.launch {
            userDetails.postValue(githubRepository.getUserDetails(username))
        }
    }

    fun getUserDetails(): LiveData<UserDetailResponse> = userDetails

    fun addSavedUser(item: Item) {
        viewModelScope.launch { savedUserRepository.insert(item) }
    }

    fun deleteSavedUser() {
        viewModelScope.launch { savedUserRepository.deleteItem(username) }
    }
}