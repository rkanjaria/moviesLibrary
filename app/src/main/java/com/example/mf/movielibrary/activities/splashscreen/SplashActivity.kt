package com.example.mf.movielibrary.activities.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.activities.homescreen.HomeActivity
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.files.MOVIE
import com.example.mf.movielibrary.files.TV_SHOWS
import com.example.mf.movielibrary.files.database
import com.example.mf.movielibrary.files.loadDrawable
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity<SplashActivityContract.SplashView, SplashActivityPresenter>(),
        SplashActivityContract.SplashView {

    override var mPresenter: SplashActivityPresenter = SplashActivityPresenter()
    var flag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        appIcon.loadDrawable(R.mipmap.ic_launcher)

        Handler().postDelayed({
            val isTableEmpty = database.genreDao().isGenreTableEmpty()
            if (isTableEmpty <= 0) {
                mPresenter.callGetGenreListApi(MOVIE, flag)
            } else {
                finishActivityAndStartAnotherActivity(
                        Intent(this, HomeActivity::class.java))
            }
        }, 1000)
    }

    override fun callGetTvGenreListApi() {
        flag = 1
        mPresenter.callGetGenreListApi(TV_SHOWS, flag)
    }


}
