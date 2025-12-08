package com.park.core.data.repository

import com.park.core.data.model.asExternalModel
import com.park.core.model.GitUser
import com.park.core.model.GitUserRepos
import com.park.core.network.NetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class GithubUserRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : UserRepository {
    override fun user(id: String): Flow<GitUser> = flow {
        emit(networkDataSource.user(id).asExternalModel())
    }.flowOn(Dispatchers.IO)

    override fun userRepos(id: String): Flow<List<GitUserRepos>> = flow {
        emit(networkDataSource.useRepos(id).asExternalModel())
    }.flowOn(Dispatchers.IO)
}