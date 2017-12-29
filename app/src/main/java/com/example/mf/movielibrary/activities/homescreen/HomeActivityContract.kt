package com.example.mf.movielibrary.activities.homescreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView
import com.example.mf.movielibrary.models.Movie

/**
 * Created by MF on 28-11-2017.
 */

object HomeActivityContract {

    interface View : BaseView {
        fun setMovieRecyclerView(moviesList: List<Movie>)
    }

    interface Presenter : BasePresenter<View>{
        fun callGetMoviesApi(movieOrSeries : String, requestType : String , page : Int)

    }

}