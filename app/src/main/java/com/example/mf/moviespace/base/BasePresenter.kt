package com.example.mf.moviespace.base

/**
 * Created by MF on 28-11-2017.
 */
interface BasePresenter<in V : BaseView> {

    fun attachView(view : V)
    fun detachView()
}