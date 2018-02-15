package com.example.mf.movielibrary.activities.seasonscreen

import android.content.Intent
import com.example.mf.movielibrary.activities.episodescreen.EpisodeActivity
import com.example.mf.movielibrary.base.BasePresenterImpl
import com.example.mf.movielibrary.helpers.RetrofitHelper
import com.example.mf.movielibrary.models.seasonmodels.Episode
import files.PARCELABLE_OBJECT
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by MF on 06-02-2018.
 */
class SeasonActivityPresenter : BasePresenterImpl<SeasonActivityContract.SeasonView>(), SeasonActivityContract.SeasonPresenter {

    override fun lanchEpisodeActivity(episode: Episode) {
        val episodeIntent = Intent(mView?.getContext(), EpisodeActivity::class.java)
        episodeIntent.putExtra(PARCELABLE_OBJECT, episode)
        mView?.getContext()?.startActivity(episodeIntent)
    }

    override fun callGetSesonDetailsApi(seriesId: Int, seasonNumber: Int) {
        if (seriesId > 0 && seasonNumber > 0) {

            mView?.showProgressLoading()
            RetrofitHelper.create().doGetSeasonsDetailsApiCall(seriesId, seasonNumber)
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
    }
}