package com.park.core.test.repository

import com.park.core.data.repository.UserRepository
import com.park.core.model.GitUser
import com.park.core.model.GitUserRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow

class TestGithubUserRepository : UserRepository {

    private val userFlow = MutableSharedFlow<GitUser>(replay = 1)
    private val reposFlow = MutableSharedFlow<List<GitUserRepo>>(replay = 1)
    private var shouldThrowError = false

    override fun user(id: String): Flow<GitUser> = flow {
        if (shouldThrowError) throw Exception("Test Error")
        userFlow.collect { emit(it) }
    }

    override fun userRepo(id: String): Flow<List<GitUserRepo>> = flow {
        if (shouldThrowError) throw Exception("Test Error")
        reposFlow.collect { emit(it) }
    }

    suspend fun emitUser(user: GitUser) {
        userFlow.emit(user)
    }

    suspend fun emitRepos(repos: List<GitUserRepo>) {
        reposFlow.emit(repos)
    }

    fun setShouldThrowError(value: Boolean) {
        shouldThrowError = value
    }
}