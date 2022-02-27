package com.onepercent.moviesapp.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedViewModel
@Inject
constructor(
    private val repository: FeedRepository
): ViewModel() {

    val movies = repository.getPopularMovies()

}