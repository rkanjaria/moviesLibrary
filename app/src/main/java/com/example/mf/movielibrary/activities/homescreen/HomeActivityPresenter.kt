package com.example.mf.movielibrary.activities.homescreen

import android.util.Log
import com.example.mf.movielibrary.base.BasePresenterImpl
import com.example.mf.movielibrary.helpers.RetrofitHelper
import com.example.mf.movielibrary.models.MoviesResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by MF on 28-11-2017.
 */
class HomeActivityPresenter : BasePresenterImpl<HomeActivityContract.HomeView>(), HomeActivityContract.HomePresenter {

    override fun callGetMoviesApi(movieOrSeries : String, requestType : String, page : Int) {

        if(page == 1){
            mView?.showProgressBar()
        }

        RetrofitHelper.create().doGetMoviesOrSeriesApiCall(movieOrSeries, requestType, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ movieResult: MoviesResult? ->

                    if(page == 1){
                        mView?.hideProgressBar()
                    }
                    mView?.setMovieRecyclerView(movieResult?.moviesList, movieResult?.totalPages!!)


                }, { error ->
                    error.printStackTrace()
                    mView?.showMessage(error.localizedMessage)})
    }
}
