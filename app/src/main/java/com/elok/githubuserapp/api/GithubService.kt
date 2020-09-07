package com.elok.githubuserapp.api

import com.elok.githubuserapp.BuildConfig
import com.elok.githubuserapp.data.Item
import com.elok.githubuserapp.data.SearchResponse
import com.elok.githubuserapp.data.UserDetailResponse
import com.elok.githubuserapp.utilities.GITHUB_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface GithubService {
    @GET("search/users")
    suspend fun searchForUsers(
        @Header("Authorization") token: String = BuildConfig.GITHUB_TOKEN,
        @Query("q") searchQuery: String,
        @Query("page") pageNumber: Int,
        @Query("per_page") pageSize: Int
    ): SearchResponse

    @GET("users/{username}")
    suspend fun getUserDetails(
        @Path("username") username: String
    ) : UserDetailResponse

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String,
        @Query("page") pageNumber: Int,
        @Query("per_page") pageSize: Int
    ) : List<Item>

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String,
        @Query("page") pageNumber: Int,
        @Query("per_page") pageSize: Int
    ) : List<Item>

    companion object {

        fun create(): GithubService {
            val logger = HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(GITHUB_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubService::class.java)
        }
    }
}