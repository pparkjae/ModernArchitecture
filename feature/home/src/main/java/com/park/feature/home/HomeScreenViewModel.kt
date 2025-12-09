package com.park.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.park.core.data.repository.UserRepository
import com.park.core.model.GitInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    userRepository: UserRepository
) : ViewModel() {

    private val targetUserId = MutableStateFlow("pparkjae")

    @OptIn(ExperimentalCoroutinesApi::class)
    val user: StateFlow<HomeUiState> = targetUserId
        .flatMapLatest { userId ->
            if (userId.isEmpty()) {
                flowOf(HomeUiState.EmptyQuery)
            } else {
                combine(
                    userRepository.user(userId),
                    userRepository.userRepos(userId)
                ) { user, repos ->
                    GitInfo(
                        gitUser = user,
                        userRepos = repos
                            .filter {
                                it.owner.nodeId.equals(user.nodeId, true)
                            }
                            .sortedByDescending { it.createdAt },
                    )
                }.map<GitInfo, HomeUiState> {
                    HomeUiState.Success(it)
                }
            }
        }
        .catch {
            emit(HomeUiState.LoadFailed)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState.Loading,
        )
}

