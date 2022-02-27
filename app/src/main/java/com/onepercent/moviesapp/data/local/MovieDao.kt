package com.onepercent.moviesapp.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.onepercent.moviesapp.model.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_table")
    fun getAll(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movie_table LIMIT 1")
    suspend fun getEntity() : MovieEntity?

    @Query("SELECT * FROM movie_table WHERE id=:id")
    suspend fun getMovie(id: String): MovieEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(popularMovieEntities: List<MovieEntity>)

    @Query("DELETE FROM movie_table")
    suspend fun deleteAll()
}