package com.park.feature.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.park.core.design.component.NetworkImageLayout
import com.park.core.model.GitUserRepo
import com.park.core.test.data.testRepos
import kotlinx.coroutines.flow.flowOf
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

private val repoDateFormatter =
    SimpleDateFormat("yyyy년 M월 d일", Locale.KOREA).apply {
        timeZone = TimeZone.getDefault()
    }

@Composable
internal fun SearchRoute(
    modifier: Modifier = Modifier,
    viewModel: SearchScreenViewModel = hiltViewModel(),
) {
    val searchResults = viewModel.pagingDataFlow.collectAsLazyPagingItems()
    val searchRepositoryKeyword by viewModel.searchRepositoryKeyword.collectAsStateWithLifecycle()

    SearchScreen(
        modifier = modifier,
        searchKeyWord = searchRepositoryKeyword,
        searchResults = searchResults,
        onSearchClick = viewModel::search,
        onItemClick = {

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchKeyWord: String,
    searchResults: LazyPagingItems<GitUserRepo>,
    onSearchClick: (String) -> Unit,
    onItemClick: (String) -> Unit
) {
    var inputText by rememberSaveable { mutableStateOf(searchKeyWord) }
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("GitHub Repository Search") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            SearchInputField(
                inputText = inputText,
                onValueChange = { inputText = it },
                onSearchDone = {
                    onSearchClick(inputText)
                    focusManager.clearFocus()
                }
            )

            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

            if (searchKeyWord.isBlank()) {
                EmptySearchGuide()
            } else {
                RepoPagingList(
                    modifier = Modifier.weight(1f),
                    pagingItems = searchResults,
                    onItemClick = onItemClick
                )
            }
        }
    }
}

@Composable
fun SearchInputField(
    inputText: String,
    onValueChange: (String) -> Unit,
    onSearchDone: () -> Unit
) {
    OutlinedTextField(
        value = inputText,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text("검색어를 입력해주세요") },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
        },
        trailingIcon = {
            if (inputText.isNotEmpty()) {
                IconButton(onClick = { onValueChange("") }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Clear")
                }
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearchDone() }),
        singleLine = true,
        shape = MaterialTheme.shapes.medium
    )
}

@Composable
fun RepoPagingList(
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<GitUserRepo>,
    onItemClick: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            count = pagingItems.itemCount,
            key = pagingItems.itemKey { it.id },
            contentType = pagingItems.itemContentType { "repos" }
        ) { index ->
            val repo = pagingItems[index]
            if (repo != null) {
                RepoItem(gitUserRepo = repo, onClick = { onItemClick(repo.htmlUrl) })
            }
        }

        val loadState = pagingItems.loadState
        when {
            loadState.refresh is LoadState.Loading -> {
                item {
                    Box(
                        modifier = Modifier.fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) { CircularProgressIndicator() }
                }
            }
            loadState.refresh is LoadState.Error -> {
                val e = loadState.refresh as LoadState.Error
                item {
                    ErrorRetryView(
                        message = e.error.localizedMessage ?: "알 수 없는 오류가 발생했습니다.",
                        onRetry = { pagingItems.retry() }
                    )
                }
            }
            loadState.append is LoadState.Loading -> {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) { CircularProgressIndicator(modifier = Modifier.size(24.dp)) }
                }
            }
            loadState.append is LoadState.Error -> {
                item {
                    TextButton(
                        onClick = { pagingItems.retry() },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("다시 시도") }
                }
            }
            loadState.refresh is LoadState.NotLoading && pagingItems.itemCount == 0 -> {
                item {
                    Box(
                        modifier = Modifier.fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) { Text("검색 결과가 없습니다.", color = Color.Gray) }
                }
            }
        }
    }
}

@Composable
fun RepoItem(gitUserRepo: GitUserRepo, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                NetworkImageLayout(
                    avatarUrl = gitUserRepo.owner.avatarUrl,
                    contentDescription = "Profile of ${gitUserRepo.name}",
                    modifier = Modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(4.dp))
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = gitUserRepo.fullName,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            gitUserRepo.description?.takeIf {
                it.isNotEmpty()
            }?.let { desc ->
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (gitUserRepo.topics.isNotEmpty()) {
                RepoTopicList(
                    topics = gitUserRepo.topics,
                    modifier = Modifier.padding(top = 8.dp) // 설명과 간격 띄우기
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                val language = gitUserRepo.language
                if (!language.isNullOrEmpty()) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(color = MaterialTheme.colorScheme.secondary, shape = CircleShape)
                            .testTag("languageTag")
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = language,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                }

                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.DarkGray,
                    modifier = Modifier.size(12.dp)
                )

                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = gitUserRepo.stargazersCount.toString(),
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.width(8.dp))

                val dateText = remember(gitUserRepo.pushedAt) {
                    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply {
                        timeZone = TimeZone.getTimeZone("UTC")
                    }

                    repoDateFormatter.format(inputFormat.parse(gitUserRepo.pushedAt)!!)
                }

                Text(
                    text = "Updated on $dateText",
                    style = MaterialTheme.typography.bodySmall,
                            fontSize = 12.sp,
                )
            }
        }
    }
}

@Composable
fun RepoTopicList(
    topics: List<String>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        items(topics) { topic ->
            TopicChip(topicName = topic)
        }
    }
}

@Composable
fun TopicChip(
    topicName: String
) {
    Surface(
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
    ) {
        Text(
            text = topicName,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun ErrorRetryView(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("오류: $message", color = MaterialTheme.colorScheme.error)
        Button(onClick = onRetry) { Text("재시도") }
    }
}

@Composable
fun EmptySearchGuide() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("emptySearchGuide"),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "원하는 저장소를 검색해보세요",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenEmptyPreview() {
    val emptyFlow = flowOf(PagingData.from(emptyList<GitUserRepo>()))
    val emptyItems = emptyFlow.collectAsLazyPagingItems()

    MaterialTheme {
        SearchScreen(
            searchKeyWord = "",
            searchResults = emptyItems,
            onSearchClick = {},
            onItemClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenResultPreview() {
    MaterialTheme {
        RepoItem(
            gitUserRepo = testRepos[0],
            {

            }
        )
    }
}