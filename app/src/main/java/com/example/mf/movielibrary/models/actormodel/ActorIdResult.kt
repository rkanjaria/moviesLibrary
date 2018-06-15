package com.example.mf.movielibrary.models.actormodel

import com.google.gson.annotations.SerializedName

data class ActorIdResult(
        @SerializedName("id") val id: Int,
        @SerializedName("twitter_id") val twitterId: String?,
        @SerializedName("facebook_id") val facebookId: String?,
        @SerializedName("tvrage_id") val tvrageId: String?,
        @SerializedName("instagram_id") val instagramId: String?,
        @SerializedName("freebase_mid") val freebaseMid: String?,
        @SerializedName("imdb_id") val imdbId: String?,
        @SerializedName("freebase_id") val freebaseId: String?
)
