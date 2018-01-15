package com.example.mf.movielibrary.activities.homescreen

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.MovieRecyclerAdapter
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.models.Movie
import files.MOVIE
import files.TV_SHOWS
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.spinner_toolbar.*


/**
 * Created by MF on 28-11-2017.
 */
class HomeActivity : BaseActivity<HomeActivityContract.HomeView, HomeActivityPresenter>(),
        HomeActivityContract.HomeView, MovieRecyclerAdapter.OnLoadMoreListener, AdapterView.OnItemSelectedListener {
    private val mMoviesList = mutableListOf<Movie?>()

    private lateinit var movieAdapter: MovieRecyclerAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private var page = 1
    private var totalpages = - 1
    private var movieOrSeries = MOVIE

    private var type = "popular"
    override var mPresenter: HomeActivityPresenter = HomeActivityPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar as Toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.toolbar_array, R.layout.spinner_dropdown)
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown)
        toolbarSpinner.adapter = spinnerAdapter
        toolbarSpinner.onItemSelectedListener = this

        gridLayoutManager = GridLayoutManager(this, 3)
        movieRecyclerView.layoutManager = gridLayoutManager
        movieAdapter = MovieRecyclerAdapter(mMoviesList, this)
        movieRecyclerView.adapter = movieAdapter

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                return if(movieAdapter.getItemViewType(position) == movieAdapter.LOADER_VIEW)
                    gridLayoutManager.spanCount else 1
            }
        }
    }

    override fun setMovieRecyclerView(moviesList: List<Movie?>?, totalPages: Int) {

        totalpages = totalPages

        if (moviesList != null) {

            if (mMoviesList.isEmpty()) {
                // for first time when data loads, add items to list and refresh the recyclerview
                mMoviesList.addAll(moviesList)
                movieAdapter.notifyDataSetChanged()
            } else {
                // for the second time remove the loader view and add the data and refresh the recyclerview
                mMoviesList.removeAt(mMoviesList.size - 1)
                val lastPosition = mMoviesList.size
                if(moviesList.isNotEmpty()){
                    mMoviesList.addAll(moviesList)
                }
                movieAdapter.refreshAdapter(lastPosition)
            }
        }
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun loadMore() {
        movieRecyclerView.post({ loadMoreData() })
    }

    private fun loadMoreData() {

        page++

        if (page <= totalpages && page <=1000) {

            mMoviesList.add(null)
            movieAdapter.notifyItemInserted(mMoviesList.size - 1)
            mPresenter.callGetMoviesApi(movieOrSeries, type, page)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?){}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(position){
            0 -> movieOrSeries = MOVIE
            1 -> movieOrSeries = TV_SHOWS
        }
        mMoviesList.clear()
        movieRecyclerView.removeAllViews()
        page = 1
        mPresenter.callGetMoviesApi(movieOrSeries, type, page)
    }
}
