package com.park.core.network

import com.park.core.network.model.NetworkGitUser
import com.park.core.network.model.NetworkUserData
import com.park.core.network.model.NetworkUserRepos
import kotlinx.serialization.InternalSerializationApi

interface NetworkDataSource {
    @OptIn(InternalSerializationApi::class)
    suspend fun user(): NetworkGitUser

    @OptIn(InternalSerializationApi::class)
    suspend fun useRepos(): List<NetworkUserRepos>

    @OptIn(InternalSerializationApi::class)
    suspend fun getUser(name: String, page: Int, itemCount: Int?): NetworkUserData
}