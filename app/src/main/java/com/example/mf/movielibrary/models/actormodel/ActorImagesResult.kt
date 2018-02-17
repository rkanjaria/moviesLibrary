package com.example.mf.movielibrary.models.actormodel

import com.example.mf.movielibrary.models.imagemodels.ImageModel
import com.google.gson.annotations.SerializedName

data class ActorImagesResult(
        @SerializedName("profiles") val actorImagesList: List<ImageModel>?
)