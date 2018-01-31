package com.example.mf.movielibrary.activities.movieseriesscreen

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.adapters.CastRecyclerAdapter
import com.example.mf.movielibrary.adapters.MovieRecyclerAdapter
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.models.castmodel.Cast
import com.example.mf.movielibrary.models.moviemodel.Movie
import files.*
import kotlinx.android.synthetic.main.activity_movie_series.*

class MovieSeriesActivity : BaseActivity<MovieSeriesActivityContract.MovieSeriesView, MovieSeriesActivityPresenter>(),
        MovieSeriesActivityContract.MovieSeriesView, CastRecyclerAdapter.OnCastAdapterListener, MovieRecyclerAdapter.OnMovieSeriesAdapterListener {



    override var mPresenter = MovieSeriesActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_series)

        val movieModel = intent.getParcelableExtra(PARCELABLE_OBJECT) as Movie
        initToolbar(toolbar as Toolbar, true, "")

        if (movieModel.backDroppath != null) {
            backdropImage.loadImage(backdropUrl + movieModel.backDroppath, placeholder = R.color.darkGrey)
        }

        if (movieModel.posterPath != null) {
            posterImage.loadImage(photoUrl + movieModel.posterPath, placeholder = R.color.darkGrey)
        }
        movieTitle.text = movieModel.title
        movieYear.text = getDateWithCustomFormat(movieModel.releaseDate)
        movieRating.text = movieModel.voteAverage.toString()
        movieOverview.text = movieModel.overview
        movieGenre.text =  mPresenter.getMovieGenres(movieModel.genreIds)

        mPresenter.callgetMovieOrSeriesCastApi(intent.getStringExtra(MOVIE_OR_SERIES), movieModel.id)
        mPresenter.callgetSimilarMovieOrSeriesApi(intent.getStringExtra(MOVIE_OR_SERIES), movieModel.id)
    }

    override fun setCastRecyclerview(castList: List<Cast>) {

        castRecyclerview.setHasFixedSize(true)
        castRecyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        castRecyclerview.adapter = CastRecyclerAdapter(castList, this)
        castRecyclerview.visibility = View.VISIBLE
        castTitle.visibility = View.VISIBLE
    }

    override fun onCastClicked(castModel : Cast) {
        mPresenter.launchActorActivity(castModel)
    }

    override fun setSimilarMoviesRecyclerview(moviesList: List<Movie?>) {
        similarMoviesRecyclerview.setHasFixedSize(true)
        similarMoviesRecyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        similarMoviesRecyclerview.adapter = MovieRecyclerAdapter(moviesList, this)
        similarMoviesRecyclerview.visibility = View.VISIBLE
        similarMoviesTitle.visibility = View.VISIBLE
    }

    override fun loadMore() {
        similarMoviesTitle.post({ loadMoreData() })
    }

    private fun loadMoreData() {
            /*page++

            if (page <= totalpages && page <=1000) {

                mMoviesList.add(null)
                movieAdapter.notifyItemInserted(mMoviesList.size - 1)
                mPresenter.callGetMoviesApi(movieOrSeries, type, page)
            }*/
    }

    override fun onMovieOrSeriesClicked(movieModel: Movie?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
