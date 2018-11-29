package com.example.mf.movielibrary.activities.searchscreen

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.*
import android.view.ContextThemeWrapper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.ActorsAdapter
import com.example.mf.movielibrary.adapters.GenreAdapter
import com.example.mf.movielibrary.adapters.MovieRecyclerAdapter
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.database.entities.Genre
import com.example.mf.movielibrary.models.actormodel.Actor
import com.example.mf.movielibrary.models.moviemodel.Movie
import files.*
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity<SearchActivityContract.SearchBaseView, SearchActivityPresenter>(),
        SearchActivityContract.SearchBaseView, MovieRecyclerAdapter.OnMovieSeriesAdapterListener,
        SearchView.OnQueryTextListener, DialogInterface.OnClickListener, GenreAdapter.GenreAdapterListener,
        ActorsAdapter.ActorsAdapterListener {

    private val mSearchList = mutableListOf<Movie?>()
    private val mActorsList = mutableListOf<Actor?>()
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var actorsGridLayoutManager: GridLayoutManager
    private lateinit var movieAdapter: MovieRecyclerAdapter
    private lateinit var actorsAdapter: ActorsAdapter
    private var queryPage = 1
    private var totalResults = -1
    private var movieSeriesOrActors = MOVIE
    private var searchQuery: String? = null
    private var selectedItemPosition = 0
    private var isGenre = false
    private var currentGenreId = -1

    override var mPresenter = SearchActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initToolbar(toolbar, false, "")
        movieSeriesOrActors = intent.getStringExtra(MOVIE_OR_SERIES)
        changeSearchPreference(movieSeriesOrActors)

        gridLayoutManager = GridLayoutManager(this, 3)
        searchRecyclerView.layoutManager = gridLayoutManager
        (searchRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false // disabling default item animatior of recyclerview

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (movieAdapter.getItemViewType(position) == movieAdapter.LOADER_VIEW)
                    gridLayoutManager.spanCount else 1
            }
        }

        actorsGridLayoutManager = GridLayoutManager(this, 3)
        actorsRecyclerView.layoutManager = actorsGridLayoutManager
        (actorsRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false // disabling default item animatior of recyclerview
        actorsGridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (actorsAdapter.getItemViewType(position) == actorsAdapter.LOADER_VIEW)
                    actorsGridLayoutManager.spanCount else 1
            }
        }

        val searchText = movieSeriesSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text) as TextView
        searchText.typeface = ResourcesCompat.getFont(this, R.font.noto_sans_regular)
        searchText.textSize = 14f
        movieSeriesSearchView.setOnQueryTextListener(this)
    }

    // Setting genreRecyclerview
    override fun setGenreRecylerview(genreList: List<Genre>) {
        genreRecyclerView.setHasFixedSize(true)
        genreRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        genreRecyclerView.adapter = GenreAdapter(genreList, this)
        runLayoutAnimation(genreRecyclerView, R.anim.layout_animation_fall_down)
    }

    // Click genreRecyclerviewItem
    override fun onGenreSelected(genreId: Int) {
        hideNoSearchLayout(null)
        movieSeriesSearchView.clearFocus()
        currentGenreId = genreId
        isGenre = true
        clearAllSearchTerms()
        clearSearchQuery()
        queryPage = 1
        mPresenter.callSearchByGenreApi(movieSeriesOrActors, queryPage, currentGenreId)
    }


    // Setting MovieOrTvShowsRecyclerView
    override fun setSearchRecyclerView(moviesList: List<Movie?>?, totalResult: Int) {
        totalResults = totalResult
        if (moviesList != null && moviesList.isNotEmpty()) {

            if (mSearchList.isEmpty()) {
                // for first time when data loads, set the adapter of recyclerview this helps in solving pagination issue since new adapter is set no refreshed
                mSearchList.addAll(moviesList)
                movieAdapter = MovieRecyclerAdapter(mSearchList, this)
                searchRecyclerView.adapter = movieAdapter
                runLayoutAnimation(searchRecyclerView, R.anim.grid_layout_animation_fall_down)

            } else {
                // for the second time remove the loader view and add the data and refresh the recyclerview
                mSearchList.removeAt(mSearchList.size - 1)
                val lastPosition = mSearchList.size
                if (moviesList.isNotEmpty()) {
                    mSearchList.addAll(moviesList)
                }
                movieAdapter.refreshAdapter(lastPosition)
            }
            hideNoSearchLayout(searchRecyclerView)
        } else {
            showNoSearchLayout(searchRecyclerView)
        }
    }

    // Loading more for MovieOrTvShowsRecyclerView
    override fun loadMore() {
        searchRecyclerView.post { loadMoreData() }
    }

    private fun loadMoreData() {
        queryPage++
        if (mSearchList.size < totalResults) {
            mSearchList.add(null)
            movieAdapter.notifyItemInserted(mSearchList.size - 1)

            if (isGenre) {
                mPresenter.callSearchByGenreApi(movieSeriesOrActors, queryPage, currentGenreId)
            } else {
                mPresenter.callSearchMovieOrSeriesByName(movieSeriesOrActors, searchQuery, queryPage)
            }
        }
    }

    // Click MovieOrTvShowsRecyclerViewItem
    override fun onMovieOrSeriesClicked(movieModel: Movie?) {
        mPresenter.launchMovieSeriesActivity(movieModel, movieSeriesOrActors)
    }


    // Setting ActorsRecyclerView
    override fun setActorsSearchRecyclerView(actorsList: List<Actor>?, totalResult: Int) {
        totalResults = totalResult

        if (actorsList != null && actorsList.isNotEmpty()) {

            if (mActorsList.isEmpty()) {
                // for first time when data loads, set the adapter of recyclerview this helps in solving pagination issue since new adapter is set no refreshed
                mActorsList.addAll(actorsList)
                actorsAdapter = ActorsAdapter(mActorsList, this)
                actorsRecyclerView.adapter = actorsAdapter
                runLayoutAnimation(actorsRecyclerView, R.anim.grid_layout_animation_fall_down)

            } else {
                // for the second time remove the loader view and add the data and refresh the recyclerview
                mActorsList.removeAt(mActorsList.size - 1)
                val lastPosition = mActorsList.size
                if (actorsList.isNotEmpty()) {
                    mActorsList.addAll(actorsList)
                }
                actorsAdapter.refreshAdapter(lastPosition)
            }
            hideNoSearchLayout(actorsRecyclerView)
        } else {
            showNoSearchLayout(actorsRecyclerView)
        }
    }

    // Loading more for ActorsRecyclerView
    override fun loadMoreActors() {
        actorsRecyclerView.post {
            loadActors()
        }
    }

    private fun loadActors() {
        queryPage++
        if (mActorsList.size < totalResults) {
            mActorsList.add(null)
            actorsAdapter.notifyItemInserted(mActorsList.size - 1)
            mPresenter.callSearchPeopleApi(searchQuery, queryPage)
        }
    }

    // Click ActorsRecyclerViewItem
    override fun onActorClicked(actorModel: Actor?) {
        mPresenter.launchActorsActivity(actorModel)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        hideNoSearchLayout(null)
        movieSeriesSearchView.clearFocus()
        searchQuery = query

        if (movieSeriesOrActors.equals(ACTORS)) {
            clearListAndMakeActorsApiCallAgain()
        } else {
            clearListAndMakeApiCallAgain()
        }
        return true
    }

    override fun onQueryTextChange(newText: String?) = false

    private fun clearListAndMakeActorsApiCallAgain() {
        queryPage = 1
        clearAllSearchTerms()
        mPresenter.callSearchPeopleApi(searchQuery, queryPage)
    }

    private fun clearListAndMakeApiCallAgain() {
        isGenre = false
        (genreRecyclerView.adapter as GenreAdapter).removeSeletedGenre()
        queryPage = 1
        clearAllSearchTerms()
        mPresenter.callSearchMovieOrSeriesByName(movieSeriesOrActors, searchQuery, queryPage)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.action_sort -> mPresenter.requestSearchTypeDialog()
        }
        return true
    }

    override fun showSearchTypeDialog() {
        val dialogBuilder = AlertDialog.Builder(ContextThemeWrapper(this, R.style.AlertDialogTheme))
        dialogBuilder.setSingleChoiceItems(R.array.search_tags, selectedItemPosition, this)
        dialogBuilder.create().show()
    }

    override fun onClick(dialogInterface: DialogInterface?, item: Int) {
        when (item) {
            0 -> changeSearchPreference(MOVIE)
            1 -> changeSearchPreference(TV_SHOWS)
            2 -> changeSearchPreference(ACTORS)
        }
        dialogInterface?.dismiss()
    }

    private fun changeSearchPreference(preferenceString: String) {
        hideNoSearchLayout(null)
        when (preferenceString) {
            MOVIE -> {
                movieSeriesOrActors = MOVIE
                clearAllSearchTerms()
                movieSeriesSearchView.queryHint = getString(R.string.search_movies)
                selectedItemPosition = 0
                mPresenter.getGenreFromDb(preferenceString)
            }
            TV_SHOWS -> {
                movieSeriesOrActors = TV_SHOWS
                clearAllSearchTerms()
                movieSeriesSearchView.queryHint = getString(R.string.search_tv_shows)
                selectedItemPosition = 1
                mPresenter.getGenreFromDb(preferenceString)
            }
            ACTORS -> {
                movieSeriesOrActors = ACTORS
                clearAllSearchTerms()
                movieSeriesSearchView.queryHint = getString(R.string.search_celebrities)
                selectedItemPosition = 2
            }
        }
    }

    private fun clearAllSearchTerms() {
        if (movieSeriesOrActors.equals(ACTORS)) {
            genreRecyclerView.visibility = View.GONE
            searchRecyclerView.visibility = View.GONE
            actorsRecyclerView.visibility = View.VISIBLE
        } else {
            genreRecyclerView.visibility = View.VISIBLE
            searchRecyclerView.visibility = View.VISIBLE
            actorsRecyclerView.visibility = View.GONE
        }

        mSearchList.clear()
        searchRecyclerView.adapter = null
        mActorsList.clear()
        actorsRecyclerView.adapter = null
    }

    private fun clearSearchQuery() {
        movieSeriesSearchView.setQuery("", false)
        movieSeriesSearchView.isIconified = true
        movieSeriesSearchView.clearFocus()
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    private fun showNoSearchLayout(recyclerViewToHide: RecyclerView?) {
        noSearchImage.loadDrawable(R.drawable.no_search)
        recyclerViewToHide?.visibility = View.GONE
        noSearchLayout.visibility = View.VISIBLE
    }

    private fun hideNoSearchLayout(recyclerViewToShow: RecyclerView?) {
        recyclerViewToShow?.visibility = View.VISIBLE
        if (noSearchLayout.visibility == View.VISIBLE)
            noSearchLayout.visibility = View.GONE
    }
}
