package com.example.mf.movielibrary.interfaces

interface ActorsMoviesSeriesListener {
    fun onCallActorsCreditsApi(actorId: Int, moviesOrSeriesCredits: String)
}