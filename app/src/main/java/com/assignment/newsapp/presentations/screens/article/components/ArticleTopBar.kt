package com.assignment.newsapp.presentations.screens.article.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleTopBar(onBack: () -> Unit) {
    TopAppBar(
        modifier = Modifier.background(
            MaterialTheme.colorScheme.surface
        ), navigationIcon = {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onBack()
                    },
                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                contentDescription = "back"
            )
        }, title = {})
}
