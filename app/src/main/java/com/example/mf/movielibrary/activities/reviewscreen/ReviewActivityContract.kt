package com.example.mf.movielibrary.activities.reviewscreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView

class ReviewActivityContract {
    interface ReviewView : BaseView {
        fun setReviewRecylerview()
    }
    interface ReviewPresenter : BasePresenter<ReviewView> {
        fun requestReviewRecyclerview()
    }
}