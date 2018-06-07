package com.example.mf.movielibrary.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.MovieRecyclerAdapter
import com.example.mf.movielibrary.models.moviemodel.Movie
import files.*
import kotlinx.android.synthetic.main.fragment_actors_movie_series.*
import kotlinx.android.synthetic.main.fragment_actors_movie_series.view.*

class ActorsMovieSeriesFragment : Fragment(), MovieRecyclerAdapter.OnMovieSeriesAdapterListener {

    private lateinit var movieOrSeries: String
    private var mListener: ActorsMoviesSeriesListener? = null
    private var page = 1
    private val mMoviesList = mutableListOf<Movie?>()
    private lateinit var movieAdapter: MovieRecyclerAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private var totalResultsCount = -1
    private var actorId = -1
    private var movieSeriesRecyclerView: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        movieOrSeries = arguments.getString(MOVIE_OR_SERIES)
        val rootView = inflater?.inflate(R.layout.fragment_actors_movie_series, container, false)
        movieSeriesRecyclerView = rootView?.findViewById(R.id.movieSeriesRecyclerView) as RecyclerView
        return rootView
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actorId = activity.intent.getIntExtra(INT_ID, -1)

        when (movieOrSeries) {
            MOVIE -> mListener?.onCallActorsCreditsApi(actorId, MOVIE_CREDITS, page)
            TV_SHOWS -> mListener?.onCallActorsCreditsApi(actorId, TV_CREDITS, page)
        }

        gridLayoutManager = GridLayoutManager(context, 3)
        movieSeriesRecyclerView?.layoutManager = gridLayoutManager

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (movieAdapter.getItemViewType(position) == movieAdapter.LOADER_VIEW)
                    gridLayoutManager.spanCount else 1
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ActorsMoviesSeriesListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + "must implement ActorsMoviesSeriesListener")
        }
    }

    fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    fun setMovieOrSeriesRecyclerView(moviesList: List<Movie?>?, totalResults: Int) {
        totalResultsCount = totalResults

        if (moviesList != null) {

            if (mMoviesList.isEmpty()) {
                // for first time when data loads, add items to list and refresh the recyclerview
                mMoviesList.addAll(moviesList)
                movieAdapter = MovieRecyclerAdapter(mMoviesList, this)
                movieSeriesRecyclerView?.adapter = movieAdapter
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

    override fun loadMore() {
        movieSeriesRecyclerView?.post { loadMoreData() }
    }

    private fun loadMoreData() {
        page++
        if (mMoviesList.size < totalResultsCount) {
            mMoviesList.add(null)
            movieAdapter.notifyItemInserted(mMoviesList.size - 1)
            mListener?.onCallActorsCreditsApi(actorId, TV_CREDITS, page)
        }
    }

    interface ActorsMoviesSeriesListener {
        fun onCallActorsCreditsApi(actorId: Int, moviesOrSeriesCredits: String, page: Int)
    }

}
