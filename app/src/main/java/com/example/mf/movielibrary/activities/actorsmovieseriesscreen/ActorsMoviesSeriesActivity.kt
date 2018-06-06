package com.example.mf.movielibrary.activities.actorsmovieseriesscreen

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.Toolbar
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.ActorsMoviesSeriesAdapter
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.fragments.ActorsMovieSeriesFragment
import com.example.mf.movielibrary.models.moviemodel.Movie
import files.BACKDROP_PATH
import files.NAME
import files.loadImage
import files.photoUrl
import kotlinx.android.synthetic.main.activity_actors_movies_series.*

class ActorsMoviesSeriesActivity : BaseActivity<ActorsMoviesSeriesActivityContract.ActorsMoviesSeriesView,
        ActorsMoviesSeriesActivityPresenter>(), ActorsMoviesSeriesActivityContract.ActorsMoviesSeriesView,
        ActorsMovieSeriesFragment.ActorsMoviesSeriesListener {

    override fun onCallActorsCreditsApi(actorId: Int, moviesOrSeriesCredits: String, page: Int) {
        mPresenter.callActorsCreditsApi(actorId, moviesOrSeriesCredits, page)
    }

    override var mPresenter = ActorsMoviesSeriesActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actors_movies_series)

        initToolbar(toolbar as Toolbar, true, "")
        actorsImage.loadImage(photoUrl + intent.getStringExtra(BACKDROP_PATH), R.color.darkGrey)
        mPresenter.requestViewPager()

        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }

                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.title = intent.getStringExtra(NAME)
                    isShow = true
                } else if (isShow) {
                    collapsingToolbar.title = " "
                    isShow = false
                }
            }
        })
    }

    override fun setViewPager() {
        movieSeriesViewpager.adapter = ActorsMoviesSeriesAdapter(supportFragmentManager,
                resources.getStringArray(R.array.toolbar_array))
        tabLayout.setupWithViewPager(movieSeriesViewpager)
    }

    override fun setMovieOrSeriesRecyclerView(moviesList: List<Movie?>?, totalResults: Int) {
        ActorsMovieSeriesFragment().setMovieOrSeriesRecyclerView(moviesList, totalResults)
    }

    override fun hideProgressBar() {
        ActorsMovieSeriesFragment().hideProgressBar()
    }

    override fun showProgressBar() {
        ActorsMovieSeriesFragment().showProgressBar()
    }
}
