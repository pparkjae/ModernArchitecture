package com.park.feature.search

import app.cash.turbine.test
import com.park.core.domain.GithubBookmarkPageUseCase
import com.park.core.test.data.testRepo
import com.park.core.test.repository.TestBookmarkRepository
import com.park.core.test.repository.TestGithubSearchRepository
import com.park.core.test.util.MainDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test

class SearchScreenViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val searchRepository = TestGithubSearchRepository()
    private val bookmarkRepository = TestBookmarkRepository()
    private val githubBookmarkPageUseCase = GithubBookmarkPageUseCase(
        searchRepository = searchRepository,
        bookmarkRepository = bookmarkRepository
    )
    private val viewModel = SearchScreenViewModel(githubBookmarkPageUseCase)

    @Test
    fun search_success() = runTest {
        val searchKeyword = "Android"

        viewModel.pagingDataFlow.test {
            viewModel.search(searchKeyword)

            assertEquals(searchKeyword, viewModel.searchRepositoryKeyword.value)

            searchRepository.emit(testRepo)

            val pagingData = awaitItem()
            assertNotNull(pagingData)

            assertEquals(searchKeyword, searchRepository.searchKeyWord)
        }
    }

    @Test
    fun search_empty() = runTest {
        viewModel.search("")

        assertEquals("", viewModel.searchRepositoryKeyword.value)
        assertEquals("", searchRepository.searchKeyWord)
    }

    @Test
    fun search_duplicate() = runTest {
        val searchKeyword = "Android"

        viewModel.pagingDataFlow.test {
            viewModel.search(searchKeyword)
            searchRepository.emit(emptyList())
            awaitItem()

            viewModel.search(searchKeyword)

            expectNoEvents()
        }
    }

    @Test
    fun search_bookmark_success() = runTest {
        val searchKeyword = "Android"
        val targetRepo = testRepo.first()

        viewModel.pagingDataFlow.test {
            viewModel.search(searchKeyword)
            searchRepository.emit(testRepo)

            val initialPagingData = awaitItem()
            assertNotNull(initialPagingData)

            viewModel.addBookmark(targetRepo)

            val updatedPagingData = awaitItem()
            assertNotNull(updatedPagingData)

            val bookmarkedIds = bookmarkRepository.getBookmarkedIds().first()
            assert(bookmarkedIds.contains(targetRepo.id))
        }
    }
}