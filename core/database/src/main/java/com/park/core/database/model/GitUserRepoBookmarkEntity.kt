package com.park.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "git_user_repo_bookmark")
data class GitUserRepoBookmarkEntity(
    @PrimaryKey
    val id: Int,
    val nodeId: String,
    val name: String,
    @ColumnInfo(name = "full_name")
    val fullName: String,
    val description: String?,
    @ColumnInfo(name = "stargazers_count")
    val stargazersCount: Int,
    val language: String?,
    @ColumnInfo(name = "html_url")
    val htmlUrl: String,
    @ColumnInfo(name = "owner_name")
    val ownerName: String,
    @ColumnInfo(name = "owner_avatar_url")
    val ownerAvatarUrl: String,
    val topics: List<String>,
    @ColumnInfo(name = "pushed_at")
    val pushedAt: String,
    @ColumnInfo(name = "saved_at")
    val savedAt: Long = System.currentTimeMillis()
)