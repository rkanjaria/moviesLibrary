package com.example.mf.movielibrary.database.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.mf.movielibrary.database.entities.Genre
import com.example.mf.movielibrary.files.GENRE_ID
import com.example.mf.movielibrary.files.GENRE_NAME
import com.example.mf.movielibrary.files.GENRE_TABLE
import com.example.mf.movielibrary.files.SHOW_TYPE

@Dao
interface GenreDao {

    @Insert
    fun insertAllGenres(genreList: List<Genre>)

    @Query("DELETE FROM ${GENRE_TABLE}")
    fun clearTable()

    @Query("SELECT ${GENRE_NAME} FROM ${GENRE_TABLE} WHERE ${GENRE_ID} = :genreId")
    fun getGenreBasedOnGenreId(genreId: Int): String

    @Query("SELECT * FROM ${GENRE_TABLE} WHERE ${SHOW_TYPE} = :showType")
    fun getAllGenresOfType(showType: String): List<Genre>

    @Query("SELECT count(*) FROM ${GENRE_TABLE}")
    fun isGenreTableEmpty(): Int

}