package com.example.mf.movielibrary.activities.searchscreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView
import com.example.mf.movielibrary.models.moviemodel.Movie

/**
 * Created by MF on 08-02-2018.
 */
class SearchActivityContract {

    interface SearchBaseView : BaseView {
        fun setSearchRecyclerView(moviesList: List<Movie?>?, totalResult: Int)
        fun showProgressBar()
        fun hideProgressBar()
        fun showSearchTypeDialog()
    }
    interface SearchPresenter : BasePresenter<SearchBaseView> {
        fun callSearchMovieOrSeriesByName(movieOrSeries: String, query: String?, queryPage: Int)
        fun launchMovieSeriesActivity(movieModel: Movie?, movieOrSeries: String)
        fun requestSearchTypeDialog()
    }
}