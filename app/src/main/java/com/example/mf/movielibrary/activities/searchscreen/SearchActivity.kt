package com.example.mf.movielibrary.activities.searchscreen

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.view.ContextThemeWrapper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.MovieRecyclerAdapter
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.classes.KeyboardUtils
import com.example.mf.movielibrary.fragments.GenreBottomSheetFragment
import com.example.mf.movielibrary.models.genremodel.Genre
import com.example.mf.movielibrary.models.moviemodel.Movie
import files.MOVIE
import files.MOVIE_OR_SERIES
import files.TV_SHOWS
import files.calculateNoOfColumns
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : BaseActivity<SearchActivityContract.SearchBaseView, SearchActivityPresenter>(),
        SearchActivityContract.SearchBaseView, MovieRecyclerAdapter.OnMovieSeriesAdapterListener,
        SearchView.OnQueryTextListener, DialogInterface.OnClickListener, GenreBottomSheetFragment.GenreBottomSheetListener {

    override fun createTagsLayout(tagsList: List<Genre>) {

    }

    private val mSearchList = mutableListOf<Movie?>()
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var movieAdapter: MovieRecyclerAdapter
    private var queryPage = 1
    private var totalResults = -1
    private var movieOrSeries = MOVIE
    private var searchQuery: String? = null
    private var selectedItemPosition = 0
    private val genreBottomSheet = GenreBottomSheetFragment()

    override var mPresenter = SearchActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initToolbar(toolbar, false, "")
        movieOrSeries = intent.getStringExtra(MOVIE_OR_SERIES)

        gridLayoutManager = GridLayoutManager(this, calculateNoOfColumns(this, 110))
        searchRecyclerView.layoutManager = gridLayoutManager

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (movieAdapter.getItemViewType(position) == movieAdapter.LOADER_VIEW)
                    gridLayoutManager.spanCount else 1
            }
        }
        movieSeriesSearchView.setOnQueryTextListener(this)
    }


    override fun setSearchRecyclerView(moviesList: List<Movie?>?, totalResult: Int) {

        totalResults = totalResult

        if (moviesList != null && moviesList.isNotEmpty()) {

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
            searchRecyclerView.visibility = View.VISIBLE
            noSearchLayout.visibility = View.GONE
        } else {
            searchRecyclerView.visibility = View.GONE
            noSearchLayout.visibility = View.VISIBLE
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sort_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_sort -> {
                genreBottomSheet.show(supportFragmentManager, "GenreBottomSheet")
            }
        }
        return true
    }

    override fun showSearchTypeDialog() {
        val dialogBuilder = AlertDialog.Builder(ContextThemeWrapper(this, R.style.AlertDialogTheme))
        dialogBuilder.setSingleChoiceItems(R.array.search_tags, selectedItemPosition, this)
        dialogBuilder.create().show()
    }

    override fun onClick(dialogInterface: DialogInterface?, item: Int) {
        /*when (item) {
            0 -> changeSearchPreference(TV_SHOWS)
            1 -> changeSearchPreference(TV_SHOWS)
            2 -> {
                mPresenter.getGenreFromDbAndCreateBottomSheet(movieOrSeries)
                //bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        dialogInterface?.dismiss()*/
    }

    private fun changeSearchPreference(searchPreference: String) {

    }

    /*override fun createTagsLayout(tagsList: List<Genre>) {

        *//*val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, displayMetrics).toInt()
        val px6dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6f, displayMetrics).toInt()
        var previousTagWidth = 0
        var previousTagHeight = 0
        var left = 0
        var top = 0

        val displayMetrices = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrices)
        val screenWidth = displayMetrices.widthPixels

        val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val tagLayout = RelativeLayout(this)
        tagLayout.setTag("TagLayout")
        tagLayout.setPadding(px, px * 2, px, px * 2)
        tagLayout.layoutParams = params

        tagsList.forEach {
            val tagView = TextView(this)
            tagView.setPadding(px * 2, px6dp, px * 2, px6dp)
            tagView.text = tagsList.get(tagsList.indexOf(it)).genreName
            tagView.tag = tagsList.get(tagsList.indexOf(it)).genreId
            tagView.background = ContextCompat.getDrawable(this, R.drawable.tag_background_drawable)
            tagView.setTextColor(ContextCompat.getColor(this, R.color.mediumGrey))
            tagView.typeface = ResourcesCompat.getFont(this, R.font.noto_sans_regular)
            tagView.textSize = 12f
            tagView.maxLines = 1
            tagView.gravity = Gravity.CENTER
            tagView.ellipsize = TextUtils.TruncateAt.END

            val tagViewParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            left = left + previousTagWidth

            if (((screenWidth - px * 2) - left) < tagView.measureTextViewWidth()) {
                top = top + previousTagHeight
                left = 0
            }

            tagViewParams.setMargins(left, top, 0, 0)
            tagView.layoutParams = tagViewParams
            tagLayout.addView(tagView)

            previousTagWidth = tagView.measureTextViewWidth() + px
            previousTagHeight = tagView.measureTextViewHeight() + px
        }

        if (view.genreTagsLayout.childCount > 0) {
            view.genreTagsLayout.removeAllViews()
        }
        view.genreTagsLayout.addView(tagLayout)*//*
    }*/

    /*fun TextView.measureTextViewWidth(): Int {
        this.measure(0, 0)
        return this.measuredWidth
    }

    fun TextView.measureTextViewHeight(): Int {
        this.measure(0, 0)
        return this.measuredHeight
    }
*/
    /*override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

        if (ev?.action == MotionEvent.ACTION_DOWN) {
            if (bottomSheetBehavior?.state == BottomSheetBehavior.STATE_EXPANDED) {

                val outRect = Rect()
                genreBottomSheet.getGlobalVisibleRect(outRect)

                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }*/

    override fun onMovieOrSeriesClicked(moviesOrSeries: String) {
        when (moviesOrSeries) {
            MOVIE -> {
                movieSeriesSearchView.queryHint = getString(R.string.search_movies)
                movieOrSeries = MOVIE
                selectedItemPosition = 0
            }
            TV_SHOWS -> {
                movieSeriesSearchView.queryHint = getString(R.string.search_tv_shows)
                movieOrSeries = TV_SHOWS
                selectedItemPosition = 1
            }
        }
        genreBottomSheet.createTagsLayout(moviesOrSeries)
    }

    override fun onGenreTagsClicked(tag: String) {

    }
}
