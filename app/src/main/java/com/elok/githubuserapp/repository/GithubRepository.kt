package com.elok.githubuserapp.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.elok.githubuserapp.api.GithubService
import com.elok.githubuserapp.data.Item
import com.elok.githubuserapp.data.UserDetailResponse
import com.elok.githubuserapp.utilities.PAGE_SIZE
import kotlinx.coroutines.flow.Flow

class GithubRepository(private val service: GithubService) {

    fun getSearchResultStream(query: String): Flow<PagingData<Item>> {
        Log.d("SearchResult", "New query: $query")
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE),
            pagingSourceFactory = { SearchResultPagingSource(service, query) }
        ).flow
    }

    fun getFollowerStream(username: String): Flow<PagingData<Item>> {
        Log.d("FollowerResult", "username: $username")
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE),
            pagingSourceFactory = { FollowerPagingSource(service, username) }
        ).flow
    }

    fun getFollowingStream(username: String): Flow<PagingData<Item>> {
        Log.d("FollowingResult", "username: $username")
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE),
            pagingSourceFactory = { FollowingPagingSource(service, username) }
        ).flow
    }

    suspend fun getUserDetails(username: String): UserDetailResponse {
        Log.d("GetUserDetails", "username: $username")
        return service.getUserDetails(username = username)
    }

}