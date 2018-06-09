package com.example.mf.movielibrary.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.activities.movieseriesscreen.MovieSeriesActivity
import com.example.mf.movielibrary.adapters.MovieRecyclerAdapter
import com.example.mf.movielibrary.interfaces.ActorsMoviesSeriesListener
import com.example.mf.movielibrary.models.moviemodel.Movie
import files.*
import kotlinx.android.synthetic.main.fragment_actors_movie_series.*

class ActorsTvshowsFragment : Fragment(), MovieRecyclerAdapter.OnMovieSeriesAdapterListener {

    private var mListener: ActorsMoviesSeriesListener? = null
    private var actorId = -1

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_actors_movie_series, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actorId = activity.intent.getIntExtra(INT_ID, -1)
        mListener?.onCallActorsCreditsApi(actorId, TV_CREDITS)
    }

    fun setMovieOrSeriesRecyclerView(moviesList: List<Movie?>?) {
        if (moviesList != null) {
            movieSeriesRecyclerView.setHasFixedSize(true)
            movieSeriesRecyclerView.layoutManager = GridLayoutManager(context, 3)
            movieSeriesRecyclerView?.adapter = MovieRecyclerAdapter(moviesList, this)
        }
    }

    fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ActorsMoviesSeriesListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + "must implement ActorsMoviesSeriesListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onMovieOrSeriesClicked(movieModel: Movie?) {
        val movieSeriesIntent = Intent(context, MovieSeriesActivity::class.java)
        movieSeriesIntent.putExtra(PARCELABLE_OBJECT, movieModel)
        movieSeriesIntent.putExtra(MOVIE_OR_SERIES, TV_SHOWS)
        context.startActivity(movieSeriesIntent)
    }
}
