package com.park.core.data.model

import com.park.core.model.GitUserRepoOwner
import com.park.core.model.GitUserRepo
import com.park.core.network.model.NetworkGitRepo

fun NetworkGitRepo.asExternalModel() : GitUserRepo =
    GitUserRepo(
        id = id,
        nodeId = nodeId,
        name = name,
        fullName = fullName,
        private = private,
        htmlUrl = htmlUrl,
        description = description,
        createdAt = createdAt,
        language = language,
        pushedAt = pushedAt,
        stargazersCount = stargazersCount,
        topics = topics,
        owner = GitUserRepoOwner(
            login = owner.login,
            nodeId = owner.nodeId,
            avatarUrl = owner.avatarUrl
        )
    )
fun List<NetworkGitRepo>.asExternalModel() : List<GitUserRepo> =
    map(NetworkGitRepo::asExternalModel)