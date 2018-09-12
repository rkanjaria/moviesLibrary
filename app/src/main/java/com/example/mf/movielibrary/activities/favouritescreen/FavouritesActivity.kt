package com.example.mf.movielibrary.activities.favouritescreen

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.FavouritesViewPagerAdapter
import com.example.mf.movielibrary.base.BaseActivity
import files.database
import files.loadDrawable
import kotlinx.android.synthetic.main.activity_favorites.*

class FavouritesActivity : BaseActivity<FavouritesActivityContract.FavoritesView, FavouritesActivityPresenter>(),
        FavouritesActivityContract.FavoritesView {

    override var mPresenter = FavouritesActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        initToolbar(toolbar as Toolbar, true, "Favourites")

        if (this.database.favouriteMovieDao().isFavouriteTableEmpty() <= 0) {
            mPresenter.requestNoFavouritesLayout()
        } else {
            mPresenter.requestSetupViewPager()
        }
    }

    override fun setupViewPager() {
        movieSeriesViewpager.adapter = FavouritesViewPagerAdapter(supportFragmentManager,
                resources.getStringArray(R.array.toolbar_array))
        tabLayout.setupWithViewPager(movieSeriesViewpager)
    }

    override fun setNoFavouritesLayout() {
        noFavavouritesImage.loadDrawable(R.drawable.no_fav)
        movieSeriesViewpager.visibility = View.GONE
        tabLayout.visibility = View.GONE
        noFavouritesLayout.visibility = View.VISIBLE
    }
}
