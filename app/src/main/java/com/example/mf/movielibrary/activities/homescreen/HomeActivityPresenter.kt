package com.example.mf.movielibrary.activities.homescreen

import com.example.mf.movielibrary.base.ApiRepositoryProvider
import com.example.mf.movielibrary.base.BasePresenterImpl
import com.example.mf.movielibrary.models.MoviesResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by MF on 28-11-2017.
 */
class HomeActivityPresenter : BasePresenterImpl<HomeActivityContract.HomeView>(), HomeActivityContract.HomePresenter {

    override fun callGetMoviesApi(movieOrSeries : String, requestType : String, page : Int) {

        mView?.showProgressBar()
        ApiRepositoryProvider.provideApiRepository().getMoviesOrSeries(movieOrSeries, requestType, page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ movieResult: MoviesResult? ->
                    mView?.hideProgressBar()
                    mView?.setMovieRecyclerView(movieResult?.moviesList)

                }, { error ->
                    error.printStackTrace()
                    mView?.showMessage(error.localizedMessage)
                })
    }
}
