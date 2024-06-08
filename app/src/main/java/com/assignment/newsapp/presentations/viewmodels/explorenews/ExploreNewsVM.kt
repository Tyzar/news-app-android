package com.assignment.newsapp.presentations.viewmodels.explorenews

import androidx.lifecycle.ViewModel
import com.assignment.newsapp.repositories.newsrepository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExploreNewsVM @Inject constructor(private val newsRepository: NewsRepository): ViewModel() {

}