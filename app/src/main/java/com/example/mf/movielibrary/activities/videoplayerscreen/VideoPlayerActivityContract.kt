package com.example.mf.movielibrary.activities.videoplayerscreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView

/**
 * Created by MF on 15-03-2018.
 */
class VideoPlayerActivityContract {

    interface VideoPlayerView : BaseView {}
    interface VideoPlayerPresenter : BasePresenter<VideoPlayerView> {}
}