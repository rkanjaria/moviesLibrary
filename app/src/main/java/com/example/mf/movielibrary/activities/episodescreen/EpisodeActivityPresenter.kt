package com.example.mf.movielibrary.activities.episodescreen

import com.example.mf.movielibrary.base.BasePresenterImpl
import com.example.mf.movielibrary.helpers.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by RK on 15-02-2018.
 */
class EpisodeActivityPresenter : BasePresenterImpl<EpisodeActivityContract.EpisodeView>(), EpisodeActivityContract.EpisodePresenter {

    override fun callGetEpisodesImageApi(seriesId: Int, seasonNumber: Int, episodeNumber: Int) {
        RetrofitHelper.create()
                .doGetEpisodeImagesApiCall(seriesId, seasonNumber, episodeNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ episodeImageResult ->

                    if (episodeImageResult.imageList != null && episodeImageResult.imageList.isNotEmpty()) {
                        mView?.addImagesToList(episodeImageResult.imageList.subList(1, episodeImageResult.imageList.size).map { it.filePath!! })
                    }

                }, { error ->
                    error.printStackTrace()
                })

    }
}