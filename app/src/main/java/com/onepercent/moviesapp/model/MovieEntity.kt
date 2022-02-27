package com.onepercent.moviesapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class MovieEntity (
    @PrimaryKey
    val id: String,
    val title: String,
    val voteAverage: Double,
    val overview: String,
    val releaseDate: String,
    val posterPath: String,
)