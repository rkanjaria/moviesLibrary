package com.example.mf.moviebox.activities.homescreen

import com.example.mf.moviebox.base.BasePresenter
import com.example.mf.moviebox.base.BaseView

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