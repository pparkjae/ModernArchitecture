package com.park.core.domain

import com.park.core.data.repository.UserRepository
import com.park.core.model.GitUserInfo
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class GithubUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String): GitUserInfo = coroutineScope {
        val userDeferred = async { userRepository.user(userId) }
        val reposDeferred = async { userRepository.userRepo(userId) }

        val user = userDeferred.await()
        val repo = reposDeferred.await()

        GitUserInfo(
            gitUser = user,
            userRepo = repo
                .filter { it.owner.nodeId.equals(user.nodeId, true) }
                .sortedByDescending { it.createdAt }
        )
    }
}