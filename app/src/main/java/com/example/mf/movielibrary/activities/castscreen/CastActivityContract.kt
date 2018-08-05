package com.example.mf.movielibrary.activities.castscreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView
import com.example.mf.movielibrary.models.castmodel.Cast

class CastActivityContract {

    interface CastView : BaseView {
        fun setCastRecyclerView()
    }
    interface CastPresenter : BasePresenter<CastView> {
        fun requestCasrRecyclerview()
        fun launchActorsActivity(castModel: Cast)
    }
}