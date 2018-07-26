package com.example.mf.movielibrary.activities.actorslistscreen

import android.content.Intent
import com.example.mf.movielibrary.activities.actorsscreen.ActorsActivity
import com.example.mf.movielibrary.activities.searchscreen.SearchActivity
import com.example.mf.movielibrary.base.BasePresenterImpl
import com.example.mf.movielibrary.helpers.RetrofitHelper
import com.example.mf.movielibrary.models.actormodel.Actor
import files.INT_ID
import files.MOVIE_OR_SERIES
import files.NAME
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ActorsListActivityPresenter : BasePresenterImpl<ActorsListActivityContract.ActorsListView>(),
        ActorsListActivityContract.ActorsListPresenter {

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

        if (page == 1) {
            mView?.showProgressBar()
        }

        RetrofitHelper.create()
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
    }

}