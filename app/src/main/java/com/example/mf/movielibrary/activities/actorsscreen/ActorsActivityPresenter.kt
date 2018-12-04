package com.example.mf.movielibrary.activities.actorsscreen

import android.content.Intent
import android.net.Uri
import com.example.mf.movielibrary.activities.actorsmovieseriesscreen.ActorsMoviesSeriesActivity
import com.example.mf.movielibrary.activities.imgescreen.ImageActivity
import com.example.mf.movielibrary.activities.movieseriesscreen.MovieSeriesActivity
import com.example.mf.movielibrary.base.BasePresenterImpl
import com.example.mf.movielibrary.helpers.RetrofitHelper
import com.example.mf.movielibrary.models.actormodel.Actor
import com.example.mf.movielibrary.models.moviemodel.Movie
import com.example.mf.movielibrary.files.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ActorsActivityPresenter : BasePresenterImpl<ActorsActivityContract.ActorsView>(),
        ActorsActivityContract.ActorsPresenter {

    override fun launchActorsMoviesSeriesActivity(actorModel: Actor) {
        val actorMovieSeriesIntent = Intent(mView?.getContext(), ActorsMoviesSeriesActivity::class.java)
        actorMovieSeriesIntent.putExtra(INT_ID, actorModel.actorId)
        actorMovieSeriesIntent.putExtra(NAME, actorModel.actorName)
        actorMovieSeriesIntent.putExtra(BACKDROP_PATH, actorModel.profileImage)
        mView?.getContext()?.startActivity(actorMovieSeriesIntent)
    }

    override fun callGetActorIds(actorId: Int) {
        RetrofitHelper.create().doGetActorsIdsApiCall(actorId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ actorIdResult ->
                    mView?.setSocialMediaIcons(actorIdResult)

                }, { error ->
                    error.printStackTrace()
                    mView?.showMessage(error.localizedMessage)
                })
    }

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

    override fun callGetActorsMoviesOrSeriesApi(actorId: Int) {
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

    override fun openInstagramIntent(instagramId: String) {
        val instaIntent = Intent(Intent.ACTION_VIEW)
        val instagramUrl = "http://instagram.com/_u/" + instagramId
        try {
            if (mView?.getContext()?.packageManager?.getPackageInfo("com.instagram.android", 0) != null)
                instaIntent.`package` = "com.instagram.android"
        } catch (e: Exception) {
        }
        instaIntent.data = Uri.parse(instagramUrl)
        mView?.getContext()?.startActivity(instaIntent)
    }

    override fun openTwitterIntent(twitterId: String) {
        val twitterIntent = Intent(Intent.ACTION_VIEW)
        try {
            if (mView?.getContext()?.packageManager?.getPackageInfo("com.twitter.android", 0) != null) {
                twitterIntent.`package` = "com.twitter.android"
                twitterIntent.data = Uri.parse("twitter://user?user_id=" + twitterId)
            }
        } catch (e: Exception) {
        }
        twitterIntent.data = Uri.parse("https://twitter.com/" + twitterId)
        mView?.getContext()?.startActivity(twitterIntent)

    }
}

