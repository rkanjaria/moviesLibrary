package com.example.mf.movielibrary.models.videomodels

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by RK on 15-03-2018.
 */

data class VideoResult(
        @SerializedName("id") val id: Int,
        @SerializedName("results") val results: List<VideoTrailers>?
)

data class VideoTrailers(
        @SerializedName("id") val id: String?,
        @SerializedName("iso_639_1") val iso6391: String?,
        @SerializedName("iso_3166_1") val iso31661: String?,
        @SerializedName("key") val key: String?,
        @SerializedName("name") val name: String?,
        @SerializedName("site") val site: String?,
        @SerializedName("size") val size: String?,
        @SerializedName("type") val type: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(iso6391)
        parcel.writeString(iso31661)
        parcel.writeString(key)
        parcel.writeString(name)
        parcel.writeString(site)
        parcel.writeString(size)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VideoTrailers> {
        override fun createFromParcel(parcel: Parcel): VideoTrailers {
            return VideoTrailers(parcel)
        }

        override fun newArray(size: Int): Array<VideoTrailers?> {
            return arrayOfNulls(size)
        }
    }
}