package com.example.mf.movielibrary.activities.episodescreen

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.models.seasonmodels.Episode
import files.PARCELABLE_OBJECT
import files.backdropUrl
import files.getDateWithCustomFormat
import files.loadImage
import kotlinx.android.synthetic.main.activity_episode.*

class EpisodeActivity : BaseActivity<EpisodeActivityContract.EpisodeView, EpisodeActivityPresenter>() {

    override var mPresenter = EpisodeActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_episode)

        val episodeModel = intent.getParcelableExtra(PARCELABLE_OBJECT) as Episode
        initToolbar(toolbar as Toolbar, true,"")

        episodeBackdropImage.loadImage(backdropUrl + episodeModel.stillPath)
        episodeName.text = episodeModel.episodeName
        episodeOverview.text = episodeModel.episodeOverview
        episodeAirDate.text = getDateWithCustomFormat(episodeModel.episodeAirDate)
        episodeVoteAverage.text = episodeModel.voteAverage.toString()
    }
}
