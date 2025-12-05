package com.park.core.data.model

import com.park.core.model.GitUserRepoOwner
import com.park.core.model.GitUserRepos
import com.park.core.network.model.NetworkUserRepos

fun NetworkUserRepos.asExternalModel() : GitUserRepos =
    GitUserRepos(
        id = id,
        nodeId = nodeId,
        name = name,
        private = private,
        htmlUrl = htmlUrl,
        owner = GitUserRepoOwner(
            login = owner.login,
            nodeId = owner.nodeId
        )
    )
fun List<NetworkUserRepos>.asExternalModel() : List<GitUserRepos> =
    map(NetworkUserRepos::asExternalModel)