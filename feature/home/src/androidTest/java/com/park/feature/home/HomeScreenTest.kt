package com.park.feature.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.park.core.model.GitUserInfo
import com.park.core.test.data.testRepos
import com.park.core.test.data.testUser
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class HomeScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val testHomeData = GitUserInfo(
        gitUser = testUser,
        userRepos = testRepos
    )

    @Test
    fun homeScreen_loading() {
        composeTestRule.setContent {
            HomeScreen(
                homeUiState = HomeUiState.Loading,
                modifier = androidx.compose.ui.Modifier
            )
        }

        composeTestRule
            .onNodeWithTag("loadingColumn")
            .assertIsDisplayed()
    }

    @Test
    fun homeScreen_success() {
        composeTestRule.setContent {
            HomeScreen(
                homeUiState = HomeUiState.Success(testHomeData),
                modifier = androidx.compose.ui.Modifier
            )
        }

        composeTestRule.onNodeWithText(testUser.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(testUser.htmlUrl).assertIsDisplayed()
        composeTestRule.onNodeWithText("Repositories (${testRepos.size})").assertIsDisplayed()
        composeTestRule.onNodeWithText(testRepos[0].name).assertIsDisplayed()
        composeTestRule.onNodeWithText(testRepos[1].name).assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription("Public Repository").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Private Repository").assertIsDisplayed()
    }
}