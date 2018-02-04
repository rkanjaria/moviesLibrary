package com.example.mf.movielibrary.activities.imgescreen

import com.bumptech.glide.Glide
import com.example.mf.movielibrary.base.BasePresenterImpl
import com.example.mf.movielibrary.helpers.RetrofitHelper
import com.example.mf.movielibrary.models.actormodel.ActorImagesResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by MF on 01-02-2018.
 */
class ImageActivityPresenter : BasePresenterImpl<ImageActivityContract.ImageBaseView>(), ImageActivityContract.ImagePresenter {

    override fun callActorsImageApi(actorId: Int) {
        mView?.showProgressLoading()
        RetrofitHelper.create().doGetActorsImagesApiCall(actorId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ actorImageResult ->
                    mView?.hideProgressLoading()
                    if (actorImageResult.actorImagesList != null && actorImageResult.actorImagesList.isNotEmpty()) {
                        mView?.setImageViewPager(actorImageResult.actorImagesList.sortedBy { it.voteAverage }.map { it.filePath })

                    }
                }, { error ->
                    mView?.hideProgressLoading()
                    mView?.showMessage(error.localizedMessage)
                })
    }
}