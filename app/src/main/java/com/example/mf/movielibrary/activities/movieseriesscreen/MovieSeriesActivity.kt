package com.example.mf.movielibrary.activities.movieseriesscreen

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.example.mf.movielibrary.R
import com.example.mf.movielibrary.base.BaseActivity
import com.example.mf.movielibrary.models.Movie
import files.PARCELABLE_OBJECT
import files.backdropUrl
import files.loadImage
import files.photoUrl
import kotlinx.android.synthetic.main.activity_movie_series.*

class MovieSeriesActivity : BaseActivity<MovieSeriesActivityContract.MovieSeriesView, MovieSeriesActivityPresenter>(),
        MovieSeriesActivityContract.MovieSeriesView {

    override var mPresenter: MovieSeriesActivityPresenter = MovieSeriesActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_series)

        val movieModel = intent.getParcelableExtra<Movie>(PARCELABLE_OBJECT) as Movie
        initToolbar(toolbar as Toolbar, true, movieModel.title.toString())

        if (movieModel.backDroppath != null) {
            backdropImage.loadImage(backdropUrl + movieModel.backDroppath, false)
        }

        synopsis.text = movieModel.overview
    }
}
