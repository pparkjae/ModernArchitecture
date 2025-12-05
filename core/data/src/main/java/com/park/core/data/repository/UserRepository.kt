package com.park.core.data.repository

import com.park.core.model.GitUser
import com.park.core.model.GitUserRepos
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun user(): Flow<GitUser>

    fun userRepos(): Flow<List<GitUserRepos>>
}