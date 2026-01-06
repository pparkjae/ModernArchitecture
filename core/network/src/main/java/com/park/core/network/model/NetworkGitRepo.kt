package com.park.core.network.model

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class NetworkGitRepo(
    @SerialName("id") val id: Int,
    @SerialName("node_id") val nodeId: String,
    @SerialName("name") val name: String,
    @SerialName("full_name") val fullName: String,
    @SerialName("private") val private: Boolean,
    @SerialName("created_at") val createdAt: String,
    @SerialName("owner") val owner: NetworkGitRepoOwner,
    @SerialName("html_url") val htmlUrl: String,
    @SerialName("description") val description: String?,
    @SerialName("language") val language: String?,
    @SerialName("pushed_at") val pushedAt: String,
    @SerialName("stargazers_count") val stargazersCount: Int,
    @SerialName("topics") val topics: List<String>,
)

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class NetworkGitRepoOwner(
    @SerialName("login") val login: String,
    @SerialName("node_id") val nodeId: String,
    @SerialName("avatar_url") val avatarUrl: String
)