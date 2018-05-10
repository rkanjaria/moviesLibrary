package com.example.mf.movielibrary.models.movieseriesdetailsmodel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by RK on 05-02-2018.
 */
data class MovieSeriesDetailsResult(
        @SerializedName("seasons") val seasonsList: List<Season>?
)

data class Season(
        @SerializedName("air_date") val airDate: String?,
        @SerializedName("episode_count") val episodeCount: Int,
        @SerializedName("id") val seasonId: Int,
        @SerializedName("poster_path") val posterPath: String?,
        @SerializedName("season_number") val seasonNumber: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(airDate)
        parcel.writeInt(episodeCount)
        parcel.writeInt(seasonId)
        parcel.writeString(posterPath)
        parcel.writeInt(seasonNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Season> {
        override fun createFromParcel(parcel: Parcel): Season {
            return Season(parcel)
        }

        override fun newArray(size: Int): Array<Season?> {
            return arrayOfNulls(size)
        }
    }
}
