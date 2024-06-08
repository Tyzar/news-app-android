package com.assignment.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.assignment.newsapp.presentations.App
import com.assignment.newsapp.presentations.themes.NewsAppTheme

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