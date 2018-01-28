package com.example.mf.movielibrary.models.genremodel

import com.google.gson.annotations.SerializedName

data class GenreResult(
        @SerializedName("genres") val genresList : List<Genre>
)

data class Genre (
        @SerializedName("id") val genreId : Int,
        @SerializedName("name") val genreName : String?
)