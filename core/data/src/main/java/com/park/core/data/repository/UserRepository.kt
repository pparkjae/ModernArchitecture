package com.park.core.data.repository

import com.park.core.model.GitUser
import com.park.core.model.GitUserRepo
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun user(id: String): Flow<GitUser>

    fun userRepo(id: String): Flow<List<GitUserRepo>>
}