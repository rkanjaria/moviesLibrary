package com.example.mf.movielibrary.activities.actorsmovieseriesscreen

import com.example.mf.movielibrary.base.BasePresenterImpl
import com.example.mf.movielibrary.helpers.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ActorsMoviesSeriesActivityPresenter : BasePresenterImpl<ActorsMoviesSeriesActivityContract.ActorsMoviesSeriesView>(),
        ActorsMoviesSeriesActivityContract.ActorsMoviesSeriesPresenter {

    override fun callActorsCreditsApi(actorId: Int, moviesOrSeriesCredits: String) {

        mView?.showProgressBar(moviesOrSeriesCredits)
        RetrofitHelper.create().doGetActorsCreditsApiCall(actorId, moviesOrSeriesCredits)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ movieResult ->
                    mView?.hideProgressBar(moviesOrSeriesCredits)
                    mView?.setMovieOrSeriesRecyclerView(movieResult?.moviesList, movieResult?.totalResults!!, moviesOrSeriesCredits)
                }, { error ->
                    error.printStackTrace()
                    mView?.showMessage(error.localizedMessage)
                })
    }

    override fun requestViewPager() {
        mView?.setViewPager()
    }
}