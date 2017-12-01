package com.example.mf.movielibrary.base

import android.content.Context
import android.support.annotation.StringRes
import android.support.v7.widget.Toolbar


/**
 * Created by MF on 28-11-2017.
 */
interface BaseView {

    fun getContext(): Context
    fun showError(error: String?)
    fun showError(@StringRes stringResId: Int)
    fun showMessage(@StringRes stringResId: Int)
    fun showMessage(message: String)
    fun initToolbar(toolbar : Toolbar, showBackButton : Boolean, title: String)

}
