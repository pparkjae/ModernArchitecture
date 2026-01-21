package com.park.core.data.model

import com.park.core.database.model.GitUserRepoBookmarkEntity
import com.park.core.model.GitUserRepo
import com.park.core.model.GitUserRepoOwner

fun GitUserRepo.asEntity(): GitUserRepoBookmarkEntity {
    return GitUserRepoBookmarkEntity(
        id = id,
        nodeId = nodeId,
        name = name,
        fullName = fullName,
        description = description,
        stargazersCount = stargazersCount,
        language = language,
        htmlUrl = htmlUrl,
        ownerName = owner.login,
        ownerAvatarUrl = owner.avatarUrl,
        topics = topics,
        pushedAt = pushedAt
    )
}

fun GitUserRepoBookmarkEntity.asExternalModel(): GitUserRepo {
    return GitUserRepo(
        id = id,
        nodeId = nodeId,
        name = name,
        fullName = fullName,
        private = false,
        htmlUrl = htmlUrl,
        description = description,
        createdAt = "",
        language = language,
        pushedAt = pushedAt,
        stargazersCount = stargazersCount,
        topics = topics,
        owner = GitUserRepoOwner(
            login = ownerName,
            nodeId = "",
            avatarUrl = ownerAvatarUrl
        )
    )
}