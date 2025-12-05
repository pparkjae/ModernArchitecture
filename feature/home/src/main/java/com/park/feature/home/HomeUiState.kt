package com.park.feature.home

import com.park.core.model.GitInfo

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data object EmptyQuery : HomeUiState

    data object LoadFailed : HomeUiState

    data class Success(val gitInfo: GitInfo) : HomeUiState
}