package com.park.core.model

data class UserData(
    val incompleteResults: Boolean,
    val items: List<ItemData>,
    val totalCount: Int
)

data class ItemData(
    val avatarUrl: String,
    val eventsUrl: String,
    val followersUrl: String,
    val followingUrl: String,
    val gistsUrl: String,
    val gravatarId: String,
    val htmlUrl: String,
    val id: Int,
    val login: String,
    val nodeId: String,
    val organizationsUrl: String,
    val receivedEventsUrl: String,
    val reposUrl: String,
    val score: Double,
    val siteAdmin: Boolean,
    val starredUrl: String,
    val subscriptionsUrl: String,
    val type: String,
    val url: String
)

data class GitInfo (
    val gitUser: GitUser,
    val userRepos: List<GitUserRepos>
)