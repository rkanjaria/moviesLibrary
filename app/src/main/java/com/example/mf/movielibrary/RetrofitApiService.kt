package com.example.mf.movielibrary

import com.example.mf.movielibrary.models.moviemodel.MoviesResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by MF on 01-12-2017.
 */
interface RetrofitApiService {

    @GET("{movie_or_series}/{request_type}?api_key=5526b6795b244b3f164510b8955df249")
    fun doGetMoviesOrSeriesApiCall(@Path("movie_or_series") movieOrSeries : String,
                           @Path("request_type") requestType : String,
                           @Query("page") page: Int) : Observable<MoviesResult>


}