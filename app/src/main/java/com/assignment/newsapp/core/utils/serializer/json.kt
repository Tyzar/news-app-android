package com.assignment.newsapp.core.utils.serializer

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder

inline fun <reified T> jsonEncode(value: T): String {
    val json =
        Json.encodeToString(value)
    return URLEncoder.encode(
        json,
        Charsets.UTF_8.toString()
    )
}

inline fun <reified T> jsonDecode(value: String): T {
    val decoded = URLDecoder.decode(
        value,
        Charsets.UTF_8.toString()
    )
    return Json.decodeFromString(decoded)
}