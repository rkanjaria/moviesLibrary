package com.example.mf.movielibrary.activities.collectionscreen

import com.example.mf.movielibrary.base.BasePresenterImpl
import com.example.mf.movielibrary.helpers.RetrofitHelper

/**
 * Created by RK on 10-05-2018.
 */
class CollectionsActivityPresenter : BasePresenterImpl<CollectionsActivityContract.CollectionsView>(),
        CollectionsActivityContract.CollectionsPresenter {

    override fun callGetListApi(intExtra: Int) {
        //RetrofitHelper.create().
    }
}