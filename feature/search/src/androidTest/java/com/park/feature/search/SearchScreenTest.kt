package com.park.feature.search

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.park.core.model.GitUserRepo
import com.park.core.test.data.testRepos
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.Test

@RunWith(AndroidJUnit4::class)
class SearchScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun searchScreen_emptyResult_showsGuide() {
        val emptyPagingFlow = flowOf(PagingData.empty<GitUserRepo>())

        composeTestRule.setContent {
            val items = emptyPagingFlow.collectAsLazyPagingItems()
            SearchScreen(
                searchKeyWord = "",
                searchResults = items,
                onSearchClick = {},
                onItemClick = {}
            )
        }
        composeTestRule
            .onNodeWithTag("emptySearchGuide")
            .assertIsDisplayed()
    }

    @Test
    fun searchScreen_withData_showsList() {
        val dummyPagingFlow = flowOf(PagingData.from(testRepos))

        composeTestRule.setContent {
            val items = dummyPagingFlow.collectAsLazyPagingItems()
            SearchScreen(
                searchKeyWord = "Android",
                searchResults = items,
                onSearchClick = {},
                onItemClick = {}
            )
        }

        composeTestRule
            .onAllNodesWithTag("languageTag", useUnmergedTree = true)
            .onFirst()
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("open-android")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("open-compose")
            .assertIsDisplayed()
    }

    @Test
    fun searchScreen_inputInteraction() {
        val emptyPagingFlow = flowOf(PagingData.empty<GitUserRepo>())

        composeTestRule.setContent {
            val items = emptyPagingFlow.collectAsLazyPagingItems()
            SearchScreen(
                searchKeyWord = "",
                searchResults = items,
                onSearchClick = {},
                onItemClick = {}
            )
        }

        val inputText = "Compose"
        composeTestRule
            .onNodeWithText("검색어를 입력해주세요")
            .performTextInput(inputText)

        composeTestRule
            .onNodeWithText(inputText)
            .assertIsDisplayed()

         composeTestRule
             .onNodeWithContentDescription("Search Icon")
             .performClick()
    }
}