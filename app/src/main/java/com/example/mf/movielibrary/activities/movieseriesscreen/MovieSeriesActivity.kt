package com.example.mf.movielibrary.activities.movieseriesscreen

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.classes.getYearFromDate
import com.example.mf.movielibrary.models.Movie
import files.*
import kotlinx.android.synthetic.main.activity_movie_series.*

class MovieSeriesActivity : BaseActivity<MovieSeriesActivityContract.MovieSeriesView, MovieSeriesActivityPresenter>(),
        MovieSeriesActivityContract.MovieSeriesView {

    override var mPresenter: MovieSeriesActivityPresenter = MovieSeriesActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_series)

        val movieModel = intent.getParcelableExtra<Movie>(PARCELABLE_OBJECT) as Movie
        initToolbar(toolbar as Toolbar, true, "")

        if (movieModel.backDroppath != null) {
            backdropImage.loadImage(backdropUrl + movieModel.backDroppath, placeholder = R.color.darkGrey)
        }

        if (movieModel.posterPath != null) {
            posterImage.loadImage(photoUrl + movieModel.posterPath, placeholder = R.color.darkGrey)
        }
        movieTitle.text = movieModel.title
        movieYear.text = "Released in " + getYearFromDate(movieModel.releaseDate)
        movieRating.text = movieModel.voteAverage.toString()
        movieOverview.text = movieModel.overview
    }
}
