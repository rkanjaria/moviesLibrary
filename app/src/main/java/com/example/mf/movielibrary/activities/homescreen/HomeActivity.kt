package com.example.mf.movielibrary.activities.homescreen

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.Toolbar
import android.view.ContextThemeWrapper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.MovieRecyclerAdapter
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.models.moviemodel.Movie
import files.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.spinner_toolbar.*


/**
 * Created by MF on 28-11-2017.
 */
class HomeActivity : BaseActivity<HomeActivityContract.HomeView, HomeActivityPresenter>(),
        HomeActivityContract.HomeView, MovieRecyclerAdapter.OnMovieSeriesAdapterListener, AdapterView.OnItemSelectedListener,
        DialogInterface.OnClickListener {

    private val mMoviesList = mutableListOf<Movie?>()

    private lateinit var movieAdapter: MovieRecyclerAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private var page = 1
    private var totalpages = - 1
    private var movieOrSeries = MOVIE
    private var selectedTypePostion = 0

    private var type = POPULAR
    override var mPresenter = HomeActivityPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar as Toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.toolbar_array, R.layout.spinner_dropdown)
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown)
        toolbarSpinner.adapter = spinnerAdapter
        toolbarSpinner.onItemSelectedListener = this

        gridLayoutManager = GridLayoutManager(this, calculateNoOfColumns(this, 110))
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
        type = POPULAR
        selectedTypePostion = 0
        clearListAndMakeApiCallAgain()
    }

    override fun onMovieOrSeriesClicked(movieModel: Movie?) {
        mPresenter.launchMovieSeriesActivity(movieModel, movieOrSeries)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.home_screen_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.action_sort -> mPresenter.requestMovieOrSeriesTypeDialog();
        }
        return true
    }

    override fun showMovieOrSeriesTypeDialog() {

        val arrayType = if(movieOrSeries == MOVIE)  R.array.movie_type_array else R.array.tv_type_array
        val dialogBuilder = AlertDialog.Builder(ContextThemeWrapper(this, R.style.AlertDialogTheme))
        dialogBuilder.setSingleChoiceItems(arrayType, selectedTypePostion,this)
        dialogBuilder.create().show()
    }

    override fun onClick(dialogInterface: DialogInterface?, item: Int) {
        if(movieOrSeries == MOVIE){

            when(item){
                0 -> type = POPULAR
                1 -> type = TOP_RATED
                2 -> type = UPCOMING
                3 -> type = NOW_PLAYING
            }
        }else{
            when(item){
                0 -> type = POPULAR
                1 -> type = TOP_RATED
                2 -> type = ON_AIR
                3 -> type = AIRING_TODAY
            }
        }

        selectedTypePostion = item
        dialogInterface?.dismiss()
        clearListAndMakeApiCallAgain()
    }


    private fun clearListAndMakeApiCallAgain() {
        mMoviesList.clear()
        movieRecyclerView.removeAllViews()
        page = 1
        mPresenter.callGetMoviesApi(movieOrSeries, type, page)
    }
}
