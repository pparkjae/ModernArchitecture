package com.park.core.data.repository

import com.park.core.model.GitUser
import com.park.core.model.GitUserRepos
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun user(id: String): Flow<GitUser>

    fun userRepos(id: String): Flow<List<GitUserRepos>>
}