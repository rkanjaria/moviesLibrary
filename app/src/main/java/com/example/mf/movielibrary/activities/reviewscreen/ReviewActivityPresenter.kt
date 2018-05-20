package com.example.mf.movielibrary.activities.reviewscreen

import com.example.mf.movielibrary.base.BasePresenterImpl

class ReviewActivityPresenter : BasePresenterImpl<ReviewActivityContract.ReviewView>(),
        ReviewActivityContract.ReviewPresenter {

    override fun requestReviewRecyclerview() {
        mView?.setReviewRecylerview()
    }
}