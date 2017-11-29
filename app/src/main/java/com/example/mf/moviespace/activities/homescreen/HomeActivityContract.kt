package com.example.mf.moviespace.activities.homescreen

import com.example.mf.moviespace.base.BasePresenter
import com.example.mf.moviespace.base.BaseView

/**
 * Created by MF on 28-11-2017.
 */

object HomeActivityContract {

    interface View : BaseView {
        fun showRequestedMessage(str: String)

    }

    interface Presenter : BasePresenter<View>{
        fun showToast(str: String)

    }

}