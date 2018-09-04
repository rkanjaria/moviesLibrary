package com.example.mf.movielibrary.base



/**
 * Created by RK on 28-11-2017.
 */
interface BasePresenter<in V : BaseView> {

    fun attachView(view : V)
    fun detachView()
    fun isNetworkAvailable(): Boolean
}