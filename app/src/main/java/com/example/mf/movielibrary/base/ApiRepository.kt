package com.example.mf.movielibrary.base

import com.example.mf.movielibrary.RetrofitApiService
import com.example.mf.movielibrary.models.MoviesResult
import io.reactivex.Observable

/**
 * Created by MF on 05-12-2017.
 */
class ApiRepository(val apiService: RetrofitApiService) {

    fun getMoviesOrSeries(movieOrSeries: String, requestType : String, page : Int) : Observable<MoviesResult>{
        return apiService.doGetMoviesOrSeriesApiCall(movieOrSeries, requestType, page)
    }
}