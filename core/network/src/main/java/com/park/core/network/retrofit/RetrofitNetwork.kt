package com.park.core.network.retrofit

import com.park.core.network.NetworkDataSource
import com.park.core.network.api.NetworkApi
import com.park.core.network.model.NetworkUserData
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
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
    override suspend fun user(id: String) = networkApi.user(id)

    @OptIn(InternalSerializationApi::class)
    override suspend fun useRepos(id: String) = networkApi.userRepo(id)

    @OptIn(InternalSerializationApi::class)
    override suspend fun getUser(
        name: String,
        page: Int,
        itemCount: Int?
    ): NetworkUserData = networkApi.getUser(name, page, itemCount ?: DEFAULT_ITEM_COUNT)
}