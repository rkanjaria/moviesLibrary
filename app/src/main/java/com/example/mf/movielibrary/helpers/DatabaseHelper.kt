package com.example.mf.movielibrary.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.mf.movielibrary.models.genremodel.Genre
import com.example.mf.movielibrary.models.moviemodel.Movie
import files.*
import org.jetbrains.anko.db.*

/**
 * Created by MF on 22-01-2018.
 */
class DatabaseHelper(context: Context) : ManagedSQLiteOpenHelper(context, DATABASE_NAME) {

    companion object {
        private var dbInstance: DatabaseHelper? = null

        @Synchronized
        fun getInstance(context: Context): DatabaseHelper {
            if (dbInstance == null) {
                dbInstance = DatabaseHelper(context.applicationContext)
            }
            return dbInstance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(GENRE_TABLE, true,
                ID to INTEGER + PRIMARY_KEY,
                GENRE_ID to TEXT,
                GENRE_NAME to TEXT, SHOW_TYPE to TEXT)
        Log.d("TABLE_CREATED", "TABLE CREATED ${GENRE_TABLE}")

        db.createTable(FAVOURITE_TABLE, true,
                ID to INTEGER + PRIMARY_KEY,
                MOVIE_ID to TEXT,
                VOTE_AVERAGE to TEXT,
                POSTER_PATH to TEXT,
                MEDIA_TYPE to TEXT,
                ORIGINAL_TITLE to TEXT,
                TITLE to TEXT,
                GENRE_ID to TEXT,
                BACKDROP_PATH to TEXT,
                OVERVIEW to TEXT,
                RELEASE_DATE to TEXT)

        Log.d("TABLE_CREATED", "TABLE CREATED ${FAVOURITE_TABLE}")

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(GENRE_TABLE, true)
    }

    fun insertGenre(genreId: String, genreName: String, movieOrSeries: String) {
        dbInstance?.use {
            insert(GENRE_TABLE, GENRE_ID to genreId,
                    GENRE_NAME to genreName, SHOW_TYPE to movieOrSeries)
        }
        Log.d("GENRE_INSERTED", "id ${genreId}, name ${genreName}, type ${movieOrSeries}")
    }

    fun clearTable(tableName: String) {
        dbInstance?.use { delete(tableName, null, null) }
        Log.d("TABLE_DELETED", tableName)
    }

    fun getGenreBasedOnGenreId(genreId: Int): String {
        val genre = dbInstance?.use {
            select(GENRE_TABLE, GENRE_NAME)
                    .whereSimple(GENRE_ID + "= ?", genreId.toString())
                    .exec { parseList(StringParser) }
        }

        if (genre!!.isNotEmpty()) return genre.get(0) else return ""
    }


    fun getAllGenres(movieOrSeries: String): List<Genre> {
        val genreList = ArrayList<Genre>()
        dbInstance?.use {
            select(GENRE_TABLE, GENRE_ID, GENRE_NAME)
                    .whereSimple(SHOW_TYPE + "= ?", movieOrSeries).exec {
                parseList(object : MapRowParser<List<Genre>> {
                    override fun parseRow(columns: Map<String, Any?>): List<Genre> {

                        val genre = Genre(columns.getValue(GENRE_ID).toString().toInt(), columns.getValue(GENRE_NAME).toString())
                        genreList.add(genre)
                        Log.e("Your result", columns.toString())
                        return genreList
                    }
                })
            }
        }
        return genreList!!
    }

    fun isMovieTableEmpty(): Boolean {
        val result = dbInstance?.use {
            select(GENRE_TABLE, GENRE_NAME)
                    .exec { parseList(StringParser) }
        }
        return (result == null || result.isEmpty())
    }

    fun insertFavourites(movieModel: Movie): Long {

        val isInserted = dbInstance?.use {
            insert(FAVOURITE_TABLE, MOVIE_ID to movieModel.id,
                    VOTE_AVERAGE to movieModel.voteAverage.toString(),
                    POSTER_PATH to movieModel.posterPath,
                    MEDIA_TYPE to movieModel.mediaType,
                    ORIGINAL_TITLE to movieModel.originalTitle,
                    TITLE to movieModel.title,
                    BACKDROP_PATH to movieModel.backDroppath,
                    OVERVIEW to movieModel.overview,
                    RELEASE_DATE to movieModel.releaseDate)
        }
        return isInserted!!
    }
}