package com.example.mf.movielibrary.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by MF on 21-12-2017.
 */
data class Movie (
        @SerializedName("id") val id : Int,
        @SerializedName("video") val video : Boolean,
        @SerializedName("vote_average") val voteAverage : Float,
        @SerializedName("poster_path") val posterPath : String,
        @SerializedName("original_title") val originalTitle : String,
        @SerializedName("genre_ids") val genreIds : List<Int>,
        @SerializedName("backdrop_path") val backDroppath : String,
        @SerializedName("overview") val overview : String,
        @SerializedName("release_date") val releaseDate : String
        ) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.readFloat(),
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
        parcel.writeString(originalTitle)
        parcel.writeString(backDroppath)
        parcel.writeString(overview)
        parcel.writeString(releaseDate)
        parcel.writeList(genreIds)
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
