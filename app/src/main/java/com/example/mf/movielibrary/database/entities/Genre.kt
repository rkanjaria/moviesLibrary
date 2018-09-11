package com.example.mf.movielibrary.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import files.*

@Entity(tableName = GENRE_TABLE)
data class Genre(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = ID) val gId: Int,

        @ColumnInfo(name = GENRE_ID)
        @SerializedName("id") val genreId: Int,

        @SerializedName("name")
        @ColumnInfo(name = GENRE_NAME) val genreName: String?,

        @ColumnInfo(name = SHOW_TYPE) var showType: String?
)