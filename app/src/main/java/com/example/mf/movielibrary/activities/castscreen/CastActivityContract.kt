package com.example.mf.movielibrary.activities.castscreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView

class CastActivityContract {

    interface CastView : BaseView {}
    interface CastPresenter : BasePresenter<CastView> {}
}