package com.park.core.data.repository

import androidx.paging.PagingData
import com.park.core.model.GitUserRepo
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchRepos(searchKeyWord: String): Flow<PagingData<GitUserRepo>>
}