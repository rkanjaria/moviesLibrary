package com.example.mf.movielibrary.models

import com.google.gson.annotations.SerializedName

/**
 * Created by MF on 01-12-2017.
 */

data class MoviesResult(
        @SerializedName("page") val page : Int,
        @SerializedName("total_results") val totalResults : Int,
        @SerializedName("total_pages") val totalPages : Int,
        @SerializedName("results") val moviesList : List<Movie>)


