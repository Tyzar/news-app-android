package com.assignment.newsapp.presentations.screens.explore_news.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.assignment.newsapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreTopBar(onSearchTap: () -> Unit) {
    TopAppBar(title = {
        Text(
            text = stringResource(
                id = R.string.app_name
            ),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }, actions = {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    onSearchTap()
                },
            imageVector = Icons.Outlined.Search,
            contentDescription = "search"
        )
        Spacer(
            modifier = Modifier.width(
                16.dp
            )
        )
    })
}