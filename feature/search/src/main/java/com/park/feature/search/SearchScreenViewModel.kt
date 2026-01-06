package com.park.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.park.core.data.repository.SearchRepository
import com.park.core.model.GitUserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {
    private val _searchRepositoryKeyword = MutableStateFlow("")
    val searchRepositoryKeyword = _searchRepositoryKeyword.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagingDataFlow: Flow<PagingData<GitUserRepo>> = _searchRepositoryKeyword
        .filter { it.isNotBlank() }
        .flatMapLatest { searchKeyword ->
            searchRepository.searchRepos(searchKeyword)
        }
        .cachedIn(viewModelScope)

    fun search(searchKeyword: String) {
        if (searchKeyword.isNotBlank() && searchKeyword != _searchRepositoryKeyword.value) {
            _searchRepositoryKeyword.value = searchKeyword
        }
    }
}
