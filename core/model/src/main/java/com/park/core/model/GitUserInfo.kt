package com.park.core.model

data class GitUserInfo (
    val gitUser: GitUser,
    val userRepo: List<GitUserRepo>
)