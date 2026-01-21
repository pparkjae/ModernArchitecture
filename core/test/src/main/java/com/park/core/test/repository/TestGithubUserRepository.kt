package com.park.core.test.repository

import com.park.core.data.repository.UserRepository
import com.park.core.model.GitUser
import com.park.core.model.GitUserRepo

class TestGithubUserRepository : UserRepository {

    private var testUser: GitUser? = null
    private var testRepos: List<GitUserRepo> = emptyList()
    private var shouldThrowError = false

    override suspend fun user(id: String): GitUser {
        if (shouldThrowError) throw Exception("Test Error")
        return testUser ?: throw IllegalStateException("Test user is not set. Call sendUser() first.")
    }

    override suspend fun userRepo(id: String): List<GitUserRepo> {
        if (shouldThrowError) throw Exception("Test Error")
        return testRepos
    }

    fun sendUser(user: GitUser) {
        testUser = user
    }

    fun sendRepos(repos: List<GitUserRepo>) {
        testRepos = repos
    }

    fun setShouldThrowError(value: Boolean) {
        shouldThrowError = value
    }
}