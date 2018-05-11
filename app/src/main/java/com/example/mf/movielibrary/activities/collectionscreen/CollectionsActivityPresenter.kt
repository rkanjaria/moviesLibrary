package com.example.mf.movielibrary.activities.collectionscreen

import com.example.mf.movielibrary.base.BasePresenterImpl
import com.example.mf.movielibrary.helpers.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by RK on 10-05-2018.
 */
class CollectionsActivityPresenter : BasePresenterImpl<CollectionsActivityContract.CollectionsView>(),
        CollectionsActivityContract.CollectionsPresenter {

    override fun callGetListApi(listId: Int) {

        mView?.showProgressBar()
        RetrofitHelper.create().doGetListApiCall(listId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ collectionsResult ->

                    mView?.hideProgressBar()
                    mView?.setCollectionsRecyclerview(collectionsResult)
                }, { error ->
                    mView?.hideProgressBar()
                    mView?.showMessage(error.localizedMessage)
                })
    }
}