package com.park.core.data.model

import com.park.core.model.GitUser
import com.park.core.model.ItemData
import com.park.core.model.UserData
import com.park.core.network.model.NetworkGitUser
import com.park.core.network.model.NetworkItemData
import com.park.core.network.model.NetworkUserData

fun NetworkGitUser.asExternalModel(): GitUser =
    GitUser(
        login = login,
        id = id,
        nodeId = nodeId,
        avatarUrl = avatarUrl,
        gravatarId = gravatarId,
        url = url,
        htmlUrl = htmlUrl,
        followersUrl = followersUrl,
        gistsUrl = gistsUrl,
        starredUrl = starredUrl,
        subscriptionsUrl = subscriptionsUrl,
        organizationsUrl = organizationsUrl,
        reposUrl = reposUrl,
        eventsUrl = eventsUrl,
        receivedEventsUrl = receivedEventsUrl,
        type = type,
        userViewType = userViewType,
        siteAdmin = siteAdmin,
        name = name,
    )

fun NetworkUserData.asExternalModel() =
    UserData(
        incompleteResults = incompleteResults,
        items = items.map { networkItemData ->
            networkItemData.asExternalModel()
        },
        totalCount = totalCount
    )

fun NetworkItemData.asExternalModel(): ItemData =
    ItemData(
        avatarUrl = avatarUrl,
        eventsUrl = eventsUrl,
        followersUrl = followersUrl,
        followingUrl = followingUrl,
        gistsUrl = gistsUrl,
        gravatarId = gravatarId,
        htmlUrl = htmlUrl,
        id = id,
        login = login,
        nodeId = nodeId,
        organizationsUrl = organizationsUrl,
        receivedEventsUrl = receivedEventsUrl,
        reposUrl = reposUrl,
        score = score,
        siteAdmin = siteAdmin,
        starredUrl = starredUrl,
        subscriptionsUrl = subscriptionsUrl,
        type = type,
        url = url
    )