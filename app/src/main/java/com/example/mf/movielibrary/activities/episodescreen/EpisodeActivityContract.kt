package com.example.mf.movielibrary.activities.episodescreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView
import com.example.mf.movielibrary.models.imagemodels.ImageModel

/**
 * Created by RK on 15-02-2018.
 */
class EpisodeActivityContract {

    interface EpisodeView : BaseView {
        fun addImagesToList(imageList: List<String>)
    }
    interface EpisodePresenter : BasePresenter<EpisodeView> {
        fun callGetEpisodesImageApi(seriesId: Int, seasonNumber: Int, episodeNumber: Int)
    }
}