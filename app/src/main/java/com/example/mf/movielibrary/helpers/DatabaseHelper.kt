package com.example.mf.movielibrary.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.mf.movielibrary.models.genremodel.Genre
import com.example.mf.movielibrary.models.moviemodel.Movie
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import files.*
import org.jetbrains.anko.db.*
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONStringer

/**
 * Created by RK on 22-01-2018.
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
                GENRES to TEXT,
                BACKDROP_PATH to TEXT,
                OVERVIEW to TEXT,
                RELEASE_DATE to TEXT,
                SHOW_TYPE to TEXT)

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

    fun insertMovie(movieModel: Movie, movieOrSeries: String): Long {

        val genresString = Gson().toJson(movieModel.genreIds)
        val isInserted = dbInstance?.use {
            insert(FAVOURITE_TABLE, MOVIE_ID to movieModel.id,
                    VOTE_AVERAGE to movieModel.voteAverage.toString(),
                    POSTER_PATH to movieModel.posterPath,
                    MEDIA_TYPE to movieModel.mediaType,
                    ORIGINAL_TITLE to movieModel.originalTitle,
                    TITLE to movieModel.title,
                    GENRES to genresString,
                    BACKDROP_PATH to movieModel.backDroppath,
                    OVERVIEW to movieModel.overview,
                    RELEASE_DATE to movieModel.releaseDate,
                    SHOW_TYPE to movieOrSeries)
        }
        return isInserted!!
    }

    fun removeMovie(movieId: Int): Boolean {
        var isDeleted = false
        dbInstance?.use {
            val result = delete(FAVOURITE_TABLE, "${MOVIE_ID} = {m_id}", "m_id" to movieId) > 0
            if (result) isDeleted = true else isDeleted = false
        }
        return isDeleted
    }


    fun doesAlreadyExists(movieId: Int): Boolean {
        val movies = dbInstance?.use {
            select(FAVOURITE_TABLE, ORIGINAL_TITLE)
                    .whereSimple(MOVIE_ID + "= ?", movieId.toString())
                    .exec { parseList(StringParser) }
        }

        return (movies != null && movies.size > 0)
    }

    fun getAllMoviesOrTvShows(movieOrSeries: String): List<Movie> {

        val moviesOrShowsList = ArrayList<Movie>()
        dbInstance?.use {
            select(FAVOURITE_TABLE, MOVIE_ID, VOTE_AVERAGE, POSTER_PATH, MEDIA_TYPE,
                    ORIGINAL_TITLE, TITLE, GENRES, BACKDROP_PATH, OVERVIEW, RELEASE_DATE)
                    .whereSimple(SHOW_TYPE + "= ?", movieOrSeries).exec {
                parseList(object : MapRowParser<List<Movie>> {
                    override fun parseRow(columns: Map<String, Any?>): List<Movie> {

                        val type = object : TypeToken<List<Int>>() {}.type
                        val genreIds = Gson().fromJson<List<Int>>(columns.getValue(GENRES).toString(), type)
                        val movie = Movie(
                                id = columns.getValue(MOVIE_ID).toString().toInt(),
                                voteAverage = columns.getValue(VOTE_AVERAGE).toString().toFloat(),
                                posterPath = columns.getValue(POSTER_PATH).toString(),
                                mediaType = columns.getValue(MEDIA_TYPE).toString(),
                                originalTitle = columns.getValue(ORIGINAL_TITLE).toString(),
                                title = columns.getValue(TITLE).toString(),
                                genreIds = genreIds,
                                backDroppath = columns.getValue(BACKDROP_PATH).toString(),
                                overview = columns.getValue(OVERVIEW).toString(),
                                releaseDate = columns.getValue(RELEASE_DATE).toString())
                        moviesOrShowsList.add(movie)
                        return moviesOrShowsList
                    }
                })
            }
        }
        return moviesOrShowsList!!
    }
}