package com.park.core.network.api

import com.park.core.network.model.NetworkGitRepo
import com.park.core.network.model.NetworkGitUser
import com.park.core.network.model.NetworkGitSearchRepo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkApi {
    @GET("users/{id}")
    suspend fun user(
        @Path("id") id: String
    ): NetworkGitUser

    @GET("users/{id}/repos")
    suspend fun userRepo(
        @Path("id") id: String
    ): List<NetworkGitRepo>

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 30
    ): NetworkGitSearchRepo
}