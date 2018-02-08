package com.example.mf.movielibrary.activities.searchscreen

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.view.View
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.MovieRecyclerAdapter
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.models.moviemodel.Movie
import files.MOVIE
import files.calculateNoOfColumns
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : BaseActivity<SearchActivityContract.SearchBaseView, SearchActivityPresenter>(),
        SearchActivityContract.SearchBaseView, MovieRecyclerAdapter.OnMovieSeriesAdapterListener, SearchView.OnQueryTextListener {

    private val mSearchList = mutableListOf<Movie?>()
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var movieAdapter: MovieRecyclerAdapter
    private var queryPage = 1
    private var totalpages = -1
    private var movieOrSeries = MOVIE
    private var searchQuery: String? = null

    override var mPresenter = SearchActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        gridLayoutManager = GridLayoutManager(this, calculateNoOfColumns(this, 110))
        searchRecyclerView.layoutManager = gridLayoutManager
        movieAdapter = MovieRecyclerAdapter(mSearchList, this)
        searchRecyclerView.adapter = movieAdapter

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (movieAdapter.getItemViewType(position) == movieAdapter.LOADER_VIEW)
                    gridLayoutManager.spanCount else 1
            }
        }

        movieSeriesSearchView.setOnQueryTextListener(this)
    }

    override fun setSearchRecyclerView(moviesList: List<Movie?>?, totalPages: Int) {

        totalpages = totalPages

        if (moviesList != null) {

            if (mSearchList.isEmpty()) {
                // for first time when data loads, add items to list and refresh the recyclerview
                mSearchList.addAll(moviesList)
                movieAdapter.notifyDataSetChanged()
            } else {
                // for the second time remove the loader view and add the data and refresh the recyclerview
                mSearchList.removeAt(mSearchList.size - 1)
                val lastPosition = mSearchList.size
                if (moviesList.isNotEmpty()) {
                    mSearchList.addAll(moviesList)
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
        searchRecyclerView.post({ loadMoreData() })
    }

    private fun loadMoreData() {

        queryPage++

        if (queryPage <= totalpages && queryPage <= 1000) {

            mSearchList.add(null)
            movieAdapter.notifyItemInserted(mSearchList.size - 1)
            mPresenter.callSearchMovieOrSeriesByName(movieOrSeries, searchQuery, queryPage)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        searchQuery = query
        clearListAndMakeApiCallAgain()
        return true
    }

    override fun onQueryTextChange(newText: String?) = false

    private fun clearListAndMakeApiCallAgain() {
        mSearchList.clear()
        searchRecyclerView.removeAllViews()
        queryPage = 1
        mPresenter.callSearchMovieOrSeriesByName(movieOrSeries, searchQuery, queryPage)
    }

    override fun onMovieOrSeriesClicked(movieModel: Movie?) {
        mPresenter.launchMovieSeriesActivity(movieModel, movieOrSeries)
    }

}
