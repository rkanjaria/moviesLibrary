package com.example.mf.movielibrary.models.imagemodels

import com.google.gson.annotations.SerializedName

/**
 * Created by MF on 17-02-2018.
 */
data class ImageModel(
        @SerializedName("width") val width: Int,
        @SerializedName("height") val height: Int,
        @SerializedName("vote_count") val voteCount: Int,
        @SerializedName("vote_average") val voteAverage: Double,
        @SerializedName("file_path") val filePath: String?,
        @SerializedName("aspect_ratio") val aspectRatio: Double
)