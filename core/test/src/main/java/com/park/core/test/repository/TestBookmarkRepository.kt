package com.park.core.test.repository

import com.park.core.data.repository.BookmarkRepository
import com.park.core.model.GitUserRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class TestBookmarkRepository : BookmarkRepository {
    private val testBookmarkedIds = MutableStateFlow<Set<Int>>(emptySet())

    override fun getBookmarkedIds(): Flow<Set<Int>> = testBookmarkedIds

    override suspend fun addBookmark(gitUserRepo: GitUserRepo) {
        testBookmarkedIds.update { it + gitUserRepo.id }
    }

    override suspend fun removeBookmark(id: Int) {
        testBookmarkedIds.update { it - id }
    }
}