package com.example.mf.movielibrary.models.moviemodel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by MF on 01-12-2017.
 */

data class MoviesResult(
        @SerializedName("page") val page : Int,
        @SerializedName("total_results") val totalResults : Int,
        @SerializedName("total_pages") val totalPages : Int,
        @SerializedName(value = "results", alternate = ["cast"]) val moviesList : List<Movie?>)


data class Movie (
        @SerializedName("id") val id : Int,
        @SerializedName("video") val video : Boolean,
        @SerializedName("vote_average") val voteAverage : Float,
        @SerializedName("poster_path") val posterPath : String ?,
        @SerializedName("media_type") val mediaType : String ?,
        @SerializedName(value = "original_title", alternate = ["original_name"]) val originalTitle : String?,
        @SerializedName(value = "title", alternate = ["name"]) val title : String?,
        @SerializedName("genre_ids") val genreIds : List<Int>?,
        @SerializedName("backdrop_path") val backDroppath : String?,
        @SerializedName("overview") val overview : String?,
        @SerializedName(value = "release_date", alternate = ["first_air_date"]) val releaseDate : String?
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
        parcel.writeString(backDroppath)
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