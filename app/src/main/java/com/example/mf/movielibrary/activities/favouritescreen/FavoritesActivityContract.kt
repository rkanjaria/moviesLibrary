package com.example.mf.movielibrary.activities.favouritescreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BaseView

/**
 * Created by MF on 05-04-2018.
 */
class FavoritesActivityContract {

    interface FavoritesView : BaseView {
        fun setupViewPager()
    }
    interface FavoritesPresenter : BasePresenter<FavoritesView> {
        fun requestSetupViewPager()
    }
}