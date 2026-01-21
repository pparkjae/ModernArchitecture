package com.park.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.park.core.domain.GithubUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    githubUserInfoUseCase: GithubUserInfoUseCase
) : ViewModel() {

    private val gitUserId = MutableStateFlow("")

    fun searchGitUser(gitId: String) {
        gitUserId.value = gitId
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val user: StateFlow<HomeUiState> = gitUserId
        .flatMapLatest { userId ->
            flow {
                if (userId.isEmpty()) {
                    emit(HomeUiState.EmptyResult)
                } else {
                    emit(HomeUiState.Loading)
                    try {
                        val userInfo = githubUserInfoUseCase(userId)
                        emit(HomeUiState.Success(userInfo))
                    } catch (e: Exception) {
                        emit(HomeUiState.LoadFailed)
                    }
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading,
        )
}

