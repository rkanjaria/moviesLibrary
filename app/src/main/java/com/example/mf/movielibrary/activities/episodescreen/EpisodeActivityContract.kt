package com.example.mf.movielibrary.activities.episodescreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView

/**
 * Created by MF on 15-02-2018.
 */
class EpisodeActivityContract {

    interface EpisodeView : BaseView {}
    interface EpisodePresenter : BasePresenter<EpisodeView> {}
}