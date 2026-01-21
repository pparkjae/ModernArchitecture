package com.park.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.park.core.domain.GithubBookmarkPageUseCase
import com.park.core.model.GitUserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val githubBookmarkPageUseCase: GithubBookmarkPageUseCase
) : ViewModel() {
    private val _searchRepositoryKeyword = MutableStateFlow("")
    val searchRepositoryKeyword = _searchRepositoryKeyword.asStateFlow()

    val pagingDataFlow: Flow<PagingData<GitUserRepo>> = _searchRepositoryKeyword
        .filter { it.isNotBlank() }
        .flatMapLatest { searchKeyword ->
            githubBookmarkPageUseCase.searchReposWithBookmarkStatus(searchKeyword, viewModelScope)
        }
        .cachedIn(viewModelScope)

    fun search(searchKeyword: String) {
        if (searchKeyword.isNotBlank() && searchKeyword != _searchRepositoryKeyword.value) {
            _searchRepositoryKeyword.value = searchKeyword
        }
    }

    fun addBookmark(repo: GitUserRepo) {
        viewModelScope.launch {
            githubBookmarkPageUseCase.addBookmark(repo)
        }
    }

    fun removeBookmark(repoId: Int) {
        viewModelScope.launch {
            githubBookmarkPageUseCase.removeBookmark(repoId)
        }
    }
}
