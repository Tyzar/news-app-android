package com.assignment.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.assignment.newsapp.presentations.screens.App
import com.assignment.newsapp.presentations.themes.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsActivity :
    ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(
            savedInstanceState
        )

        setContent {
            NewsAppTheme {
                App()
            }
        }
    }
}