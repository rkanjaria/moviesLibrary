package com.example.mf.movielibrary.activities.collectionlistscreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView

/**
 * Created by RK on 10-05-2018.
 */
class CollectionsListActivityContract {

    interface CollectionsListView : BaseView {
        fun setCollectionsRecyclerview()
    }

    interface CollectionsListPresenter : BasePresenter<CollectionsListView> {
        fun requestCollectionsRecyclerview()
    }
}