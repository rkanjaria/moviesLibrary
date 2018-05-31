package com.example.mf.movielibrary.activities.homescreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView
import com.example.mf.movielibrary.models.moviemodel.Movie

/**
 * Created by RK on 28-11-2017.
 */

class HomeActivityContract {

    interface HomeView : BaseView {
        fun setMovieRecyclerView(moviesList: List<Movie?>?, totalResults: Int)
        fun showProgressBar()
        fun hideProgressBar()
        fun showMovieOrSeriesTypeDialog()
    }

    interface HomePresenter : BasePresenter<HomeView>{
        fun callGetMoviesApi(movieOrSeries : String, requestType : String , page : Int)
        fun launchMovieSeriesActivity(movieModel: Movie?, movieOrSeries: String)
        fun requestMovieOrSeriesTypeDialog()
        fun requestSearchActivity(movieOrSeries: String)
        fun requestFavouritesActivity()
        fun requestCollectionsActivity()
        fun requestActorsActivity()
    }

}