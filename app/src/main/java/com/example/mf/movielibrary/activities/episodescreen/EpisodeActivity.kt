package com.example.mf.movielibrary.activities.episodescreen

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.base.BaseActivity

class EpisodeActivity : BaseActivity<EpisodeActivityContract.EpisodeView, EpisodeActivityPresenter>() {

    override var mPresenter = EpisodeActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode)
    }
}
