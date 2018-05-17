package com.example.mf.movielibrary.models.reviewmodels

import com.google.gson.annotations.SerializedName

data class ReviewResult(
        @SerializedName("id") val id: Int,
        @SerializedName("page") val page: Int,
        @SerializedName("total_pages") val totalPages: Int,
        @SerializedName("total_results") val totalResults: Int,
        @SerializedName("results") val reviewList: List<UserReview>?
)

data class UserReview(
        @SerializedName("author") val author: String?,
        @SerializedName("content") val content: String?,
        @SerializedName("id") val id: String?,
        @SerializedName("url") val url: String?
)
