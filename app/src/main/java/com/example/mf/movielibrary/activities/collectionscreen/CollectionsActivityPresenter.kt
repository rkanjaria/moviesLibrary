package com.example.mf.movielibrary.activities.collectionscreen

import android.content.Intent
import com.example.mf.movielibrary.activities.movieseriesscreen.MovieSeriesActivity
import com.example.mf.movielibrary.base.BasePresenterImpl
import com.example.mf.movielibrary.classes.NoInternetConnectionException
import com.example.mf.movielibrary.helpers.RetrofitHelper
import com.example.mf.movielibrary.models.moviemodel.Movie
import files.MOVIE_OR_SERIES
import files.PARCELABLE_OBJECT
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by RK on 10-05-2018.
 */
class CollectionsActivityPresenter : BasePresenterImpl<CollectionsActivityContract.CollectionsView>(),
        CollectionsActivityContract.CollectionsPresenter {

    override fun callGetListApi(listId: Int) {
        try {
            mView?.showProgressBar()
            RetrofitHelper.create(mView).doGetListApiCall(listId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ collectionsResult ->

                        mView?.hideProgressBar()
                        mView?.setCollectionsRecyclerview(collectionsResult)
                    }, { error ->
                        mView?.hideProgressBar()
                        mView?.showMessage(error.localizedMessage)
                    })
        } catch (e: NoInternetConnectionException) {
            mView?.hideProgressBar()
        }

    }

    override fun launchMoviesOrSeriesActivity(movieModel: Movie?) {
        val movieSeriesIntent = Intent(mView?.getContext(), MovieSeriesActivity::class.java)
        movieSeriesIntent.putExtra(PARCELABLE_OBJECT, movieModel)
        movieSeriesIntent.putExtra(MOVIE_OR_SERIES, movieModel?.mediaType)
        mView?.getContext()?.startActivity(movieSeriesIntent)
    }
}
