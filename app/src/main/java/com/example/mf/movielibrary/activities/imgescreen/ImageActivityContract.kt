package com.example.mf.movielibrary.activities.imgescreen

import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView

/**
 * Created by RK on 01-02-2018.
 */
class ImageActivityContract {

    interface ImageBaseView : BaseView {
        fun setImageViewPager(imageList: List<String?>)
        fun showProgressLoading()
        fun hideProgressLoading()
    }

    interface ImagePresenter : BasePresenter<ImageBaseView> {
        fun callActorsImageApi(actorId: Int)
    }
}