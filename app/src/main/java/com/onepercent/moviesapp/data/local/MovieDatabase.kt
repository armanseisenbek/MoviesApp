package com.onepercent.moviesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.onepercent.moviesapp.model.MovieEntity
import com.onepercent.moviesapp.model.MovieRemoteKeys

@Database(
    entities = [
        MovieEntity::class,
        MovieRemoteKeys::class
    ],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun movieRemoteKeysDao(): MovieRemoteKeysDao

    companion object {
        const val DATABASE_NAME: String = "movie_db"
    }
}