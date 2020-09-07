package com.elok.githubuserapp.utilities

import android.content.Context
import com.elok.githubuserapp.api.GithubService
import com.elok.githubuserapp.data.AppDatabase
import com.elok.githubuserapp.repository.GithubRepository
import com.elok.githubuserapp.repository.SavedUserRepository
import com.elok.githubuserapp.viewmodels.*

object InjectorUtils {
    private fun provideDatabase(context: Context): AppDatabase = AppDatabase.getInstance(context)

    fun provideSavedUserListViewModelFactory(
        context: Context
    ): SavedUserViewModelFactory {
        val savedUserRepository = SavedUserRepository(provideDatabase(context))
        return SavedUserViewModelFactory(savedUserRepository)
    }

    fun provideSearchUserViewModelFactory(): SearchUserViewModelFactory {
        val repository = GithubRepository(GithubService.create())
        return SearchUserViewModelFactory(repository)
    }

    fun provideUserDetailViewModelFactory(context: Context, username: String) : UserDetailViewModelFactory {
        val githubRepository = GithubRepository(GithubService.create())
        val savedUserRepository = SavedUserRepository(provideDatabase(context))
        return UserDetailViewModelFactory(githubRepository, savedUserRepository, username)
    }

    fun provideFollowerViewModelFactory() : FollowerViewModelFactory {
        val repository = GithubRepository(GithubService.create())
        return FollowerViewModelFactory(repository)
    }

    fun provideFollowingViewModelFactory() : FollowingViewModelFactory {
        val repository = GithubRepository(GithubService.create())
        return FollowingViewModelFactory(repository)
    }
}