package com.example.mf.movielibrary.activities.collectionlistscreen

import com.example.mf.movielibrary.base.BasePresenterImpl

/**
 * Created by RK on 10-05-2018.
 */
class CollectionsListActivityPresenter : BasePresenterImpl<CollectionsListActivityContract.CollectionsListView>(),
        CollectionsListActivityContract.CollectionsListPresenter {
    override fun requestCollectionsRecyclerview() {
        mView?.setCollectionsRecyclerview()
    }

}