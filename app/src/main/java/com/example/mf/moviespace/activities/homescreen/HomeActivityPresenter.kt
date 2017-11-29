package com.example.mf.moviespace.activities.homescreen

import com.example.mf.moviespace.base.BasePresenterImpl

/**
 * Created by MF on 28-11-2017.
 */
class HomeActivityPresenter : BasePresenterImpl<HomeActivityContract.View>(), HomeActivityContract.Presenter{
    override fun showToast(str: String) {
        mView?.showRequestedMessage(str)
    }
}