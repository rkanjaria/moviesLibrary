package com.example.mf.movielibrary.models.collectionmodels

import com.example.mf.movielibrary.models.moviemodel.Movie
import com.google.gson.annotations.SerializedName

/**
 * Created by RK on 10-05-2018.
 */
data class CollectionsResult(
        @SerializedName("created_by") val createdBy: String?,
        @SerializedName("description") val description: String?,
        @SerializedName("favorite_count") val favoriteCount: Int,
        @SerializedName("id") val id: String?,
        @SerializedName("item_count") val itemCount: Int,
        @SerializedName("iso_639_1") val iso: String?,
        @SerializedName("name") val name: String?,
        @SerializedName("poster_path") val posterPath: String?,
        @SerializedName("items") val moviesList: List<Movie>?
)
