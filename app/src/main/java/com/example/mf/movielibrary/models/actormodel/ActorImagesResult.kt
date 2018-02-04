package com.example.mf.movielibrary.models.actormodel

import com.google.gson.annotations.SerializedName

data class ActorImagesResult(
        @SerializedName("profiles") val actorImagesList: List<ActorImage>?
)
data class ActorImage(
        @SerializedName("width") val width: Int,
        @SerializedName("height") val height: Int,
        @SerializedName("vote_count") val voteCount: Int,
        @SerializedName("vote_average") val voteAverage: Double,
        @SerializedName("file_path") val filePath: String?,
        @SerializedName("aspect_ratio") val aspectRatio: Double
)