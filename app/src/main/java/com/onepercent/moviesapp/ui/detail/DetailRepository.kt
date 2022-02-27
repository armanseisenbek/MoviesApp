package com.onepercent.moviesapp.ui.detail

import com.onepercent.moviesapp.data.local.MovieDatabase
import com.onepercent.moviesapp.model.MovieEntity
import javax.inject.Inject

class DetailRepository
@Inject
constructor(
    private val movieDatabase: MovieDatabase
) {

    suspend fun getMovie(id: String) : MovieEntity {
         return movieDatabase.movieDao().getMovie(id)
    }

}