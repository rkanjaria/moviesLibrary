package com.example.mf.movielibrary.activities.seasonscreen

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.EpisodeRecyclerAdapter
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.models.movieseriesdetailsmodel.Season
import com.example.mf.movielibrary.models.seasonmodels.Episode
import com.example.mf.movielibrary.models.seasonmodels.SeasonResult
import files.INT_ID
import files.PARCELABLE_OBJECT
import files.runLayoutAnimation
import kotlinx.android.synthetic.main.activity_season.*

class SeasonActivity : BaseActivity<SeasonActivityContract.SeasonView, SeasonActivityPresenter>(),
        SeasonActivityContract.SeasonView, EpisodeRecyclerAdapter.EpisodeAdapterListener {

    override var mPresenter = SeasonActivityPresenter()
    private var seriesId = -1
    private var seasonNumber = -1
    private lateinit var seasonModel: Season

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_season)

        seasonModel = intent.getParcelableExtra(PARCELABLE_OBJECT) as Season
        seriesId = intent.getIntExtra(INT_ID, -1)
        seasonNumber = seasonModel.seasonNumber

        initToolbar(toolbar as Toolbar, true, "Season ${seasonNumber}")
        callGetSesonEpisodesApi()
    }

    private fun callGetSesonEpisodesApi() {
        mPresenter.callGetSesonDetailsApi(seriesId, seasonModel.seasonNumber)
    }

    override fun setEpisodesRecyclerview(seasonResult: SeasonResult?) {
        if (seasonResult?.episodeList != null && seasonResult.episodeList.isNotEmpty()) {
            episodesRecyclerview.setHasFixedSize(true)
            episodesRecyclerview.layoutManager = LinearLayoutManager(this)
            episodesRecyclerview.adapter = EpisodeRecyclerAdapter(seasonResult.episodeList, this)
            episodesRecyclerview.visibility = View.VISIBLE
            runLayoutAnimation(episodesRecyclerview, R.anim.layout_animation_fall_down)
        }
    }

    override fun showProgressLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressLoading() {
        progressBar.visibility = View.GONE
    }

    override fun onEpisodeClicked(episode: Episode) {
        mPresenter.lanchEpisodeActivity(episode, seriesId, seasonNumber)
    }

    override fun onSnackBarButtonClicked() {
        callGetSesonEpisodesApi()
    }
}
