package com.example.mf.movielibrary.activities.searchscreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView
import com.example.mf.movielibrary.database.entities.Genre
import com.example.mf.movielibrary.models.actormodel.Actor
import com.example.mf.movielibrary.models.moviemodel.Movie

/**
 * Created by RK on 08-02-2018.
 */
class SearchActivityContract {

    interface SearchBaseView : BaseView {
        fun setSearchRecyclerView(moviesList: List<Movie?>?, totalResult: Int)
        fun showProgressBar()
        fun hideProgressBar()
        fun showSearchTypeDialog()
        fun setGenreRecylerview(genreList: List<Genre>)
        fun setActorsSearchRecyclerView(actorsList: List<Actor>?, totalResult: Int)
    }

    interface SearchPresenter : BasePresenter<SearchBaseView> {
        fun callSearchMovieOrSeriesByName(movieOrSeries: String, query: String?, queryPage: Int)
        fun launchMovieSeriesActivity(movieModel: Movie?, movieOrSeries: String)
        fun requestSearchTypeDialog()
        fun getGenreFromDb(movieOrSeries: String)
        fun callSearchByGenreApi(movieOrSeries: String, page: Int, genreId: Int)
        fun callSearchPeopleApi(searchQuery: String?, queryPage: Int)
        fun launchActorsActivity(actorModel: Actor?)
    }
}