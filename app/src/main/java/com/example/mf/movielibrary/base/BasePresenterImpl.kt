package com.example.mf.movielibrary.base

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by RK on 28-11-2017.
 */
open class BasePresenterImpl<V : BaseView> : BasePresenter<V> {

    protected  var mView : V? = null

    override fun attachView(view : V) {
        mView = view
    }

    override fun detachView() {
        mView = null
    }

    override fun isNetworkAvailable(): Boolean {
        val connectivityManager = mView?.getContext()?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.getActiveNetworkInfo()
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()
    }
}