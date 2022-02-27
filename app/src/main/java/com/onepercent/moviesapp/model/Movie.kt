package com.onepercent.moviesapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,

    @SerialName("vote_average")
    val voteAverage: Double,

    @SerialName("release_date")
    val releaseDate: String,

    @SerialName("poster_path")
    val posterPath: String,
)