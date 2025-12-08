package com.park.core.test.data

import com.park.core.model.GitUser
import com.park.core.model.GitUserRepoOwner
import com.park.core.model.GitUserRepos

val testUser = GitUser(
    name = "Park",
    htmlUrl = "https://github.com/park",
    avatarUrl = "https://avatars.githubusercontent.com/u/22655078?v=4",
    login = "pparkjae",
    id = 22655078,
    nodeId = "MDQ6VXNlcjIyNjU1MDc4",
    gravatarId = "",
    url = "https://api.github.com/users/pparkjae",
    followersUrl = "https://api.github.com/users/pparkjae/followers",
    gistsUrl = "https://api.github.com/users/pparkjae/gists{/gist_id}",
    starredUrl = "https://api.github.com/users/pparkjae/starred{/owner}{/repo}",
    subscriptionsUrl = "https://api.github.com/users/pparkjae/subscriptions",
    organizationsUrl = "https://api.github.com/users/pparkjae/orgs",
    reposUrl = "https://api.github.com/users/pparkjae/repos",
    eventsUrl = "https://api.github.com/users/pparkjae/events{/privacy}",
    receivedEventsUrl = "https://api.github.com/users/pparkjae/received_events",
    type = "User",
    userViewType = "public",
    siteAdmin = false
)

val testRepos = listOf(
    GitUserRepos(
        id = 1,
        name = "Android-App",
        htmlUrl = "url",
        private = false,
        nodeId = "MDEwOlJlcG9zaXRvcnkzODA5MjQzNzg=",
        owner = GitUserRepoOwner(
            login = "pparkjae",
            nodeId = "MDQ6VXNlcjIyNjU1MDc4"
        )
    ),
    GitUserRepos(
        id = 2,
        name = "Compose-App",
        htmlUrl = "url",
        private = true,
        nodeId = "MDEwOlJlcG9zaXRvcnkzODA5MjQzNzg=",
        owner = GitUserRepoOwner(
            login = "pparkjae",
            nodeId = "MDQ6VXNlcjIyNjU1MDc4"
        )
    )
)

val myRepo = GitUserRepos(
    id = 1,
    name = "Android-App",
    htmlUrl = "url",
    private = false,
    nodeId = "MDEwOlJlcG9zaXRvcnkzODA5MjQzNzg=",
    owner = GitUserRepoOwner(
        login = "pparkjae",
        nodeId = "MDQ6VXNlcjIyNjU1MDc4"
    )
)

val notMyRepo = GitUserRepos(
    id = 2,
    name = "Compose-App",
    htmlUrl = "url",
    private = true,
    nodeId = "MDEwOlJlcG9zaXRvcnkzODA5MjQzNzg=",
    owner = GitUserRepoOwner(
        login = "pparkjae3",
        nodeId = "MDQ6VXNlcjIyNjU1MDc3"
    )
)