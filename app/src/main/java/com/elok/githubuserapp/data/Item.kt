package com.elok.githubuserapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = "items",
    indices = [Index(value = ["id", "login"], unique = true)]
)

@Parcelize
data class Item (
    @PrimaryKey(autoGenerate = true)
    val ai_id: Int? = null,
    val avatar_url: String?,
    val events_url: String?,
    val followers_url: String?,
    val following_url: String?,
    val gists_url: String?,
    val gravatar_id: String?,
    val html_url: String?,
    val id: Int?,
    val login: String,
    val node_id: String?,
    val organizations_url: String?,
    val received_events_url: String?,
    val repos_url: String?,
    val score: Double?,
    val site_admin: Boolean?,
    val starred_url: String?,
    val subscriptions_url: String?,
    val type: String?,
    val url: String?
) : Parcelable
