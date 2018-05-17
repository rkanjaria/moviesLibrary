package com.example.mf.movielibrary.activities.movieseriesscreen

import android.content.Intent
import com.example.mf.movielibrary.activities.actorsscreen.ActorsActivity
import com.example.mf.movielibrary.activities.seasonscreen.SeasonActivity
import com.example.mf.movielibrary.activities.trailerscreen.TrailerActivity
import com.example.mf.movielibrary.base.BasePresenterImpl
import com.example.mf.movielibrary.helpers.RetrofitHelper
import com.example.mf.movielibrary.models.castmodel.Cast
import com.example.mf.movielibrary.models.castmodel.CastResult
import com.example.mf.movielibrary.models.moviemodel.Movie
import com.example.mf.movielibrary.models.moviemodel.MoviesResult
import com.example.mf.movielibrary.models.movieseriesdetailsmodel.MovieSeriesDetailsResult
import com.example.mf.movielibrary.models.movieseriesdetailsmodel.Season
import com.example.mf.movielibrary.models.videomodels.VideoTrailers
import files.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by RK on 04-01-2018.
 */
class MovieSeriesActivityPresenter : BasePresenterImpl<MovieSeriesActivityContract.MovieSeriesView>(),
        MovieSeriesActivityContract.MovieSeriesPresenter {


    override fun addOrRemoveFavourites(movieModel: Movie, movieOrSeries: String) {

        if (mView?.getContext()?.database?.doesAlreadyExists(movieModel.id)!!) {
            if (mView?.getContext()?.database?.removeMovie(movieModel.id)!!) {
                mView?.unhighlightFavoriteIcon()
            }
        } else {
            if (mView?.getContext()?.database?.insertMovie(movieModel, movieOrSeries) != -1L) {
                mView?.highlightFavoriteIcon()
            } else {
                mView?.showMessage("Favourite can't be added")
            }
        }
    }

    override fun launchTrailerActivity(videoTrailer: VideoTrailers?) {
        val trailerIntent = Intent(mView?.getContext(), TrailerActivity::class.java)
        trailerIntent.putExtra(PARCELABLE_OBJECT, videoTrailer)
        mView?.getContext()?.startActivity(trailerIntent)
    }

    override fun launchSeasonActivity(season: Season, movieOrSeriesId: Int) {
        val seasonIntent = Intent(mView?.getContext(), SeasonActivity::class.java)
        seasonIntent.putExtra(PARCELABLE_OBJECT, season)
        seasonIntent.putExtra(INT_ID, movieOrSeriesId)
        mView?.getContext()?.startActivity(seasonIntent)
    }

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

    override fun callGetTvDetailsApi(movieOrSeriesId: Int) {
        RetrofitHelper.create().doGetTvSeasonsApiCall(movieOrSeriesId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ movieSeriesDetailsResult ->

                    if (movieSeriesDetailsResult.seasonsList != null && movieSeriesDetailsResult.seasonsList.isNotEmpty()) {
                        mView?.setSeasonRecyclerview(movieSeriesDetailsResult.seasonsList
                                .filter {
                                    it.airDate != null && it.episodeCount != 0 && it.posterPath != null && it.seasonNumber != 0
                                }.sortedBy { it.seasonNumber })
                    }
                }, { error ->
                    error.printStackTrace()
                    mView?.showMessage(error.localizedMessage)
                })
    }

    override fun callGetMoviesOrSeriesTrailersApi(moviesOrSeries: String, movieOrSeriesId: Int) {
        RetrofitHelper.create().doGetMovieOrSeriesTrailers(moviesOrSeries, movieOrSeriesId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ videoResults ->

                    if (videoResults.results != null && videoResults.results.isNotEmpty()) {

                        mView?.showPlayTrailerLayout(videoResults.results.filter {
                            it.site.equals("YouTube") && it.type.equals("Trailer")
                        })
                    }

                }, { error ->
                    error.printStackTrace()
                    mView?.showMessage(error.localizedMessage)
                })
    }

    override fun callGetMoviesOrSeriesReviewsApi(moviesOrSeries: String, movieOrSeriesId: Int) {
        RetrofitHelper.create().doGetMovieOrSeriesReviews(moviesOrSeries, movieOrSeriesId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ reviewResults ->

                    if (reviewResults.reviewList != null && reviewResults.reviewList.isNotEmpty()) {

                        mView?.setReviewRecyclerview(reviewResults.reviewList)
                    }

                }, { error ->
                    error.printStackTrace()
                    mView?.showMessage(error.localizedMessage)
                })
    }
}