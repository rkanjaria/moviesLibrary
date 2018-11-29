package com.example.mf.movielibrary.fragments


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.MovieRecyclerAdapter
import com.example.mf.movielibrary.models.moviemodel.Movie
import files.*
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment(), MovieRecyclerAdapter.OnMovieSeriesAdapterListener, DialogInterface.OnClickListener {

    private var mListener: HomeFragmentListener? = null
    private val mMoviesList = mutableListOf<Movie?>()

    private lateinit var movieAdapter: MovieRecyclerAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private var page = 1
    private var totalResultsCount = -1
    private var movieOrSeries = MOVIE
    private var selectedTypePostion = 0
    private var type = POPULAR

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        callMoviesOrseriesApi()
        gridLayoutManager = GridLayoutManager(context, 3)
        movieRecyclerView.layoutManager = gridLayoutManager
        (movieRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false // disabling default item animatior of recyclerview

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (movieAdapter.getItemViewType(position) == movieAdapter.LOADER_VIEW)
                    gridLayoutManager.spanCount else 1
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is HomeFragmentListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement HomeFragmentListener")
        }
    }

    fun showProgressBar() {
        progressBar?.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        progressBar?.visibility = View.GONE
    }

    fun setRecyclerView(moviesList: List<Movie?>?, totalResults: Int) {
        totalResultsCount = totalResults

        if (moviesList != null) {

            if (mMoviesList.isEmpty()) {
                // for first time when data loads, add items to list and refresh the recyclerview
                mMoviesList.addAll(moviesList)
                movieAdapter = MovieRecyclerAdapter(mMoviesList, this)
                movieRecyclerView.adapter = movieAdapter
                runLayoutAnimation(movieRecyclerView, R.anim.grid_layout_animation_fall_down)
            } else {
                // for the second time remove the loader view and add the data and refresh the recyclerview
                mMoviesList.removeAt(mMoviesList.size - 1)
                val lastPosition = mMoviesList.size
                if (moviesList.isNotEmpty()) {
                    mMoviesList.addAll(moviesList)
                }
                movieAdapter.refreshAdapter(lastPosition)
            }
        }
    }

    override fun onMovieOrSeriesClicked(movieModel: Movie?) {
        mListener?.onMovieOrSeriesClicked(movieModel, movieOrSeries)
    }

    private fun clearListAndMakeApiCallAgain() {
        mMoviesList.clear()
        movieRecyclerView.removeAllViews()
        page = 1
        callMoviesOrseriesApi()
    }

    override fun loadMore() {
        movieRecyclerView.post { loadMoreData() }
    }

    private fun loadMoreData() {
        page++
        if (mMoviesList.size < totalResultsCount) {
            mMoviesList.add(null)
            movieAdapter.notifyItemInserted(mMoviesList.size - 1)
            callMoviesOrseriesApi()
        }
    }

    fun showMovieOrSeriesTypeDialog() {
        val arrayType = if (movieOrSeries == MOVIE) R.array.movie_type_array else R.array.tv_type_array
        val dialogBuilder = AlertDialog.Builder(ContextThemeWrapper(context, R.style.AlertDialogTheme))
        dialogBuilder.setSingleChoiceItems(arrayType, selectedTypePostion, this)
        dialogBuilder.create().show()
    }

    override fun onClick(dialogInterface: DialogInterface?, item: Int) {
        if (movieOrSeries == MOVIE) {

            when (item) {
                0 -> type = POPULAR
                1 -> type = TOP_RATED
                2 -> type = UPCOMING
                3 -> type = NOW_PLAYING
            }
        } else {
            when (item) {
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

    fun callMoviesOrSeriesApi(movieOrSeries: String) {
        this.movieOrSeries = movieOrSeries
        type = POPULAR
        selectedTypePostion = 0
        clearListAndMakeApiCallAgain()
    }

    fun onClickSearch() {
        mListener?.onClickSearchActivity(movieOrSeries)
    }

    fun callMoviesOrseriesApi() {
        mListener?.callGetMoviesApi(movieOrSeries, type, page)
    }

    interface HomeFragmentListener {
        fun callGetMoviesApi(movieOrSeries: String, type: String, page: Int)
        fun onMovieOrSeriesClicked(movieModel: Movie?, movieOrSeries: String)
        fun onClickSearchActivity(movieOrSeries: String)
    }
}
