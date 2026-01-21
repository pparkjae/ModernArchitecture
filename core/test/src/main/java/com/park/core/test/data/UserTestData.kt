package com.park.core.test.data

import com.park.core.model.GitUser
import com.park.core.model.GitUserInfo
import com.park.core.model.GitUserRepoOwner
import com.park.core.model.GitUserRepo

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

val testRepo = listOf(
    GitUserRepo(
        id = 1,
        name = "Android-App",
        fullName = "open-android",
        htmlUrl = "url",
        description = "GitHub",
        private = false,
        nodeId = "MDEwOlJlcG9zaXRvcnkzODA5MjQzNzg=",
        createdAt = "2025-01-04T04:07:17Z",
        language = "kotlin",
        pushedAt = "2025-12-22T04:07:17Z",
        stargazersCount = 3400,
        topics = listOf("java", "kotlin"),
        owner = GitUserRepoOwner(
            login = "pparkjae",
            nodeId = "MDQ6VXNlcjIyNjU1MDc4",
            avatarUrl = "https://avatars.githubusercontent.com/u/22655078?v=4"
        )
    ),
    GitUserRepo(
        id = 2,
        name = "Compose-App",
        fullName = "open-compose",
        htmlUrl = "url",
        description = "GitHub",
        private = true,
        nodeId = "MDEwOlJlcG9zaXRvcnkzODA5MjQzNzg=",
        createdAt = "2022-01-04T04:07:17Z",
        language = "java",
        pushedAt = "2025-12-21T04:07:17Z",
        stargazersCount = 3400,
        topics = listOf("java", "kotlin"),
        owner = GitUserRepoOwner(
            login = "pparkjae",
            nodeId = "MDQ6VXNlcjIyNjU1MDc4",
            avatarUrl = "https://avatars.githubusercontent.com/u/22655078?v=4"
        )
    )
)

val myRepo = GitUserRepo(
    id = 1,
    name = "Android-App",
    fullName = "open-android/Android",
    htmlUrl = "url",
    description = "GitHub",
    private = false,
    nodeId = "MDEwOlJlcG9zaXRvcnkzODA5MjQzNzg=",
    createdAt = "2024-01-04T04:07:17Z",
    language = "java",
    pushedAt = "2025-12-21T04:07:17Z",
    stargazersCount = 3400,
    topics = listOf("java", "kotlin"),
    owner = GitUserRepoOwner(
        login = "pparkjae",
        nodeId = "MDQ6VXNlcjIyNjU1MDc4",
        avatarUrl = "https://avatars.githubusercontent.com/u/22655078?v=4"
    )
)

val notMyRepo = GitUserRepo(
    id = 2,
    name = "Compose-App",
    fullName = "open-compose",
    htmlUrl = "url",
    description = "GitHub",
    private = true,
    nodeId = "MDEwOlJlcG9zaXRvcnkzODA5MjQzNzg=",
    createdAt = "2024-04-04T04:07:17Z",
    language = "kotlin",
    pushedAt = "2025-12-23T04:07:17Z",
    stargazersCount = 3000,
    topics = listOf("java", "kotlin"),
    owner = GitUserRepoOwner(
        login = "pparkjae3",
        nodeId = "MDQ6VXNlcjIyNjU1MDc3",
        avatarUrl = "https://avatars.githubusercontent.com/u/22655078?v=3"
    )
)

val testGitUserInfo = GitUserInfo(
    gitUser = testUser,
    userRepo = testRepo
)