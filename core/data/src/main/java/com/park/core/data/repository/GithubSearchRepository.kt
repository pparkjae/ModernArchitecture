package com.park.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.park.core.data.paging.GithubSearchPagingSource
import com.park.core.model.GitUserRepo
import com.park.core.network.NetworkDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GithubSearchRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource
) : SearchRepository {
    override fun searchRepos(searchKeyWord: String): Flow<PagingData<GitUserRepo>> {
        return Pager(
            config = PagingConfig(
                pageSize = NetworkDataSource.DEFAULT_ITEM_COUNT,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GithubSearchPagingSource(
                    networkDataSource = networkDataSource,
                    query = searchKeyWord,
                    perPage = NetworkDataSource.DEFAULT_ITEM_COUNT
                )
            }
        ).flow
    }
}