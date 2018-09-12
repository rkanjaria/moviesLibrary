package com.example.mf.movielibrary.models.moviemodel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by RK on 01-12-2017.
 */

data class MoviesResult(
        @SerializedName("page") val page : Int,
        @SerializedName("total_results") val totalResults : Int,
        @SerializedName("total_pages") val totalPages : Int,
        @SerializedName(value = "results", alternate = ["cast"]) val moviesList : List<Movie?>)


data class Movie (
        @SerializedName("id") var id : Int,
        @SerializedName("video") var video : Boolean = false,
        @SerializedName("vote_average") var voteAverage : Float,
        @SerializedName("poster_path") var posterPath : String ?,
        @SerializedName("media_type") var mediaType : String ?,
        @SerializedName(value = "original_title", alternate = ["original_name"]) var originalTitle : String?,
        @SerializedName(value = "title", alternate = ["name"]) var title : String?,
        @SerializedName("genre_ids") var genreIds : List<Int>?,
        @SerializedName("backdrop_path") var backDropPath : String?,
        @SerializedName("overview") var overview : String?,
        @SerializedName(value = "release_date", alternate = ["first_air_date"]) var releaseDate : String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.readFloat(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            arrayListOf<Int>().apply {
                parcel.readList(this, Int::class.java.classLoader)
            },
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeByte(if (video) 1 else 0)
        parcel.writeFloat(voteAverage)
        parcel.writeString(posterPath)
        parcel.writeString(mediaType)
        parcel.writeString(originalTitle)
        parcel.writeString(title)
        parcel.writeList(genreIds)
        parcel.writeString(backDropPath)
        parcel.writeString(overview)
        parcel.writeString(releaseDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}