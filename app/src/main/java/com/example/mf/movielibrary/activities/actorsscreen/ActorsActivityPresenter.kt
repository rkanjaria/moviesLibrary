package com.example.mf.movielibrary.activities.actorsscreen

import android.content.Intent
import com.example.mf.movielibrary.activities.imgescreen.ImageActivity
import com.example.mf.movielibrary.activities.movieseriesscreen.MovieSeriesActivity
import com.example.mf.movielibrary.base.BasePresenterImpl
import com.example.mf.movielibrary.helpers.RetrofitHelper
import com.example.mf.movielibrary.models.moviemodel.Movie
import files.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ActorsActivityPresenter : BasePresenterImpl<ActorsActivityContract.ActorsView>(),
        ActorsActivityContract.ActorsPresenter {

    override fun callGetActorApi(actorId: Int) {
        mView?.showProgressLoading()
        RetrofitHelper.create().doGetActorApiCall(actorId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ actorResult ->

                    if (actorResult != null) {
                        mView?.hideProgressLoading()
                        mView?.setActorsData(actorResult)
                    }

                }, { error ->
                    mView?.hideProgressLoading()
                    error.printStackTrace()
                    mView?.showMessage(error.localizedMessage)
                })
    }

    override fun callGetActorsMoviesOrSeriesApi(actorId: Int, moviesOrSeries: String?) {

        if (moviesOrSeries != null) {

            //val movieOrSeriesQueryParam = if (moviesOrSeries.equals(MOVIE)) MOVIE_CREDITS else TV_CREDITS
            RetrofitHelper.create().doGetActorsMoviesAndSeriesApiCall(actorId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ movieResult ->
                        if (movieResult != null) {
                            mView?.setActorMoviesRecyclerview(movieResult.moviesList)
                        }
                    }, { error ->
                        error.printStackTrace()
                        mView?.showMessage(error.localizedMessage)
                    })
        }
    }

    override fun launchMovieSeriesActivity(movieModel: Movie?) {
        val movieSeriesIntent = Intent(mView?.getContext(), MovieSeriesActivity::class.java)
        movieSeriesIntent.putExtra(PARCELABLE_OBJECT, movieModel)
        movieSeriesIntent.putExtra(MOVIE_OR_SERIES, movieModel?.mediaType)
        mView?.getContext()?.startActivity(movieSeriesIntent)
    }

    override fun launchImagesActivity(actorId: Int) {
        val imagesIntent = Intent(mView?.getContext(), ImageActivity::class.java)
        imagesIntent.putExtra(ID, actorId)
        mView?.getContext()?.startActivity(imagesIntent)
    }
}