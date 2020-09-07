package com.elok.githubuserapp.repository

import androidx.paging.PagingSource
import com.elok.githubuserapp.api.GithubService
import com.elok.githubuserapp.data.Item
import com.elok.githubuserapp.utilities.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class FollowingPagingSource(
    private val service: GithubService,
    private val username: String
) : PagingSource<Int, Item>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val following = service.getFollowing(username = username, pageNumber = page, pageSize = params.loadSize)
            LoadResult.Page(
                data = following,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (following.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}