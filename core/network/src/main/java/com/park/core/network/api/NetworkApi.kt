package com.park.core.network.api

import com.park.core.network.model.NetworkGitUser
import com.park.core.network.model.NetworkUserData
import com.park.core.network.model.NetworkUserRepos
import kotlinx.serialization.InternalSerializationApi
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {
    @InternalSerializationApi
    @GET("user")
    suspend fun user(): NetworkGitUser

    @InternalSerializationApi
    @GET("user/repos")
    suspend fun userRepo(): List<NetworkUserRepos>

    @InternalSerializationApi
    @GET("search/users")
    suspend fun getUser(
        @Query("q") name: String,
        @Query("page") page: Int,
        @Query("per_page") itemCount: Int
    ): NetworkUserData
}