package com.park.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.park.core.data.model.asExternalModel
import com.park.core.model.GitUserRepo
import com.park.core.network.NetworkDataSource

class GithubSearchPagingSource(
    private val networkDataSource: NetworkDataSource,
    private val query: String,
    private val perPage: Int
) : PagingSource<Int, GitUserRepo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GitUserRepo> {
        val page = params.key ?: 1

        return try {
            val response = networkDataSource.searchRepos(
                query = query,
                page = page,
                perPage = params.loadSize
            )

            val items = response.items.map { it.asExternalModel() }

            LoadResult.Page(
                data = items,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (items.isEmpty() || items.size < perPage) {
                    null
                } else {
                    page + 1
                }
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GitUserRepo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}