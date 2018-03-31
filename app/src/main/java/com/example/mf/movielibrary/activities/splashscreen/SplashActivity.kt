package com.example.mf.movielibrary.activities.splashscreen

import android.content.Intent
import android.os.Bundle
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.activities.homescreen.HomeActivity
import com.example.mf.movielibrary.base.BaseActivity
import files.MOVIE
import files.TV_SHOWS
import files.database

class SplashActivity : BaseActivity<SplashActivityContract.SplashView, SplashActivityPresenter>(),
        SplashActivityContract.SplashView {

    override var mPresenter: SplashActivityPresenter = SplashActivityPresenter()
    var flag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val isTableEmpty = database.isMovieTableEmpty()

        if (isTableEmpty) {
            mPresenter.callGetGenreListApi(MOVIE, flag)
        } else {
            finishActivityAndStartAnotherActivity(
                    Intent(this, HomeActivity::class.java))
        }
    }

    override fun callGetTvGenreListApi() {
        flag = 1
        mPresenter.callGetGenreListApi(TV_SHOWS, flag)
    }


}
