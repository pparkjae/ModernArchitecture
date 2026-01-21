package com.park.core.domain

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.park.core.data.repository.BookmarkRepository
import com.park.core.data.repository.SearchRepository
import com.park.core.model.GitUserRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GithubBookmarkPageUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
    private val bookmarkRepository: BookmarkRepository
) {
    fun searchReposWithBookmarkStatus(searchKeyword: String, scope: CoroutineScope): Flow<PagingData<GitUserRepo>> {
        val searchFlow = searchRepository
            .searchRepos(searchKeyword)
            .cachedIn(scope)

        val bookmarkIdsFlow = bookmarkRepository.getBookmarkedIds()

        return combine(searchFlow, bookmarkIdsFlow) { pagingData, bookmarkedIds ->
            pagingData.map { repo ->
                repo.copy(
                    isBookmarked = bookmarkedIds.contains(repo.id)
                )
            }
        }
    }

    suspend fun addBookmark(gitUserRepo: GitUserRepo) {
        bookmarkRepository.addBookmark(gitUserRepo)
    }

    suspend fun removeBookmark(id: Int) {
        bookmarkRepository.removeBookmark(id)
    }
}