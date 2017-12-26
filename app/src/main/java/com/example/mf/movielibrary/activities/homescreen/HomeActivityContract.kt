package com.example.mf.movielibrary.activities.homescreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView

/**
 * Created by MF on 28-11-2017.
 */

object HomeActivityContract {

    interface View : BaseView {
    }

    interface Presenter : BasePresenter<View>{
        fun callGetMoviesApi(movieOrSeries : String, requestType : String , page : Int)

    }

}