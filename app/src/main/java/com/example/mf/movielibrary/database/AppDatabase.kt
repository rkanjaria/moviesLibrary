package com.example.mf.movielibrary.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.mf.movielibrary.database.daos.GenreDao
import com.example.mf.movielibrary.database.entities.Genre

@Database(entities = [(Genre::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun genreDao(): GenreDao
}