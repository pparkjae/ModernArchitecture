package com.park.core.network.model

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class NetworkGitSearchRepo(
    @SerialName("total_count") val totalCount: Int,
    @SerialName("items") val items: List<NetworkGitRepo>
)