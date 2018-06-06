package com.example.mf.movielibrary.activities.actorsscreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView
import com.example.mf.movielibrary.models.actormodel.Actor
import com.example.mf.movielibrary.models.moviemodel.Movie

class ActorsActivityContract {

    interface ActorsView : BaseView {
        fun setActorsData(actor: Actor)
        fun showProgressLoading()
        fun hideProgressLoading()
        fun setActorMoviesRecyclerview(moviesList: List<Movie?>)
    }

    interface ActorsPresenter : BasePresenter<ActorsView> {
        fun callGetActorApi(actorId: Int)
        fun callGetActorsMoviesOrSeriesApi(actorId: Int)
        fun launchMovieSeriesActivity(movieModel: Movie?)
        fun launchImagesActivity(actorId: Int)
        fun launchActorsMoviesSeriesActivity(actorModel: Actor)
    }
}