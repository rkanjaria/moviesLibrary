package com.example.mf.movielibrary.activities.seasonscreen

import android.content.Intent
import com.example.mf.movielibrary.activities.episodescreen.EpisodeActivity
import com.example.mf.movielibrary.base.BasePresenterImpl
import com.example.mf.movielibrary.classes.NoInternetConnectionException
import com.example.mf.movielibrary.helpers.RetrofitHelper
import com.example.mf.movielibrary.models.seasonmodels.Episode
import files.ID
import files.INT_ID
import files.PARCELABLE_OBJECT
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by RK on 06-02-2018.
 */
class SeasonActivityPresenter : BasePresenterImpl<SeasonActivityContract.SeasonView>(), SeasonActivityContract.SeasonPresenter {

    override fun lanchEpisodeActivity(episode: Episode, seriesId: Int, seasonNumber: Int) {
        val episodeIntent = Intent(mView?.getContext(), EpisodeActivity::class.java)
        episodeIntent.putExtra(PARCELABLE_OBJECT, episode)
        episodeIntent.putExtra(ID, seriesId)
        episodeIntent.putExtra(INT_ID, seasonNumber)
        mView?.getContext()?.startActivity(episodeIntent)
    }

    override fun callGetSesonDetailsApi(seriesId: Int, seasonNumber: Int) {
        try {
            if (seriesId > 0 && seasonNumber > 0) {
                mView?.showProgressLoading()
                RetrofitHelper.create(mView).doGetSeasonsDetailsApiCall(seriesId, seasonNumber)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ seasonResult ->
                            mView?.hideProgressLoading()
                            if (seasonResult.episodeList != null) {
                                mView?.setEpisodesRecyclerview(seasonResult)
                            }
                        }, { error ->
                            mView?.hideProgressLoading()
                            mView?.showMessage(error.localizedMessage)
                        })
            }
        } catch (e: NoInternetConnectionException) {
            mView?.hideProgressLoading()
        }
    }
}