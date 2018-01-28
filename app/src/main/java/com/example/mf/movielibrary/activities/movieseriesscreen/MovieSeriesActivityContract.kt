package com.example.mf.movielibrary.activities.movieseriesscreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView
import com.example.mf.movielibrary.models.castmodel.Cast

/**
 * Created by MF on 04-01-2018.
 */
object MovieSeriesActivityContract {

    interface MovieSeriesView : BaseView{
        fun setCastRecyclerview(castList: List<Cast>)

    }

    interface MovieSeriesPresenter : BasePresenter<MovieSeriesView>{
        fun callgetMovieOrSeriesCastApi(movieOrSeries: String?, movieId: Int)
        fun getMovieGenres(genreIds: List<Int>?) : String
        fun launchActorActivity(castModel: Cast)
    }
}