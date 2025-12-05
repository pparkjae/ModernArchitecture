package com.park.core.network.retrofit

import com.park.core.network.BuildConfig
import com.park.core.network.NetworkDataSource
import com.park.core.network.api.NetworkApi
import com.park.core.network.model.NetworkUserData
import com.park.core.network.model.NetworkUserRepos
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class RetrofitNetwork @Inject constructor() : NetworkDataSource {

    companion object {
        private const val DEFAULT_ITEM_COUNT = 30
    }

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
                            // TODO: Token 위치 변경
                            .addHeader("Authorization", "Bearer ${BuildConfig.GITHUB_ACCESS_TOKEN}")
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

    @OptIn(InternalSerializationApi::class)
    override suspend fun user() = networkApi.user()

    @OptIn(InternalSerializationApi::class)
    override suspend fun useRepos() = networkApi.userRepo()

    @OptIn(InternalSerializationApi::class)
    override suspend fun getUser(
        name: String,
        page: Int,
        itemCount: Int?
    ): NetworkUserData = networkApi.getUser(name, page, itemCount ?: DEFAULT_ITEM_COUNT)
}