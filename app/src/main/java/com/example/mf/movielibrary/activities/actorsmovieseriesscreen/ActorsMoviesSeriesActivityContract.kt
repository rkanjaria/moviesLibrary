package com.example.mf.movielibrary.activities.actorsmovieseriesscreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView
import com.example.mf.movielibrary.models.moviemodel.Movie

class ActorsMoviesSeriesActivityContract {

    interface ActorsMoviesSeriesView : BaseView {
        fun setViewPager()
        fun setMovieOrSeriesRecyclerView(moviesList: List<Movie?>?, totalResults: Int, moviesOrSeriesCredits: String)
        fun hideProgressBar(moviesOrSeriesCredits: String)
        fun showProgressBar(moviesOrSeriesCredits: String)
    }

    interface ActorsMoviesSeriesPresenter : BasePresenter<ActorsMoviesSeriesView> {
        fun requestViewPager()
        fun callActorsCreditsApi(actorId: Int, moviesOrSeriesCredits: String)
    }
}