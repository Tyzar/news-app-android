package com.assignment.newsapp.presentations.screens.explore_news.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.assignment.newsapp.presentations.utils.compose.debouncer.debounce

@Composable
fun SearchNewsTopBar(
    keyword: String,
    onBack: () -> Unit,
    onKeywordChanged: (keyword: String) -> Unit
) {
    var searchKeyword by rememberSaveable {
        mutableStateOf(keyword)
    }

    debounce(
        value = searchKeyword,
        timeoutInMillis = { 3000L }
    ) {
        onKeywordChanged(searchKeyword)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    onBack()
                },
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = null
        )

        Spacer(
            modifier = Modifier.width(
                16.dp
            )
        )

        OutlinedTextField(
            modifier = Modifier
                .weight(1f),
            value = searchKeyword,
            onValueChange = { text ->
                searchKeyword = text
            },
            shape = MaterialTheme.shapes.medium,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Light
            ),
            placeholder = {
                Text(
                    text = "Search...",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Light
                    )
                )
            },
            leadingIcon = {
                Icon(
                    modifier = Modifier
                        .size(
                            24.dp
                        ),
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (keyword.isNotEmpty()) {
                    Icon(
                        modifier = Modifier
                            .size(
                                24.dp
                            )
                            .clickable {
                                searchKeyword =
                                    ""
                            },
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = "clear"
                    )
                }
            }
        )
    }
}