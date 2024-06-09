package com.assignment.newsapp.presentations.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.assignment.newsapp.presentations.screens.explore_news.ExploreNewsScreen

@Composable
fun App() {
    Surface(modifier = Modifier.fillMaxSize()) {
        val navController =
            rememberNavController()

        NavHost(
            navController = navController,
            startDestination = "explore",
        ) {
            composable("explore") {
                ExploreNewsScreen(newsVM = hiltViewModel())
            }
        }
    }
}