package com.example.mf.movielibrary.activities.homescreen

import android.content.Intent
import com.example.mf.movielibrary.activities.celebritiesscreen.CelebritiesActivity
import com.example.mf.movielibrary.activities.collectionlistscreen.CollectionsListActivity
import com.example.mf.movielibrary.activities.favouritescreen.FavouritesActivity
import com.example.mf.movielibrary.activities.movieseriesscreen.MovieSeriesActivity
import com.example.mf.movielibrary.activities.searchscreen.SearchActivity
import com.example.mf.movielibrary.base.BasePresenterImpl
import com.example.mf.movielibrary.classes.NoInternetConnectionException
import com.example.mf.movielibrary.helpers.RetrofitHelper
import com.example.mf.movielibrary.models.moviemodel.Movie
import files.MOVIE_OR_SERIES
import files.PARCELABLE_OBJECT
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by RK on 28-11-2017.
 */
class HomeActivityPresenter : BasePresenterImpl<HomeActivityContract.HomeView>(), HomeActivityContract.HomePresenter {

    override fun requestActorsActivity() {
        val actorsIntent = Intent(mView?.getContext(), CelebritiesActivity::class.java)
        mView?.getContext()?.startActivity(actorsIntent)
    }

    override fun requestCollectionsActivity() {
        mView?.lauchchActivity(Intent(mView?.getContext(), CollectionsListActivity::class.java))
    }

    override fun requestFavouritesActivity() {
        mView?.lauchchActivity(Intent(mView?.getContext(), FavouritesActivity::class.java))
    }

    override fun requestMovieOrSeriesTypeDialog() {
        mView?.showMovieOrSeriesTypeDialog();
    }

    override fun requestSearchActivity(movieOrSeries: String) {
        val searchIntent = Intent(mView?.getContext(), SearchActivity::class.java)
        searchIntent.putExtra(MOVIE_OR_SERIES, movieOrSeries)
        mView?.getContext()?.startActivity(searchIntent)
    }

    override fun callGetMoviesApi(movieOrSeries: String, requestType: String, page: Int) {

        if (page == 1) {
            mView?.showProgressBar()
        }

        try {

            RetrofitHelper.create(mView).doGetMoviesOrSeriesApiCall(movieOrSeries, requestType, page = page)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ movieResult ->

                        if (page == 1) {
                            mView?.hideProgressBar()
                        }
                        mView?.setMovieRecyclerView(movieResult?.moviesList, movieResult?.totalResults!!)
                    }, { error ->
                        error.printStackTrace()
                        mView?.showMessage(error.localizedMessage)
                    })

        }catch (e : NoInternetConnectionException){
            mView?.hideProgressBar()
        }
    }


    override fun launchMovieSeriesActivity(movieModel: Movie?, movieOrSeries: String) {

        val movieSeriesIntent = Intent(mView?.getContext(), MovieSeriesActivity::class.java)
        movieSeriesIntent.putExtra(PARCELABLE_OBJECT, movieModel)
        movieSeriesIntent.putExtra(MOVIE_OR_SERIES, movieOrSeries)
        mView?.getContext()?.startActivity(movieSeriesIntent)
    }
}
