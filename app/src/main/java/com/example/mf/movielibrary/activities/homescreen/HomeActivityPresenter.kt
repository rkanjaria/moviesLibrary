package com.example.mf.movielibrary.activities.homescreen

import com.example.mf.movielibrary.base.BasePresenterImpl
import com.example.mf.movielibrary.helpers.RetrofitHelper

/**
 * Created by MF on 28-11-2017.
 */
class HomeActivityPresenter : BasePresenterImpl<HomeActivityContract.View>(), HomeActivityContract.Presenter {
    override fun callGetMoviesApi() {
        RetrofitHelper.create()
    }
}
