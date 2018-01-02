package com.example.mf.movielibrary.activities.homescreen

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.MovieRecyclerAdapter
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.models.Movie
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.view.*


/**
 * Created by MF on 28-11-2017.
 */
class HomeActivity : BaseActivity<HomeActivityContract.View, HomeActivityPresenter>(),
        HomeActivityContract.View {

    override var mPresenter: HomeActivityPresenter = HomeActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initToolbar(toolbar as Toolbar, true, "Movies")
        mPresenter.callGetMoviesApi("movie", "popular", 1)
    }

    override fun setMovieRecyclerView(moviesList: List<Movie> ?) {

        if(moviesList != null && !moviesList.isEmpty()) {
            movieRecyclerView.layoutManager = GridLayoutManager(this, 3)
            movieRecyclerView.adapter = MovieRecyclerAdapter(moviesList)
        }
    }
}