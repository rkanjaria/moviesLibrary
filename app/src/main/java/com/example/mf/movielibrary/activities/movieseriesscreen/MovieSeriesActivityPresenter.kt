package com.example.mf.movielibrary.activities.movieseriesscreen

import android.content.Intent
import com.example.mf.movielibrary.activities.actorsscreen.ActorsActivity
import com.example.mf.movielibrary.base.BasePresenterImpl
import com.example.mf.movielibrary.helpers.RetrofitHelper
import com.example.mf.movielibrary.models.castmodel.Cast
import com.example.mf.movielibrary.models.castmodel.CastResult
import com.example.mf.movielibrary.models.moviemodel.Movie
import com.example.mf.movielibrary.models.moviemodel.MoviesResult
import files.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by MF on 04-01-2018.
 */
class MovieSeriesActivityPresenter : BasePresenterImpl<MovieSeriesActivityContract.MovieSeriesView>(),
        MovieSeriesActivityContract.MovieSeriesPresenter {

    override fun launchMovieSeriesActivity(movieModel: Movie?, movieOrSeries: String?) {
        val movieSeriesIntent = Intent(mView?.getContext(), MovieSeriesActivity::class.java)
        movieSeriesIntent.putExtra(PARCELABLE_OBJECT, movieModel)
        movieSeriesIntent.putExtra(MOVIE_OR_SERIES, movieOrSeries)
        mView?.getContext()?.startActivity(movieSeriesIntent)
    }

    override fun callGetSimilarMovieOrSeriesApi(moviesOrSeries: String?, movieOrSeriesId: Int) {
        if (moviesOrSeries != null) {
            RetrofitHelper.create().doGetSimilarMoviesOrSeriesApiCall(moviesOrSeries, movieOrSeriesId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ movieResult: MoviesResult? ->

                        if (movieResult?.moviesList != null && movieResult.moviesList.isNotEmpty()) {
                            mView?.setSimilarMoviesRecyclerview(movieResult.moviesList, movieResult.page)
                        }
                    }, { error ->
                        error.printStackTrace()
                        mView?.showMessage(error.localizedMessage)
                    })
        }
    }


    override fun launchActorActivity(castModel: Cast, movieOrSeries: String?) {
        val actorIntent = Intent(mView?.getContext(), ActorsActivity::class.java)
        actorIntent.putExtra(INT_ID, castModel.id)
        actorIntent.putExtra(NAME, castModel.name)
        actorIntent.putExtra(MOVIE_OR_SERIES, movieOrSeries)
        mView?.lauchchActivity(actorIntent)
    }

    override fun getMovieGenres(genreIds: List<Int>?): String {
        val genre = StringBuilder()
        genreIds?.forEach {
            genre.append(mView?.getContext()?.database?.getGenreBasedOnGenreId(it) + ", ")
        }
        if (genre.isEmpty()) return "" else return genre.toString().substring(0, genre.length - 2)
    }

    override fun callgetMovieOrSeriesCastApi(movieOrSeries: String?, movieId: Int) {

        if (movieOrSeries != null) {
            RetrofitHelper.create().doGetMovieOrSeriesCastApiCall(movieOrSeries, movieId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ castResult: CastResult? ->

                        if (castResult?.castList != null && castResult.castList.isNotEmpty()) {
                            mView?.setCastRecyclerview(castResult.castList.sortedBy { it.order })
                        }
                    }, { error ->
                        error.printStackTrace()
                        mView?.showMessage(error.localizedMessage)
                    })
        }

    }
}