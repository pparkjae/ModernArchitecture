package com.park.core.network.retrofit

import com.park.core.network.NetworkDataSource
import com.park.core.network.api.NetworkApi
import com.park.core.network.model.NetworkGitSearchRepo
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class RetrofitNetwork @Inject constructor() : NetworkDataSource {

    val json: Json = Json {
        ignoreUnknownKeys = true
    }

    private val networkApi =
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val newRequest = chain.request().newBuilder()
                            .build()
                        chain.proceed(newRequest)
                    }
                    .build()
            )
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(NetworkApi::class.java)

    override suspend fun user(id: String) = networkApi.user(id)

    override suspend fun useRepos(id: String) = networkApi.userRepo(id)

    override suspend fun searchRepos(
        query: String,
        page: Int,
        perPage: Int
    ): NetworkGitSearchRepo = networkApi.searchRepositories(
        query = query,
        page = page,
        perPage = perPage
    )
}