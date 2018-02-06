package com.example.mf.movielibrary.activities.seasonscreen

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.EpisodeRecyclerAdapter
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.models.movieseriesdetailsmodel.Season
import com.example.mf.movielibrary.models.seasonmodels.SeasonResult
import files.INT_ID
import files.PARCELABLE_OBJECT
import kotlinx.android.synthetic.main.activity_season.*

class SeasonActivity : BaseActivity<SeasonActivityContract.SeasonView, SeasonActivityPresenter>(),
        SeasonActivityContract.SeasonView{


    override var mPresenter = SeasonActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_season)

        val seasonModel = intent.getParcelableExtra(PARCELABLE_OBJECT) as Season
        val seriesId = intent.getIntExtra(INT_ID, -1)
        mPresenter.callGetSesonDetailsApi(seriesId, seasonModel.seasonNumber)
    }

    override fun setEpisodesRecyclerview(seasonResult: SeasonResult?) {

        if (seasonResult?.episodeList != null && seasonResult.episodeList.isNotEmpty()) {
            episodesRecyclerview.setHasFixedSize(true)
            episodesRecyclerview.layoutManager = LinearLayoutManager(this)
            episodesRecyclerview.adapter = EpisodeRecyclerAdapter(seasonResult.episodeList)
            episodesRecyclerview.visibility = View.VISIBLE
        }
    }

    override fun showProgressLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressLoading() {
        progressBar.visibility = View.GONE
    }
}
