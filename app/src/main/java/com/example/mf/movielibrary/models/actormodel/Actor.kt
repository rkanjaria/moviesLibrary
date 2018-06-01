package com.example.mf.movielibrary.models.actormodel

import com.example.mf.movielibrary.models.moviemodel.Movie
import com.google.gson.annotations.SerializedName

data class Actor(
  @SerializedName("birthday")val birthday: String?,
  @SerializedName("deathday")val deathday: String?,
  @SerializedName("id")val actorId: Int,
  @SerializedName("name")val actorName: String?,
  @SerializedName("gender")val gender: Int,
  @SerializedName("biography")val biography: String?,
  @SerializedName("place_of_birth")val placeOfBirth: String?,
  @SerializedName("profile_path")val profileImage: String?,
  @SerializedName("imdb_id")val imdbId: String?,
  @SerializedName("homepage")val homePage: String?,
  @SerializedName("known_for")val knownForList: List<Movie>?
)

