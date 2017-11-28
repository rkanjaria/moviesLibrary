package com.example.mf.moviebox.base

import android.view.View

/**
 * Created by MF on 28-11-2017.
 */
interface BasePresenter<in V : BaseView> {

    fun attachView(view : V)
    fun detachView()
}