package com.assignment.newsapp.presentations.screens.article

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.assignment.newsapp.core.utils.formatter.DateFmt
import com.assignment.newsapp.entities.news.articles.Article
import com.assignment.newsapp.presentations.screens.article.components.ArticleTopBar
import com.assignment.newsapp.presentations.viewmodels.explore_news.ExploreNewsVM

@Composable
fun ArticleScreen(
    newsVM: ExploreNewsVM,
    title: String,
    onBack: () -> Unit
) {
    val newsState by newsVM.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            ArticleTopBar(
                onBack = onBack
            )
        }
    ) { contentPadding ->
        val article =
            newsState.articles.firstOrNull { temp -> temp.title == title }
        when (article) {
            null -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        contentPadding
                    )
            ) {
                Text(
                    text = "Article not found",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            else -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        PaddingValues(
                            end = 16.dp,
                            start = 16.dp,
                            top = contentPadding.calculateTopPadding(),
                            bottom = 8.dp
                        )
                    )
                    .verticalScroll(
                        rememberScrollState()
                    )
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    text = article.title,
                    style = MaterialTheme.typography.headlineMedium
                )

                val sourceAndDateInfo =
                    if (article.source.name.isNotEmpty() && article.publishedDate != null) {
                        "${article.source.name} • ${
                            DateFmt.format(
                                article.publishedDate,
                                DateFmt.articleDetail
                            )
                        }"
                    } else if (article.source.name.isNotEmpty()) {
                        article.source.name
                    } else if (article.publishedDate != null) {
                        DateFmt.format(
                            article.publishedDate,
                            DateFmt.articleDetail
                        )
                    } else {
                        ""
                    }
                if (sourceAndDateInfo.isNotEmpty())
                    Text(
                        modifier = Modifier.padding(
                            bottom = 8.dp
                        ),
                        text = sourceAndDateInfo,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Light
                        )
                    )

                if (article.author.isNotEmpty()) {
                    Text(
                        modifier = Modifier.padding(
                            bottom = 16.dp
                        ),
                        text = article.author,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                if (!article.urlToImage.isNullOrEmpty()) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(
                                (100 / 60).toFloat()
                            )
                            .clip(
                                RoundedCornerShape(
                                    12.dp
                                )
                            ),
                        model = ImageRequest.Builder(
                            LocalContext.current
                        )
                            .data(
                                article.urlToImage
                            )
                            .memoryCachePolicy(
                                CachePolicy.DISABLED
                            )
                            .diskCachePolicy(
                                CachePolicy.ENABLED
                            )
                            .build(),
                        contentScale = ContentScale.FillWidth,
                        contentDescription = null
                    )
                }

                Spacer(
                    modifier = Modifier.height(
                        16.dp
                    )
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = article.content
                )
            }
        }
    }
}