package com.example.mf.movielibrary.activities.searchscreen

import android.content.Intent
import com.example.mf.movielibrary.activities.actorsscreen.ActorsActivity
import com.example.mf.movielibrary.activities.movieseriesscreen.MovieSeriesActivity
import com.example.mf.movielibrary.base.BasePresenterImpl
import com.example.mf.movielibrary.helpers.RetrofitHelper
import com.example.mf.movielibrary.models.actormodel.Actor
import com.example.mf.movielibrary.models.actormodel.ActorsResult
import com.example.mf.movielibrary.models.moviemodel.Movie
import files.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by RK on 08-02-2018.
 */
class SearchActivityPresenter : BasePresenterImpl<SearchActivityContract.SearchBaseView>(),
        SearchActivityContract.SearchPresenter {

    override fun launchActorsActivity(actorModel: Actor?) {
        val actorIntent = Intent(mView?.getContext(), ActorsActivity::class.java)
        actorIntent.putExtra(INT_ID, actorModel?.actorId)
        actorIntent.putExtra(NAME, actorModel?.actorName)
        mView?.lauchchActivity(actorIntent)
    }

    override fun callSearchPeopleApi(searchQuery: String?, queryPage: Int) {
        if (queryPage == 1) {
            mView?.showProgressBar()
        }

        if (searchQuery != null && searchQuery.isNotEmpty()) {
            RetrofitHelper.create().doSearchPeopleApiCall(searchQuery = searchQuery, page = queryPage)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ actorsResult: ActorsResult ->

                        if (queryPage == 1) {
                            mView?.hideProgressBar()
                        }
                        if (actorsResult.actorsList != null && actorsResult.actorsList.isNotEmpty()) {
                            mView?.setActorsSearchRecyclerView(actorsResult.actorsList, actorsResult.totalResults)
                        }
                    }, { error ->
                        error.printStackTrace()
                        mView?.showMessage(error.localizedMessage)
                    })
        }
    }

    override fun callSearchByGenreApi(movieOrSeries: String, page: Int, genreId: Int) {

        if (page == 1) {
            mView?.showProgressBar()
        }

        RetrofitHelper.create().doSearchMovieOrSeriesByGenreApiCall(movieOrSeries, page = page, genres = genreId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ movieResult ->

                    if (page == 1) {
                        mView?.hideProgressBar()
                    }
                    mView?.setSearchRecyclerView(movieResult?.moviesList, movieResult?.totalResults!!)
                }, { error ->
                    error.printStackTrace()
                    mView?.showMessage(error.localizedMessage)
                })
    }

    override fun getGenreFromDb(movieOrSeries: String) {
        val genreList = mView?.getContext()?.database?.getAllGenres(movieOrSeries)
        if (genreList != null && genreList.isNotEmpty()) {
            mView?.setGenreRecylerview(genreList)
        }
    }

    override fun launchMovieSeriesActivity(movieModel: Movie?, movieOrSeries: String) {
        val movieSeriesIntent = Intent(mView?.getContext(), MovieSeriesActivity::class.java)
        movieSeriesIntent.putExtra(PARCELABLE_OBJECT, movieModel)
        movieSeriesIntent.putExtra(MOVIE_OR_SERIES, movieOrSeries)
        mView?.getContext()?.startActivity(movieSeriesIntent)
    }

    override fun callSearchMovieOrSeriesByName(movieOrSeries: String, query: String?, queryPage: Int) {
        if (queryPage == 1) {
            mView?.showProgressBar()
        }

        if (query != null && query.isNotEmpty()) {

            RetrofitHelper.create().doSearchMovieOrSeriesApiCall(movieOrSeries, searchQuery = query, page = queryPage)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ movieResult ->

                        if (queryPage == 1) {
                            mView?.hideProgressBar()
                        }
                        mView?.setSearchRecyclerView(movieResult?.moviesList, movieResult?.totalResults!!)
                    }, { error ->
                        error.printStackTrace()
                        mView?.showMessage(error.localizedMessage)
                    })
        }
    }

    override fun requestSearchTypeDialog() {
        mView?.showSearchTypeDialog()
    }
}