package com.example.mf.movielibrary.activities.collectionlistscreen

import android.content.Intent
import com.example.mf.movielibrary.activities.collectionscreen.CollectionsActivity
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