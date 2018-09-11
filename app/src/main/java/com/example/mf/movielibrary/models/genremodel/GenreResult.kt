package com.example.mf.movielibrary.models.genremodel

import com.example.mf.movielibrary.database.entities.Genre
import com.google.gson.annotations.SerializedName

data class GenreResult(
        @SerializedName("genres") val genresList : List<Genre>
)