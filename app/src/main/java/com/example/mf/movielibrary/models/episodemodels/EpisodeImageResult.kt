package com.example.mf.movielibrary.models.episodemodels

import com.example.mf.movielibrary.models.imagemodels.ImageModel
import com.google.gson.annotations.SerializedName

/**
 * Created by MF on 17-02-2018.
 */
data class EpisodeImageResult(
        @SerializedName("id") val id: Int,
        @SerializedName("stills") val imageList: List<ImageModel>?
)