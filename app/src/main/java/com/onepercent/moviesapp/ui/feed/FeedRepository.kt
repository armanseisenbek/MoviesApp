package com.onepercent.moviesapp.ui.feed

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData

import com.onepercent.moviesapp.data.local.MovieDatabase
import com.onepercent.moviesapp.data.paging.MovieRemoteMediator
import com.onepercent.moviesapp.data.remote.MovieApi
import com.onepercent.moviesapp.model.MovieEntity

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FeedRepository
@Inject
constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getPopularMovies(): Flow<PagingData<MovieEntity>> {

        val pagingSourceFactory = { movieDatabase.movieDao().getAll() }

        return Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100,
                enablePlaceholders = false,
                initialLoadSize = 20,
                prefetchDistance = 20
            ),
            remoteMediator = MovieRemoteMediator(
                movieApi = movieApi,
                movieDatabase = movieDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}