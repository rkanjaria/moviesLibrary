package com.example.mf.movielibrary.activities.searchscreen

import android.app.Activity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.MovieRecyclerAdapter
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.classes.KeyboardUtils
import com.example.mf.movielibrary.models.moviemodel.Movie
import files.*
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.displayMetrics


class SearchActivity : BaseActivity<SearchActivityContract.SearchBaseView, SearchActivityPresenter>(),
        SearchActivityContract.SearchBaseView, MovieRecyclerAdapter.OnMovieSeriesAdapterListener, SearchView.OnQueryTextListener {

    private val mSearchList = mutableListOf<Movie?>()
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var movieAdapter: MovieRecyclerAdapter
    private var queryPage = 1
    private var totalResults = -1
    private var movieOrSeries = MOVIE
    private var searchQuery: String? = null

    override var mPresenter = SearchActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        if(intent.getStringExtra(MOVIE_OR_SERIES) == MOVIE){
            movieOrSeries = MOVIE
            movieSeriesSearchView.queryHint = "Search movies"
        }else{
            movieOrSeries = TV_SHOWS
            movieSeriesSearchView.queryHint = "Search Tv shows"
        }

        gridLayoutManager = GridLayoutManager(this, calculateNoOfColumns(this, 110))
        searchRecyclerView.layoutManager = gridLayoutManager

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (movieAdapter.getItemViewType(position) == movieAdapter.LOADER_VIEW)
                    gridLayoutManager.spanCount else 1
            }
        }

        val list = resources.getStringArray(R.array.search_tags).toList()
        tagsLayout.asTagsLayout(list)
        movieSeriesSearchView.setOnQueryTextListener(this)

    }

    override fun setSearchRecyclerView(moviesList: List<Movie?>?, totalResult: Int) {

        totalResults = totalResult

        if (moviesList != null) {

            if (mSearchList.isEmpty()) {
                // for first time when data loads, set the adapter of recyclerview this helps in solving pagination issue since new adapter is set no refreshed
                mSearchList.addAll(moviesList)
                movieAdapter = MovieRecyclerAdapter(mSearchList, this)
                searchRecyclerView.adapter = movieAdapter

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

        if (mSearchList.size < totalResults) {

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
        KeyboardUtils.hideSoftInputKeyboard(this)
        mSearchList.clear()
        searchRecyclerView.removeAllViews()
        queryPage = 1
        mPresenter.callSearchMovieOrSeriesByName(movieOrSeries, searchQuery, queryPage)
    }

    override fun onMovieOrSeriesClicked(movieModel: Movie?) {
        mPresenter.launchMovieSeriesActivity(movieModel, movieOrSeries)
    }

    /*fun asTagsLayout(tagsList: List<String>) {

        val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, displayMetrics).toInt()
        var previousTagWidth = 0
        var previousTagHeight = 0
        var left = 0
        var top = 0

        val displayMetrices = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrices)
        val screenWidth = displayMetrices.widthPixels

        val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val tagLayout = RelativeLayout(this)
        tagLayout.setPadding(0, px * 2, 0, 0)
        tagLayout.layoutParams = params

        tagsList.forEach {
            val tagView = TextView(this)
            tagView.setPadding(px, px, px, px)
            tagView.text = tagsList.get(tagsList.indexOf(it))
            tagView.background = ContextCompat.getDrawable(this, R.drawable.tag_background_drawable)
            tagView.typeface = ResourcesCompat.getFont(this, R.font.noto_sans_regular)
            tagView.maxLines = 1
            tagView.gravity = Gravity.CENTER
            tagView.ellipsize = TextUtils.TruncateAt.END

            val tagViewParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            left = left + previousTagWidth

            if ((screenWidth - px - left) < tagView.measureTextViewWidth()) {
                top = top + previousTagHeight
                left = 0
            }

            tagViewParams.setMargins(left, top, 0, 0)
            tagView.layoutParams = tagViewParams
            tagLayout.addView(tagView)

            previousTagWidth = tagView.measureTextViewWidth() + px
            previousTagHeight = tagView.measureTextViewHeight() + px
        }

        (tagsLayout as LinearLayout).addView(tagLayout)

    }*/

}
