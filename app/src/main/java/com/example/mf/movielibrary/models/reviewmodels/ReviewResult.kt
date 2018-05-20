package com.example.mf.movielibrary.models.reviewmodels

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
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
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(author)
        parcel.writeString(content)
        parcel.writeString(id)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserReview> {
        override fun createFromParcel(parcel: Parcel): UserReview {
            return UserReview(parcel)
        }

        override fun newArray(size: Int): Array<UserReview?> {
            return arrayOfNulls(size)
        }
    }

}
