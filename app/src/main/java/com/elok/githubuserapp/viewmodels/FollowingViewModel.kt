package com.elok.githubuserapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.elok.githubuserapp.data.Item
import com.elok.githubuserapp.repository.GithubRepository
import kotlinx.coroutines.flow.Flow

class FollowingViewModel internal constructor(
    private val repository: GithubRepository
) : ViewModel() {
    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<Item>>? = null

    fun userFollowing(username: String): Flow<PagingData<Item>> {
        val lastResult = currentSearchResult
        if (username == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = username
        val newResult: Flow<PagingData<Item>> =
            repository.getFollowingStream(username).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}