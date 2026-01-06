package com.park.core.test.repository

import androidx.paging.PagingData
import com.park.core.data.repository.SearchRepository
import com.park.core.model.GitUserRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow

class TestGithubSearchRepository : SearchRepository {
    private val pagingFlow = MutableSharedFlow<PagingData<GitUserRepo>>(replay = 1)

    var searchKeyWord: String = ""
        private set

    private var shouldThrowError = false

    override fun searchRepos(searchKeyWord: String): Flow<PagingData<GitUserRepo>> = flow {
        this@TestGithubSearchRepository.searchKeyWord = searchKeyWord

        if (shouldThrowError) {
            throw Exception("Test Error")
        }

        pagingFlow.collect { emit(it) }
    }

    suspend fun emit(items: List<GitUserRepo>) {
        pagingFlow.emit(PagingData.from(items))
    }
}