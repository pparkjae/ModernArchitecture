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
    override fun user(): Flow<GitUser> = flow {
        emit(networkDataSource.user().asExternalModel())
    }.flowOn(Dispatchers.IO)

    override fun userRepos(): Flow<List<GitUserRepos>> = flow {
        emit(networkDataSource.useRepos().asExternalModel())
    }.flowOn(Dispatchers.IO)
}