package com.example.mf.movielibrary.activities.splashscreen

import android.content.Intent
import com.example.mf.movielibrary.activities.homescreen.HomeActivity
import com.example.mf.movielibrary.base.BasePresenterImpl
import com.example.mf.movielibrary.classes.NoInternetConnectionException
import com.example.mf.movielibrary.helpers.RetrofitHelper
import files.database
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SplashActivityPresenter : BasePresenterImpl<SplashActivityContract.SplashView>(),
        SplashActivityContract.SplashPresenter {

    override fun clearTable(table: String) {
        mView?.getContext()?.database?.genreDao()?.clearTable()
    }

    override fun callGetGenreListApi(movieOrSeries: String, flag: Int) {
        try {
            RetrofitHelper.create(mView).doGetGenreListApiCall(movieOrSeries)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ genreResult ->
                        if (genreResult != null && genreResult.genresList.isNotEmpty()) {
                            //mView?.getContext()?.database?.genreDao()?.insertAllGenres(genreResult.genresList, movieOrSeries)

                            genreResult.genresList.forEach {
                                it.showType = movieOrSeries
                                mView?.getContext()?.database?.genreDao()?.insertAllGenres(genreResult.genresList)
                            }

                            if (flag == 1) { // api call for tv is completed finish the activity
                                mView?.finishActivityAndStartAnotherActivity(
                                        Intent(mView?.getContext(), HomeActivity::class.java))
                            } else {
                                mView?.callGetTvGenreListApi()
                            }
                        }
                    }, { error ->
                        error.printStackTrace()
                        mView?.showMessage(error.localizedMessage)
                    })
        } catch (e: NoInternetConnectionException) {
        }

    }
}