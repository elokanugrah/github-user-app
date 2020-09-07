package com.elok.githubuserapp.repository

import com.elok.githubuserapp.data.AppDatabase
import com.elok.githubuserapp.data.Item

class SavedUserRepository constructor(
    private val database: AppDatabase
) {
    fun getSavedUsers() = database.getItemDao().getSavedUsers()

    fun isSaved(username: String) = database.getItemDao().isSaved(username)

    suspend fun insert(item: Item) = database.getItemDao().insert(item)

    suspend fun deleteItem(item: Item) = database.getItemDao().deleteItem(item)

    suspend fun deleteItem(username: String) = database.getItemDao().deleteItem(username)
}