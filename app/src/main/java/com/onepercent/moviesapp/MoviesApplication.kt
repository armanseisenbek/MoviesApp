package com.onepercent.moviesapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoviesApplication : Application() {

    companion object {
        // put your themoviedb api key here
        const val API_KEY = ""
    }
}