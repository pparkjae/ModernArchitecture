package com.park.core.test.repository

import com.park.core.data.repository.UserRepository
import com.park.core.model.GitUser
import com.park.core.model.GitUserRepos
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow

class TestGithubUserRepository : UserRepository {

    private val userFlow = MutableSharedFlow<GitUser>(replay = 1)
    private val reposFlow = MutableSharedFlow<List<GitUserRepos>>(replay = 1)
    private var shouldThrowError = false

    override fun user(): Flow<GitUser> = flow {
        if (shouldThrowError) throw Exception("Test Error")
        userFlow.collect { emit(it) }
    }

    override fun userRepos(): Flow<List<GitUserRepos>> = flow {
        if (shouldThrowError) throw Exception("Test Error")
        reposFlow.collect { emit(it) }
    }

    suspend fun emitUser(user: GitUser) {
        userFlow.emit(user)
    }

    suspend fun emitRepos(repos: List<GitUserRepos>) {
        reposFlow.emit(repos)
    }

    fun setShouldThrowError(value: Boolean) {
        shouldThrowError = value
    }
}