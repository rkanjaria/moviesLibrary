package com.example.mf.movielibrary.activities.homescreen

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import butterknife.BindView
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.MovieRecyclerAdapter
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.models.Movie

/**
 * Created by MF on 28-11-2017.
 */
class HomeActivity : BaseActivity<HomeActivityContract.View, HomeActivityPresenter>(),
        HomeActivityContract.View{

    @BindView(R.id.toolbar)
    lateinit var toolbar : Toolbar

    @BindView(R.id.movie_recyclerview)
    lateinit var movieRecyclerview : RecyclerView

    override var mPresenter: HomeActivityPresenter = HomeActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        toolbar = findViewById(R.id.toolbar)
        initToolbar(toolbar , true, "Movies")
        mPresenter.callGetMoviesApi("movie", "popular", 1)
    }

    override fun setMovieRecyclerView(moviesList: List<Movie>) {

        movieRecyclerview.adapter = MovieRecyclerAdapter(moviesList)
    }
}