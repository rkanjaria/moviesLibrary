package com.example.mf.movielibrary.fragments


import android.app.Activity.RESULT_OK
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
import com.example.mf.movielibrary.models.moviemodel.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.mf.movielibrary.files.*
import kotlinx.android.synthetic.main.fragment_favourites.*


/**
 * A simple [Fragment] subclass.
 */
class FavouritesFragment : Fragment(), MovieRecyclerAdapter.OnMovieSeriesAdapterListener {

    private lateinit var movieOrSeries: String
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        movieOrSeries = arguments.getString(MOVIE_OR_SERIES)
        return inflater!!.inflate(R.layout.fragment_favourites, container, false)
    }

    fun initFragment() {
        val moviesList = mutableListOf<Movie?>()
        context.database.favouriteMovieDao().getAllMoviesOrTvShows(movieOrSeries).forEach {
            val genres: List<Int> = Gson().fromJson(it.genreIds, object : TypeToken<List<Int>>() {}.type)
            val movie = Movie(id = it.id, voteAverage = it.voteAverage, posterPath = it.posterPath, mediaType = it.mediaType,
                    originalTitle = it.originalTitle, title = it.title, genreIds = genres, backDropPath = it.backDropPath,
                    overview = it.overview, releaseDate = it.releaseDate)
            moviesList.add(movie)
        }

        if (moviesList.isNotEmpty()) {
            favouritesRecyclerview.setHasFixedSize(true)
            favouritesRecyclerview.layoutManager = GridLayoutManager(context, 3)
            favouritesRecyclerview.adapter = MovieRecyclerAdapter(moviesList, this)
            runLayoutAnimation(favouritesRecyclerview, R.anim.grid_layout_animation_fall_down)
        } else {
            favouritesRecyclerview.visibility = View.GONE
            noFavavouritesImage.loadDrawable(R.drawable.no_fav)
            noFavouritesLayout.visibility = View.VISIBLE
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initFragment()
    }

    override fun onMovieOrSeriesClicked(movieModel: Movie?) {
        val movieSeriesIntent = Intent(context, MovieSeriesActivity::class.java)
        movieSeriesIntent.putExtra(PARCELABLE_OBJECT, movieModel)
        movieSeriesIntent.putExtra(MOVIE_OR_SERIES, movieOrSeries)
        startActivityForResult(movieSeriesIntent, RESULT_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_CODE && resultCode == RESULT_OK) {
            initFragment()
        }
    }
}
