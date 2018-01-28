package com.example.mf.movielibrary

import com.example.mf.movielibrary.models.castmodel.CastResult
import com.example.mf.movielibrary.models.genremodel.GenreResult
import com.example.mf.movielibrary.models.moviemodel.MoviesResult
import files.API_KEY
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by MF on 01-12-2017.
 */
interface RetrofitApiService {

    @GET("{movie_or_series}/{request_type}")
    fun doGetMoviesOrSeriesApiCall(@Path("movie_or_series") movieOrSeries: String,
                                   @Path("request_type") requestType: String,
                                   @Query("api_key") apiKey: String = API_KEY,
                                   @Query("page") page: Int): Observable<MoviesResult>

    @GET("{movie_or_series}/{movie_or_series_id}/credits")
    fun doGetMovieOrSeriesCastApiCall(@Path("movie_or_series") movieOrSeries: String,
                                      @Path("movie_or_series_id") movieOrSeriesId: Int,
                                      @Query("api_key") apiKey: String = API_KEY): Observable<CastResult>

    @GET("genre/{movie_or_series}/list")
    fun doGetGenreListApiCall(@Path("movie_or_series") movieOrSeries: String,
                                      @Query("api_key") apiKey: String = API_KEY): Observable<GenreResult>
}