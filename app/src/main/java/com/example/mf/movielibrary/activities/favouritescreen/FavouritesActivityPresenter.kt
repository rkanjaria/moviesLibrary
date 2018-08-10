package com.example.mf.movielibrary.activities.favouritescreen

import com.example.mf.movielibrary.base.BasePresenterImpl

/**
 * Created by RK on 05-04-2018.
 */
class FavouritesActivityPresenter : BasePresenterImpl<FavouritesActivityContract.FavoritesView>(),
        FavouritesActivityContract.FavoritesPresenter {
    override fun requestNoFavouritesLayout() {
        mView?.setNoFavouritesLayout()
    }

    override fun requestSetupViewPager() {
        mView?.setupViewPager()
    }


}