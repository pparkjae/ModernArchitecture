package com.park.feature.home

import com.park.core.model.GitUserInfo

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data object EmptyResult : HomeUiState

    data object LoadFailed : HomeUiState

    data class Success(val gitUserInfo: GitUserInfo) : HomeUiState
}