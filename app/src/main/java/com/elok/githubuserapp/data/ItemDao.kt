package com.elok.githubuserapp.data

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item): Long

    @Query("SELECT * FROM items")
    fun getSavedUsers(): LiveData<List<Item>>

    @Query("SELECT EXISTS(SELECT 1 FROM items WHERE login = :username LIMIT 1)")
    fun isSaved(username: String): LiveData<Boolean>

    @Delete
    suspend fun deleteItem(item: Item)

    @Query("DELETE FROM items WHERE login = :username")
    suspend fun deleteItem(username: String): Int
}