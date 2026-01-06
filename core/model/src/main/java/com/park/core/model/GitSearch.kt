package com.park.core.model

data class GitSearchRepo(
    val totalCount: Int,
    val items: List<GitUserRepo>
)