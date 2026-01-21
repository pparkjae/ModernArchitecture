package com.park.core.model

data class GitUserRepo(
    val id: Int,
    val nodeId: String,
    val name: String,
    val fullName: String,
    val private: Boolean,
    val htmlUrl: String,
    val description: String?,
    val createdAt: String,
    val owner: GitUserRepoOwner,
    val language: String?,
    val pushedAt: String,
    val stargazersCount: Int,
    val topics: List<String>,
    val isBookmarked: Boolean = false
)

data class GitUserRepoOwner(
    val login: String,
    val nodeId: String,
    val avatarUrl: String
)