package com.example.mf.movielibrary.activities.movieseriesscreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView

/**
 * Created by MF on 04-01-2018.
 */
object MovieSeriesActivityContract {

    interface MovieSeriesView : BaseView{}
    interface MovieSeriesPresenter : BasePresenter<MovieSeriesView>{}
}