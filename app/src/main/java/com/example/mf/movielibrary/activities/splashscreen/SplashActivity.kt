package com.example.mf.movielibrary.activities.splashscreen

import android.os.Bundle
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.base.BaseActivity
import files.MOVIE
import files.MOVIE_TABLE
import files.TV_SHOWS

class SplashActivity : BaseActivity<SplashActivityContract.SplashView, SplashActivityPresenter>(),
        SplashActivityContract.SplashView {
    override fun callGetTvGenreListApi() {
        flag = 1
        mPresenter.callGetGenreListApi(TV_SHOWS, flag)
    }

    override var mPresenter: SplashActivityPresenter = SplashActivityPresenter()
    var flag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mPresenter.callGetGenreListApi(MOVIE, flag)
    }

}
