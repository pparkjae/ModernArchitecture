package com.park.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.park.core.data.repository.UserRepository
import com.park.core.model.GitInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    userRepository: UserRepository
) : ViewModel() {

    val user: StateFlow<HomeUiState> =
        combine(
            userRepository.user(),
            userRepository.userRepos()
        ) { user, repos ->
            GitInfo(
                gitUser = user,
                userRepos = repos.filter {
                    it.owner.nodeId.equals(user.nodeId, true)
                },
            )
        }.map<GitInfo, HomeUiState> { user ->
            HomeUiState.Success(user)
        }.catch {
            Log.e("PARK" ,it.toString())
            emit(HomeUiState.LoadFailed)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading,
        )
}

