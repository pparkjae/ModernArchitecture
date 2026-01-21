package com.park.core.data.repository

import com.park.core.data.model.asEntity
import com.park.core.database.dao.BookmarkDao
import com.park.core.model.GitUserRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class GithubRepoBookmarkRepository @Inject constructor(
    private val bookmarkDao: BookmarkDao
) : BookmarkRepository {
    override fun getBookmarkedIds(): Flow<Set<Int>> {
        return bookmarkDao.getBookmarkedIds()
            .map { ids ->
                ids.toSet()
            }
            .distinctUntilChanged()
    }

    override suspend fun addBookmark(gitUserRepo: GitUserRepo) {
        bookmarkDao.insertBookmark(gitUserRepo.asEntity())
    }

    override suspend fun removeBookmark(id: Int) {
        bookmarkDao.deleteBookmark(id)
    }
}