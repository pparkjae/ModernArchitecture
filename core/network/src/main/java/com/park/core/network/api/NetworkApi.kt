package com.park.core.network.api

import com.park.core.network.model.NetworkGitUser
import com.park.core.network.model.NetworkUserData
import com.park.core.network.model.NetworkUserRepos
import kotlinx.serialization.InternalSerializationApi
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkApi {
    @InternalSerializationApi
    @GET("users/{id}")
    suspend fun user(
        @Path("id") id: String
    ): NetworkGitUser

    @InternalSerializationApi
    @GET("users/{id}/repos")
    suspend fun userRepo(
        @Path("id") id: String
    ): List<NetworkUserRepos>

    @InternalSerializationApi
    @GET("search/users")
    suspend fun getUser(
        @Query("q") name: String,
        @Query("page") page: Int,
        @Query("per_page") itemCount: Int
    ): NetworkUserData
}