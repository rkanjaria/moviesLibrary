package com.example.mf.movielibrary.activities.celebritiesscreen

import android.content.Intent
import com.example.mf.movielibrary.activities.actorsscreen.ActorsActivity
import com.example.mf.movielibrary.activities.searchscreen.SearchActivity
import com.example.mf.movielibrary.base.BasePresenterImpl
import com.example.mf.movielibrary.classes.NoInternetConnectionException
import com.example.mf.movielibrary.helpers.RetrofitHelper
import com.example.mf.movielibrary.models.actormodel.Actor
import com.example.mf.movielibrary.files.INT_ID
import com.example.mf.movielibrary.files.MOVIE_OR_SERIES
import com.example.mf.movielibrary.files.NAME
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CelebritiesActivityPresenter : BasePresenterImpl<CelebritiesActivityContract.CelebritiesView>(),
        CelebritiesActivityContract.CelebritiesPresenter {

    override fun lauchchSearchActivity(movieSeriesOrActors: String) {
        val searchIntent = Intent(mView?.getContext(), SearchActivity::class.java)
        searchIntent.putExtra(MOVIE_OR_SERIES, movieSeriesOrActors)
        mView?.getContext()?.startActivity(searchIntent)
    }

    override fun launchActorsActivity(actorModel: Actor?) {
        val actorIntent = Intent(mView?.getContext(), ActorsActivity::class.java)
        actorIntent.putExtra(INT_ID, actorModel?.actorId)
        actorIntent.putExtra(NAME, actorModel?.actorName)
        mView?.lauchchActivity(actorIntent)
    }

    override fun callGetPopularPeopleApi(page: Int) {

        try {
            if (page == 1) {
                mView?.showProgressBar()
            }

            RetrofitHelper.create(mView)
                    .doGetPopularPeopleApiCall(page)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ actorsResult ->

                        if (page == 1) {
                            mView?.hideProgressBar()
                        }
                        if (actorsResult.actorsList != null && actorsResult.actorsList.isNotEmpty()) {
                            mView?.setActorsRecyclerview(actorsResult.actorsList, actorsResult.totalResults)
                        }

                    }, { error ->
                        error.printStackTrace()
                        mView?.showMessage(error.localizedMessage)
                    })

        } catch (e: NoInternetConnectionException) {
            mView?.hideProgressBar()
        }
    }
}