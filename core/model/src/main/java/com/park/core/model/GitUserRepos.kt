package com.park.core.model

data class GitUserRepos(
    val id: Int,
    val nodeId: String,
    val name: String,
    val private: Boolean,
    val htmlUrl: String,
    val createdAt: String,
    val owner: GitUserRepoOwner
)

data class GitUserRepoOwner(
    val login: String,
    val nodeId: String,
)