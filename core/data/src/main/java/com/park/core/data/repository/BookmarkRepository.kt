package com.park.core.data.repository

import com.park.core.model.GitUserRepo
import kotlinx.coroutines.flow.Flow


interface BookmarkRepository {
    fun getBookmarkedIds(): Flow<Set<Int>>
    suspend fun addBookmark(gitUserRepo: GitUserRepo)
    suspend fun removeBookmark(id: Int)
}