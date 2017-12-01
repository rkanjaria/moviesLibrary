package com.example.mf.movielibrary.base

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