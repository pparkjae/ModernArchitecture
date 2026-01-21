package com.park.core.data.repository

import com.park.core.model.GitUser
import com.park.core.model.GitUserRepo
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun user(id: String): GitUser

    suspend fun userRepo(id: String): List<GitUserRepo>
}