package com.example.mf.movielibrary.activities.collectionscreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView
import com.example.mf.movielibrary.models.collectionmodels.CollectionsResult
import com.example.mf.movielibrary.models.moviemodel.Movie

/**
 * Created by RK on 10-05-2018.
 */
class CollectionsActivityContract {

    interface CollectionsView : BaseView {
        fun setCollectionsRecyclerview(collectionsResult: CollectionsResult)
        fun showProgressBar()
        fun hideProgressBar()
    }

    interface CollectionsPresenter : BasePresenter<CollectionsView> {
        fun callGetListApi(intExtra: Int)
        fun launchMoviesOrSeriesActivity(movieModel: Movie?)
    }
}