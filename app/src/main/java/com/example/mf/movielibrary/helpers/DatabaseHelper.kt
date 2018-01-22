package com.example.mf.movielibrary.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import files.DATABASE_NAME
import files.GENRE_ID
import files.ID
import files.MOVIE_TABLE
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
                GENRE_ID to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}