package com.example.mf.movielibrary.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.mf.movielibrary.database.daos.FavouriteMovieDao
import com.example.mf.movielibrary.database.daos.GenreDao
import com.example.mf.movielibrary.database.entities.FavouriteMovie
import com.example.mf.movielibrary.database.entities.Genre

@Database(entities = [(Genre::class), (FavouriteMovie::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun genreDao(): GenreDao
    abstract fun favouriteMovieDao(): FavouriteMovieDao
}