package com.park.core.network

import com.park.core.network.model.NetworkGitRepo
import com.park.core.network.model.NetworkGitUser
import com.park.core.network.model.NetworkGitSearchRepo

interface NetworkDataSource {
    companion object {
        const val DEFAULT_ITEM_COUNT = 30
    }

    suspend fun user(id: String): NetworkGitUser

    suspend fun useRepos(id: String): List<NetworkGitRepo>

    suspend fun searchRepos(
        query: String,
        page: Int = 1,
        perPage: Int = DEFAULT_ITEM_COUNT
    ): NetworkGitSearchRepo
}