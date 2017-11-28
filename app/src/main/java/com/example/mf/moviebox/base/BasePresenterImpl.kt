package com.example.mf.moviebox.base

import android.view.View

/**
 * Created by MF on 28-11-2017.
 */
open class BasePresenterImpl<V : BaseView> : BasePresenter<V> {

    protected  var mView : V? = null

    override fun attachView(view : V) {
        mView = view
    }

    override fun detachView() {
        mView = null
    }
}