package com.example.mf.movielibrary.database.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.mf.movielibrary.database.entities.FavouriteMovie
import com.example.mf.movielibrary.files.FAVOURITE_TABLE
import com.example.mf.movielibrary.files.MOVIE_ID
import com.example.mf.movielibrary.files.SHOW_TYPE


@Dao
interface FavouriteMovieDao {

    @Query("SELECT * FROM ${FAVOURITE_TABLE} WHERE ${MOVIE_ID} = :movieOrSeriesId")
    fun doesAlreadyExists(movieOrSeriesId: Int): Boolean

    @Query("DELETE FROM ${FAVOURITE_TABLE} WHERE ${MOVIE_ID} = :movieId")
    fun removeMovie(movieId: Int)

    @Insert
    fun insertMovie(favouriteMovie: FavouriteMovie)

    @Query("SELECT * FROM ${FAVOURITE_TABLE} WHERE ${SHOW_TYPE}= :movieOrSeries")
    fun getAllMoviesOrTvShows(movieOrSeries: String): List<FavouriteMovie>

    @Query("SELECT count(*) FROM ${FAVOURITE_TABLE}")
    fun isFavouriteTableEmpty(): Int
}