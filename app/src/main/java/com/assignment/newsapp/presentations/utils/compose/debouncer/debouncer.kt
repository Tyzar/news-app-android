package com.assignment.newsapp.presentations.utils.compose.debouncer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.assignment.newsapp.core.utils.logger.AppLogger
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun <T> debounce(
    value: T,
    timeoutInMillis: () -> Long,
    callback: (value: T) -> Unit,
) {
    val scope = rememberCoroutineScope()
    DisposableEffect(value) {
        val job = scope.launch {
            delay(timeoutInMillis())
            AppLogger.log(msg = "Apply callback $value")
            callback(value)
        }

        onDispose {
            job.cancel()
        }
    }
}