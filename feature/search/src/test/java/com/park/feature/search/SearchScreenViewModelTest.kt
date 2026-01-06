package com.park.feature.search

import app.cash.turbine.test
import com.park.core.test.data.testRepos
import com.park.core.test.repository.TestGithubSearchRepository
import com.park.core.test.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

class SearchScreenViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val searchRepository = TestGithubSearchRepository()
    private val viewModel = SearchScreenViewModel(searchRepository)

    @Test
    fun search_success() = runTest {
        val searchKeyword = "Android"

        viewModel.pagingDataFlow.test {
            viewModel.search(searchKeyword)

            assertEquals(searchKeyword, viewModel.searchRepositoryKeyword.value)

            searchRepository.emit(testRepos)

            val pagingData = awaitItem()
            assertNotNull(pagingData)

            assertEquals(searchKeyword, searchRepository.searchKeyWord)
        }
    }

    @Test
    fun search_ignore_empty_result() = runTest {
        viewModel.search("")

        assertEquals("", viewModel.searchRepositoryKeyword.value)
        assertEquals("", searchRepository.searchKeyWord)
    }

    @Test
    fun search_ignore_duplicate_result() = runTest {
        val searchKeyword = "Android"

        viewModel.pagingDataFlow.test {
            viewModel.search(searchKeyword)
            searchRepository.emit(emptyList())
            awaitItem()

            viewModel.search(searchKeyword)

            expectNoEvents()
        }
    }
}