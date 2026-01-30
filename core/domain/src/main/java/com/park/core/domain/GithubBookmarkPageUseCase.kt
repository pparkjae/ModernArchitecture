package com.park.core.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.park.core.data.repository.BookmarkRepository
import com.park.core.data.repository.SearchRepository
import com.park.core.model.GitUserRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GithubBookmarkPageUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
    private val bookmarkRepository: BookmarkRepository
) {
    fun searchRepos(searchKeyword: String) = searchRepository.searchRepos(searchKeyword)

    fun searchReposWithBookmarkStatus(repos: Flow<PagingData<GitUserRepo>>): Flow<PagingData<GitUserRepo>> {
        val bookmarkIdsFlow = bookmarkRepository.getBookmarkedIds()

        return combine(repos, bookmarkIdsFlow) { pagingData, bookmarkedIds ->
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