package com.example.mf.movielibrary.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.mf.movielibrary.models.genremodel.Genre
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
        db.createTable(MOVIE_TABLE, true,
                ID to INTEGER + PRIMARY_KEY,
                GENRE_ID to TEXT,
                GENRE_NAME to TEXT, SHOW_TYPE to TEXT)
        Log.d("TABLE_CREATED", "TABLE CREATED ${MOVIE_TABLE}")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(MOVIE_TABLE, true)
    }

    fun insertGenre(genreId: String, genreName: String, movieOrSeries: String) {
        dbInstance?.use {
            insert(MOVIE_TABLE, GENRE_ID to genreId,
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
            select(MOVIE_TABLE, GENRE_NAME)
                    .whereSimple(GENRE_ID + "= ?", genreId.toString())
                    .exec { parseList(StringParser) }
        }

        if (genre!!.isNotEmpty()) return genre.get(0) else return ""
    }


    fun getAllGenres(movieOrSeries: String): List<Genre> {
        val genreList = ArrayList<Genre>()
        dbInstance?.use {
            select(MOVIE_TABLE, GENRE_ID, GENRE_NAME)
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
            select(MOVIE_TABLE, GENRE_NAME)
                    .exec { parseList(StringParser) }
        }

        return (result == null || result.isEmpty())
    }
}