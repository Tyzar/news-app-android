package com.assignment.newsapp.presentations.routing

import android.os.Bundle
import androidx.navigation.NavType
import com.assignment.newsapp.core.utils.serializer.jsonDecode
import com.assignment.newsapp.core.utils.serializer.jsonEncode
import com.assignment.newsapp.entities.news.articles.Article

class ArticleArgType :
    NavType<Article>(isNullableAllowed = true) {

    override fun put(
        bundle: Bundle,
        key: String,
        value: Article
    ) {
        val json = jsonEncode(value)
        bundle.putString(
            key,
            json
        )
    }

    override fun get(
        bundle: Bundle,
        key: String
    ): Article? {
        val json = bundle.getString(key)
        if (json.isNullOrEmpty()) {
            return null
        }

        return parseValue(json)
    }

    override fun parseValue(value: String): Article {
        return jsonDecode(value)
    }

}