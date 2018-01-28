package com.example.mf.movielibrary.activities.splashscreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView

class SplashActivityContract {
    interface SplashView : BaseView {
        fun callGetTvGenreListApi()

    }
    interface SplashPresenter : BasePresenter<SplashView> {
        fun callGetGenreListApi(movieOrSeries: String, flag : Int)
        fun clearTable(table: String)
    }
}