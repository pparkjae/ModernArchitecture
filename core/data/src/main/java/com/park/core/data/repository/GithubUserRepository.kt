package com.park.core.data.repository

import com.park.core.data.model.asExternalModel
import com.park.core.model.GitUser
import com.park.core.model.GitUserRepo
import com.park.core.network.NetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class GithubUserRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : UserRepository {
    override suspend fun user(id: String): GitUser = networkDataSource.user(id).asExternalModel()

    override suspend fun userRepo(id: String): List<GitUserRepo> = networkDataSource.useRepos(id).asExternalModel()
}