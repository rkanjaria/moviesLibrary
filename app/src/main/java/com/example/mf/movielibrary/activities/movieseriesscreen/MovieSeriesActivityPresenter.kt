package com.example.mf.movielibrary.activities.movieseriesscreen

import com.example.mf.movielibrary.base.BasePresenterImpl
import com.example.mf.movielibrary.helpers.RetrofitHelper
import com.example.mf.movielibrary.models.castmodel.CastResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by MF on 04-01-2018.
 */
class MovieSeriesActivityPresenter : BasePresenterImpl<MovieSeriesActivityContract.MovieSeriesView>(),
        MovieSeriesActivityContract.MovieSeriesPresenter {

    override fun callgetMovieOrSeriesCastApi(movieOrSeries: String?, movieId: Int) {

        if (movieOrSeries != null) {
            RetrofitHelper.create().doGetMovieOrSeriesCastApiCall(movieOrSeries, movieId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe ({
                        castResult: CastResult? ->

                        if(castResult?.castList != null && castResult?.castList.isNotEmpty()){
                            mView?.setCastRecyclerview(castResult.castList)
                        }
                    },{
                        error ->
                        error.printStackTrace()
                        mView?.showMessage(error.localizedMessage)
                    })
        }

    }
}