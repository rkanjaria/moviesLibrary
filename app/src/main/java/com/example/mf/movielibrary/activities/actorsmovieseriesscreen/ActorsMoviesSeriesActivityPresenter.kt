package com.example.mf.movielibrary.activities.actorsmovieseriesscreen

import com.example.mf.movielibrary.base.BasePresenterImpl
import com.example.mf.movielibrary.helpers.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ActorsMoviesSeriesActivityPresenter : BasePresenterImpl<ActorsMoviesSeriesActivityContract.ActorsMoviesSeriesView>(),
        ActorsMoviesSeriesActivityContract.ActorsMoviesSeriesPresenter {

    override fun callActorsCreditsApi(actorId: Int, moviesOrSeriesCredits: String, page: Int) {
        if (page == 1) {
            mView?.showProgressBar()
        }

        RetrofitHelper.create().doGetActorsCreditsApiCall(actorId, moviesOrSeriesCredits, page = page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ movieResult ->

                    if (page == 1) {
                        mView?.hideProgressBar()
                    }
                    mView?.setMovieOrSeriesRecyclerView(movieResult?.moviesList, movieResult?.totalResults!!)
                }, { error ->
                    error.printStackTrace()
                    mView?.showMessage(error.localizedMessage)
                })
    }

    override fun requestViewPager() {
        mView?.setViewPager()
    }
}