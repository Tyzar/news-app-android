package com.assignment.newsapp.presentations.screens.explore_news.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.assignment.newsapp.core.utils.formatter.DateFmt
import com.assignment.newsapp.entities.news.articles.Article
import java.time.Duration
import java.time.LocalDateTime

@Composable
fun ArticleItem(
    modifier: Modifier = Modifier,
    article: Article,
    onTap: () -> Unit
) {
    Row(
        modifier = modifier.clickable {
            onTap()
        },
        verticalAlignment = Alignment.Top
    ) {
        //image
        AsyncImage(
            modifier = Modifier.size(
                width = 100.dp,
                height = 50.dp
            ),
            contentScale = ContentScale.FillWidth,
            model = ImageRequest.Builder(
                LocalContext.current
            ).data(article.urlToImage)
                .diskCachePolicy(
                    CachePolicy.ENABLED
                ).memoryCachePolicy(
                    CachePolicy.DISABLED
                ).build(),
            contentDescription = null
        )

        //spacer
        Spacer(
            modifier = Modifier.width(
                12.dp
            )
        )

        //article summary
        ArticleSummary(
            modifier = Modifier.weight(
                1f
            ),
            article.title,
            article.publishedDate,
            article.source.name
        )
    }
}

@Composable
fun ArticleSummary(
    modifier: Modifier = Modifier,
    title: String,
    publishedAt: LocalDateTime?,
    source: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(
            modifier = Modifier.height(
                16.dp
            )
        )
        Text(
            text = "$source • ${publishedAt.formatAsSimplePastInfo()}",
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Light
            )
        )
    }
}

private fun LocalDateTime?.formatAsSimplePastInfo(): String {
    if (this == null) {
        return ""
    }

    val now = LocalDateTime.now()
    val diff =
        Duration.between(this, now)
    val inDays = diff.toDays()
    if (inDays >= 30) {
        return DateFmt.format(
            this,
            DateFmt.articleShort
        )
    }

    if (inDays in 2..29) {
        return "$inDays days ago"
    }

    val inHours = diff.toHours()
    if (inHours in 2..23) {
        return "$inHours hours ago"
    }

    val inMinutes = diff.toMinutes()
    return if (inMinutes in 2..59) {
        "$inMinutes minutes ago"
    } else "now"
}
