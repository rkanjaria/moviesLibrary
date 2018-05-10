package com.example.mf.movielibrary.models.castmodel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by RK on 22-01-2018.
 */
data class CastResult(
        @SerializedName("id") val id: Int,
        @SerializedName("cast") val castList: List<Cast>)

data class Cast(
        @SerializedName("cast_id") val castId: Int,
        @SerializedName("character") val character: String?,
        @SerializedName("credit_id") val creditId: String?,
        @SerializedName("gender") val gender: Int,
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String?,
        @SerializedName("order") val order: Int,
        @SerializedName("profile_path") val profilePath: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(castId)
        parcel.writeString(character)
        parcel.writeString(creditId)
        parcel.writeInt(gender)
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(order)
        parcel.writeString(profilePath)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cast> {
        override fun createFromParcel(parcel: Parcel): Cast {
            return Cast(parcel)
        }

        override fun newArray(size: Int): Array<Cast?> {
            return arrayOfNulls(size)
        }
    }

}