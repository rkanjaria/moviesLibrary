package com.example.mf.movielibrary.models.seasonmodels

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by RK on 06-02-2018.
 */
data class SeasonResult(
        @SerializedName("id") val seasonId: Int,
        @SerializedName("episodes") val episodeList: List<Episode>?,
        @SerializedName("air_date") val airDate: String?,
        @SerializedName("poster_path") val posterPath: String?,
        @SerializedName("season_number") val seasonNumber: Int,
        @SerializedName("overview") val overview: String?,
        @SerializedName("name") val seasonName: String?
)

data class Episode(
        @SerializedName("air_date") val episodeAirDate: String?,
        @SerializedName("episode_number") val episodeNumber: Int,
        @SerializedName("name") val episodeName: String?,
        @SerializedName("overview") val episodeOverview: String?,
        @SerializedName("id") val episodeId: Int,
        @SerializedName("production_code") val episodeProductionCode: String?,
        @SerializedName("season_number") val episodeSeasonNumber: Int,
        @SerializedName("still_path") val stillPath: String?,
        @SerializedName("vote_average") val voteAverage: Double,
        @SerializedName("vote_count") val voteCount: Double
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readDouble()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(episodeAirDate)
        parcel.writeInt(episodeNumber)
        parcel.writeString(episodeName)
        parcel.writeString(episodeOverview)
        parcel.writeInt(episodeId)
        parcel.writeString(episodeProductionCode)
        parcel.writeInt(episodeSeasonNumber)
        parcel.writeString(stillPath)
        parcel.writeDouble(voteAverage)
        parcel.writeDouble(voteCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Episode> {
        override fun createFromParcel(parcel: Parcel): Episode {
            return Episode(parcel)
        }

        override fun newArray(size: Int): Array<Episode?> {
            return arrayOfNulls(size)
        }
    }
}