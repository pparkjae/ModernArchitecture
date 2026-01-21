package com.park.feature.home

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.park.core.design.component.LoadingLayout
import com.park.core.design.component.NetworkImageLayout
import com.park.core.model.GitUser
import com.park.core.model.GitUserInfo
import com.park.core.model.GitUserRepo
import com.park.core.test.data.testGitUserInfo

@Composable
internal fun HomeRoute(
    submitKeyWord: String,
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    LaunchedEffect(submitKeyWord) {
        viewModel.searchGitUser(submitKeyWord)
    }

    val user by viewModel.user.collectAsStateWithLifecycle()

    HomeScreen(
        homeUiState = user,
        modifier = modifier
    )
}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    homeUiState: HomeUiState = HomeUiState.Loading
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (homeUiState) {
            HomeUiState.Loading -> LoadingLayout(Modifier.align(Alignment.Center))
            is HomeUiState.Success -> HomeLayout(
                gitUserInfo = homeUiState.gitUserInfo,
                onRepoClick = { /* Handle click */ },
                modifier = Modifier.fillMaxSize()
            )

            HomeUiState.LoadFailed -> Text(
                "Error Loading Data",
                Modifier
                    .align(Alignment.Center)
                    .testTag("error")
            )

            HomeUiState.EmptyResult -> Text("Search for a user", Modifier.align(Alignment.Center))
        }
    }
}

@Composable
private fun HomeLayout(
    gitUserInfo: GitUserInfo,
    onRepoClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        GitProfile(
            gitUser = gitUserInfo.gitUser,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Repositories (${gitUserInfo.userRepo.size})",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            repoListSection(
                repos = gitUserInfo.userRepo,
                onClick = onRepoClick
            )
        }
    }
}

@Composable
private fun GitProfile(
    gitUser: GitUser,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NetworkImageLayout(
            avatarUrl = gitUser.avatarUrl,
            contentDescription = "Profile of ${gitUser.name}",
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(18.dp))
        )

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = gitUser.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 24.sp
                ),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = gitUser.login,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 18.sp
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = gitUser.htmlUrl,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 18.sp
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

private fun LazyListScope.repoListSection(
    repos: List<GitUserRepo>,
    onClick: (String) -> Unit
) {
    items(repos, key = { it.id }) { repo ->
        RepoItem(repo, onClick)
    }
}

@Composable
private fun RepoItem(
    gitUserRepo: GitUserRepo,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onClick(gitUserRepo.htmlUrl) },
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (gitUserRepo.private) Icons.Rounded.Lock else Icons.Rounded.Face,
                contentDescription = if (gitUserRepo.private) "Private Repository" else "Public Repository",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = gitUserRepo.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = gitUserRepo.htmlUrl,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeLayoutPreview() {
    HomeLayout(
        gitUserInfo = testGitUserInfo,
        onRepoClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun LoadingPreview() {
    HomeScreen(
        homeUiState = HomeUiState.Loading,
        modifier = Modifier.fillMaxSize()
    )
}
