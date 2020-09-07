package com.elok.githubuserapp.repository

import androidx.paging.PagingSource
import com.elok.githubuserapp.api.GithubService
import com.elok.githubuserapp.data.Item
import com.elok.githubuserapp.utilities.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class SearchResultPagingSource(
    private val service: GithubService,
    private val query: String
) : PagingSource<Int, Item>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.searchForUsers(searchQuery = query, pageNumber = page, pageSize = params.loadSize)
            val users = response.items
            LoadResult.Page(
                data = users,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (users.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}