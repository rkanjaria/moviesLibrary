package com.example.mf.movielibrary.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import files.*

@Entity(tableName = FAVOURITE_TABLE)
data class FavouriteMovie(
        @PrimaryKey
        @ColumnInfo(name = MOVIE_ID) val id: Int,
        @ColumnInfo(name = VOTE_AVERAGE) val voteAverage: Double,
        @ColumnInfo(name = POSTER_PATH) val posterPath: String?,
        @ColumnInfo(name = MEDIA_TYPE) val mediaType: String?,
        @ColumnInfo(name = ORIGINAL_TITLE) val originalTitle: String?,
        @ColumnInfo(name = TITLE) val title: String?,
        @ColumnInfo(name = GENRES) val genreIds: String?,
        @ColumnInfo(name = BACKDROP_PATH) val backDropPath: String?,
        @ColumnInfo(name = OVERVIEW) val overview: String?,
        @ColumnInfo(name = RELEASE_DATE) val releaseDate: String?,
        @ColumnInfo(name = SHOW_TYPE) val showType: String?
)