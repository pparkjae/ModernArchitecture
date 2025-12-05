package com.park.core.design.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter

@Composable
fun NetworkImageLayout(
    avatarUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    val painter = rememberAsyncImagePainter(model = avatarUrl)
    val state = painter.state

    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        if (state is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp),
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
        Image(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(18.dp)),
            contentScale = ContentScale.Crop,
            painter = when (state) {
                is AsyncImagePainter.State.Error -> rememberVectorPainter(Icons.Rounded.Person)
                else -> painter
            },
            contentDescription = contentDescription,
        )
    }
}