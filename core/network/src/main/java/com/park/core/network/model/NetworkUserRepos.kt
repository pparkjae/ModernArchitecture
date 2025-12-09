package com.park.core.network.model

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@InternalSerializationApi
@Serializable
data class NetworkUserRepos(
    @SerialName("id") val id: Int,
    @SerialName("node_id") val nodeId: String,
    @SerialName("name") val name: String,
    @SerialName("private") val private: Boolean,
    @SerialName("created_at") val createdAt: String,
    @SerialName("owner") val owner: NetworkUserRepoOwner,
    @SerialName("html_url") val htmlUrl: String,
)

@InternalSerializationApi
@Serializable
data class NetworkUserRepoOwner(
    @SerialName("login") val login: String,
    @SerialName("node_id") val nodeId: String,
)