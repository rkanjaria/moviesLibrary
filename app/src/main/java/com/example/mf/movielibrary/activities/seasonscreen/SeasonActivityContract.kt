package com.example.mf.movielibrary.activities.seasonscreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView
import com.example.mf.movielibrary.models.seasonmodels.Episode
import com.example.mf.movielibrary.models.seasonmodels.SeasonResult

/**
 * Created by MF on 06-02-2018.
 */
class SeasonActivityContract {

    interface SeasonView : BaseView {
        fun setEpisodesRecyclerview(seasonResult: SeasonResult?)
        fun showProgressLoading()
        fun hideProgressLoading()
    }
    interface SeasonPresenter : BasePresenter<SeasonView> {
        fun callGetSesonDetailsApi(seriesId: Int, seasonNumber: Int)
        fun lanchEpisodeActivity(episode: Episode)
    }
}