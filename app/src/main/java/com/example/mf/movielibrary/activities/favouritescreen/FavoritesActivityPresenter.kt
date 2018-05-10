package com.example.mf.movielibrary.activities.favouritescreen

import com.example.mf.movielibrary.base.BasePresenter
import com.example.mf.movielibrary.base.BasePresenterImpl

/**
 * Created by RK on 05-04-2018.
 */
class FavoritesActivityPresenter : BasePresenterImpl<FavoritesActivityContract.FavoritesView>(),
        FavoritesActivityContract.FavoritesPresenter {
    override fun requestSetupViewPager() {
        mView?.setupViewPager()
    }


}