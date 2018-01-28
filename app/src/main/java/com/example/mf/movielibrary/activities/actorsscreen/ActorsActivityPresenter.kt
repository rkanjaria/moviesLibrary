package com.example.mf.movielibrary.activities.actorsscreen

import com.example.mf.movielibrary.base.BasePresenterImpl
import com.example.mf.movielibrary.helpers.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ActorsActivityPresenter : BasePresenterImpl<ActorsActivityContract.ActorsView>(),
        ActorsActivityContract.ActorsPresenter {

    override fun callGetActorApi(actorId: Int) {
        mView?.showProgressLoading()
        RetrofitHelper.create().doGetActorApiCall(actorId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ actorResult ->

                    if (actorResult != null) {
                        mView?.hideProgressLoading()
                        mView?.setActorsData(actorResult)
                    }

                }, { error ->
                    mView?.hideProgressLoading()
                    error.printStackTrace()
                    mView?.showMessage(error.localizedMessage)
                })
    }


}