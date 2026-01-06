package com.park.feature.home

import app.cash.turbine.test
import com.park.core.model.GitUser
import com.park.core.test.data.myRepo
import com.park.core.test.data.notMyRepo
import com.park.core.test.data.testUser
import com.park.core.test.repository.TestGithubUserRepository
import com.park.core.test.util.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule

class HomeScreenViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val userRepository = TestGithubUserRepository()
    private val viewModel = HomeScreenViewModel(userRepository)

    @Test
    fun user_loading() = runTest {
        assertEquals(HomeUiState.Loading, viewModel.user.value)
    }

    @Test
    fun user_success() = runTest {
        viewModel.searchGitUser("PParkjae")

        viewModel.user.test {
            assertEquals(HomeUiState.Loading, awaitItem())

            userRepository.emitUser(testUser)
            userRepository.emitRepos(listOf(myRepo, notMyRepo))

            val successState = awaitItem() as HomeUiState.Success

            assertEquals(1, successState.gitUserInfo.userRepos.size)
            assertEquals(
                successState.gitUserInfo.gitUser.nodeId,
                successState.gitUserInfo.userRepos[0].owner.nodeId
            )
        }
    }

    @Test
    fun user_failed() = runTest {
        viewModel.searchGitUser("PParkjae")
        userRepository.setShouldThrowError(true)

        viewModel.user.test {
            assertEquals(HomeUiState.Loading, awaitItem())

            userRepository.emitUser(
                GitUser(
                    name = "Test",
                    nodeId = "1",
                    htmlUrl = "",
                    avatarUrl = "",
                    login = "",
                    id = -1,
                    gravatarId = "",
                    url = "",
                    followersUrl = "",
                    gistsUrl = "",
                    starredUrl = "",
                    subscriptionsUrl = "",
                    organizationsUrl = "",
                    reposUrl = "",
                    eventsUrl = "",
                    receivedEventsUrl = "",
                    type = "",
                    userViewType = "",
                    siteAdmin = false
                )
            )
            userRepository.emitRepos(emptyList())

            assertEquals(HomeUiState.LoadFailed, awaitItem())
        }
    }
}