package com.elok.githubuserapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.elok.githubuserapp.data.Item
import com.elok.githubuserapp.repository.GithubRepository
import kotlinx.coroutines.flow.Flow

class SearchUserViewModel internal constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {
    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<Item>>? = null

    fun searchUser(query: String): Flow<PagingData<Item>> {
        val lastResult = currentSearchResult
        if (query == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = query
        val newResult: Flow<PagingData<Item>> =
            githubRepository.getSearchResultStream(query).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

}