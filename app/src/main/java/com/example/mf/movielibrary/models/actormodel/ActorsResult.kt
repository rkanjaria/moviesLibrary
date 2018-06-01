package com.example.mf.movielibrary.models.actormodel

import com.google.gson.annotations.SerializedName

data class ActorsResult(
        @SerializedName("page")val page: Int,
        @SerializedName("total_results")val totalResults: Int,
        @SerializedName("total_pages")val totalPages: Int,
        @SerializedName("results")val actorsList: List<Actor>?
)